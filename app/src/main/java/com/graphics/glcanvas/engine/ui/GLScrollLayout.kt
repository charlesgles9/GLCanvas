package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import kotlin.math.abs

class GLScrollLayout(width:Float,height:Float):GLView(width,height) {

    private var items= mutableListOf<GLView>()
    private var orientation= VERTICAL
    private var offset=Vector2f()
    private var onSwipeEvent:GLOnSwipeEvent?=null
    private var onSwipeListener:GLOnSwipeEvent.OnSwipeListener?=null
    companion object{
        const val VERTICAL=0
        const val HORIZONTAL=1
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

    private fun groupItems(){
        if(orientation== LinearLayoutConstraint.VERTICAL) {
            for (i in 0 until items.size) {
                val view = items[i]
                val xOffset = getX() - width * 0.5f
                //if its the first item position it at the top
                if (i == 0)
                    view.set(xOffset + view.width * 0.5f,
                        getY() - height * 0.5f + view.height * 0.5f + view.getConstraints()
                            .getMarginBottom()+offset.y
                    )
                // set next layout below this layout
                if (view != items.last()) {
                    val next = items[i + 1]
                    next.setX(xOffset + next.width * 0.5f)
                    next.getConstraints().alignBelow(view)
                    next.setY(next.getY())
                }
            }
            // horizontal orientation code from left to right
        }else{
            for (i in 0 until items.size) {
                val view = items[i]
                val yOffset = getY() -height * 0.5f
                //if its the first item position it at the top
                if (i == 0)
                    view.set(getX() - width * 0.5f + view.width * 0.5f + view.getConstraints()
                        .getMarginRight(),yOffset + view.height * 0.5f)
                // set next layout below this layout
                if (view != items.last()) {
                    val next = items[i + 1]
                    next.setY(yOffset + next.height * 0.5f+view.getConstraints()
                        .getMarginBottom())
                    next.getConstraints().toRightOf(view)
                }
            }
        }
    }

    fun addOnSwipeEvent(onSwipeListener:GLOnSwipeEvent.OnSwipeListener){
        this.onSwipeEvent= GLOnSwipeEvent(onSwipeListener,this)
        this.onSwipeListener=onSwipeListener
    }

    private fun clipView(view:GLView){
        val lowerVisible=getY()+height*0.5f>=view.getY()-view.height*0.5f
        val upperVisible=getY()-height*0.5f<=view.getY()+view.height*0.5f
        val diffLower=(getY()+height*0.5f)-(view.getY()+view.height*0.5f)
        val diffUpper=(getY()-height*0.5f)-(view.getY()-view.height*0.5f)
        view.setVisibility(lowerVisible && upperVisible)
        if(diffLower<=0&& abs(diffLower)<=view.height)
            view.clipViewLower(1f,getY()+(height*0.5f))
        if(diffUpper<=0&& abs(diffUpper)<=view.height)
            view.clipViewUpper(1f,getY()-height*0.5f)

    }

    override fun draw(batch: Batch) {
        super.draw(batch)
        val first=items.first()
        val last=items.last()
        val vy=(onSwipeEvent?.getVelocity()?.y?:0f)
        if((first.getY()-first.height+vy)<=getY()-height*0.5f&&onSwipeEvent?.UP==true){
             offset.sub(0f, vy)
             onSwipeEvent?.getVelocity()?.multiply(GLOnSwipeEvent.friction)
        }else
            if((last.getY()+last.height+vy)>getY()+height*0.5f&&onSwipeEvent?.DOWN==true){
                offset.sub(0f, vy)
                onSwipeEvent?.getVelocity()?.multiply(GLOnSwipeEvent.friction)
              //@debug  println("last "+last.getY()+" origin "+getY()+height*0.5f+" velocity "+vy)
            }else{
                onSwipeEvent?.setVelocity(0f,0f)
            }
         groupItems()

        items.forEach {
             clipView(it)
             it.draw(batch)

        }
    }

    override fun onTouchEvent(event: MotionEvent) {
        super.onTouchEvent(event)
        onSwipeEvent?.onTouchEvent(event)

    }
}