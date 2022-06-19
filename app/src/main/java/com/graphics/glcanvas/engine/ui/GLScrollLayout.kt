package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.structures.RectF
import com.graphics.glcanvas.engine.utils.TextureAtlas
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class GLScrollLayout(width:Float,height:Float):GLView(width,height) {

    private var items= mutableListOf<GLView>()
    private var orientation= VERTICAL
    private var offset=Vector2f()
    private var onSwipeEvent:GLOnSwipeEvent?=null
    private var onSwipeListener:GLOnSwipeEvent.OnSwipeListener?=null
    private val scrollBarProgress=RectF()
    private val scrollBarBackground=RectF()
    private var scrollBarHeight=10f
    private var enableScrollBar=false

    companion object{
        const val VERTICAL=0
        const val HORIZONTAL=1
    }
    constructor(width:Float, height:Float, atlas: TextureAtlas, name:String):this(width, height){
        this.atlas=atlas
        this.name=name
        setBackgroundTextureAtlas(atlas)
        setPrimaryImage(name)
        setBackgroundFrame(name)
    }

    fun showScrollBar(enableScrollBar:Boolean){
        this.enableScrollBar=enableScrollBar
    }

    fun setScrollBarProgressColor(color: ColorRGBA){
        scrollBarProgress.setColor(color)
    }

    fun setScrollBarBackgroundColor(color: ColorRGBA){
        scrollBarBackground.setColor(color)
    }

    fun setScrollBarProgressFromAtlas(name: String){
        scrollBarProgress.setSpriteSheet(atlas?.getSheet()?.clone())
        scrollBarProgress.setTexture(atlas?.getTexture()!!)
        scrollBarProgress.getSpriteSheet().setCurrentFrame(atlas?.getTextureCoordinate(name)?:0)
    }

    fun setScrollBarBackgroundFromAtlas(name: String){
        scrollBarBackground.setSpriteSheet(atlas?.getSheet()?.clone())
        scrollBarBackground.setTexture(atlas?.getTexture()!!)
        scrollBarBackground.getSpriteSheet().setCurrentFrame(atlas?.getTextureCoordinate(name)?:0)
    }

    fun setScrollBarHeight(height: Float){
        scrollBarHeight=height
    }

    //push this view from center origin 0.5,0.5 -> 0,0
    fun setPosition(x:Float,y:Float){
        set(x+width*0.5f,y+height*0.5f)
    }

    fun setColor(color: ColorRGBA){
        setBackgroundColor(color)
    }

    fun setItems(items:MutableList<GLView>){
        this.items=items

    }

    fun setOrientation(orientation:Int){
        this.orientation=orientation
    }

    fun addOnSwipeEvent(onSwipeListener:GLOnSwipeEvent.OnSwipeListener){
        this.onSwipeEvent= GLOnSwipeEvent(onSwipeListener,this)
        this.onSwipeListener=onSwipeListener
    }


    private fun scrollVertical(){
        val first=items.first()
        val last=items.last()
        val vy=(onSwipeEvent?.getVelocity()?.y?:0f)
        if((first.getY()-vy-first.height*0.5f)<=getY()-height*0.5f&&onSwipeEvent?.UP==true){
            offset.sub(0f, vy)
            onSwipeEvent?.getVelocity()?.multiply(GLOnSwipeEvent.friction)
        }else
            if((last.getY()+vy+last.height*0.5f)>=getY()+height*0.5f&&onSwipeEvent?.DOWN==true){
                offset.sub(0f, vy)
                onSwipeEvent?.getVelocity()?.multiply(GLOnSwipeEvent.friction)
                //@debug  println("last "+last.getY()+" origin "+getY()+height*0.5f+" velocity "+vy)
            }else{
                //reset this or we'll get this annoying jagged scrolling effect
                onSwipeEvent?.getVelocity()?.set(0f,0f)
            }
    }

    private fun scrollHorizontal(){
        val first=items.first()
        val last=items.last()
        val vx=(onSwipeEvent?.getVelocity()?.x?:0f)
        if((first.getX()-first.width+vx)<getX()-width*0.5f&&onSwipeEvent?.LEFT==true){
            offset.sub(vx, 0f)
            onSwipeEvent?.getVelocity()?.multiply(GLOnSwipeEvent.friction)
        }else
            if((last.getX()+last.width+vx)>getX()+width*0.5f&&onSwipeEvent?.RIGHT==true){
                offset.sub(vx, 0f)
                onSwipeEvent?.getVelocity()?.multiply(GLOnSwipeEvent.friction)
                //@debug  println("last "+last.getY()+" origin "+getY()+height*0.5f+" velocity "+vy)
            }else{
                onSwipeEvent?.getVelocity()?.set(0f,0f)
            }
    }

    private fun drawScrollBar(batch: Batch){
        val last=items.last()
        val minWidth=getX()+last.width*0.5f+width*0.5f
        val farWidth=min(minWidth, max(last.getX()+last.width*0.5f-width,1f))
        val percent=(1.0f-farWidth/minWidth)
        // println(percent)
        //println(getX())
      //  println(last.width)
        //println(factor*100f)
        scrollBarBackground.setHeight(scrollBarHeight)
        scrollBarProgress.setHeight(scrollBarHeight*0.8f)
        scrollBarBackground.setWidth(width*0.8f)
        scrollBarProgress.setWidth(50f)
        scrollBarBackground.set(getX()+width*0.1f*0.5f,getY()+height*0.4f)
        val offset=(scrollBarBackground.getWidth())*percent+scrollBarProgress.getWidth()
        val px=min(scrollBarBackground.getX()-scrollBarBackground.getWidth()*0.5f+offset,
             scrollBarBackground.getX()+scrollBarBackground.getWidth()*0.5f)

        scrollBarProgress.set(px,getY()+height*0.4f)
        batch.draw(scrollBarBackground)
        batch.draw(scrollBarProgress)


    }
    override fun draw(batch: Batch) {
        super.draw(batch)

        when(orientation){
             VERTICAL->
                 scrollVertical()
             HORIZONTAL->
                 scrollHorizontal()
        }
        LayoutConstraint.groupItems(orientation,offset ,this,items)
        items.forEach {
            LayoutConstraint.clipView(this,it)
             it.draw(batch)
        }
        drawScrollBar(batch)


    }

    override fun onTouchEvent(event: MotionEvent) {
        super.onTouchEvent(event)
        onSwipeEvent?.onTouchEvent(event)

    }
}