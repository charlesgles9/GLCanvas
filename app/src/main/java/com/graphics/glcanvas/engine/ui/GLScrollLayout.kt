package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.structures.RectF
import com.graphics.glcanvas.engine.structures.Text
import kotlin.math.abs

class GLScrollLayout(width:Float,height:Float):GLView(width,height) {

    private var items= mutableListOf<GLView>()
    private var orientation= VERTICAL
    private var offset=Vector2f()
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

    private fun clipView(view:GLView){
        val lower_visible=getY()+height*0.5f>=view.getY()-view.height*0.5f
        val upper_visible=getY()-height*0.5f<=view.getY()+view.height*0.5f
        val diff_lower=(getY()+height*0.5f)-(view.getY()+view.height*0.5f)
        val diff_upper=(getY()-height*0.5f)-(view.getY()-view.height*0.5f)
        view.setVisibility(lower_visible && upper_visible)
        if(diff_lower<=0&& abs(diff_lower)<=view.height)
            view.clipViewLower(1f,getY()+(height*0.5f))
        if(diff_upper<=0&& abs(diff_upper)<=view.height)
            view.clipViewUpper(1f,getY()-height*0.5f)

    }

    private fun clipText(text:Text?){
        val x= (text?.position?.x?:0f)
        val y= (text?.position?.y?:0f)
        val w= (text?.width?:0f)
        val h= (text?.height?:0f)
        val diff_lower=(y+h)-(getY()+height)
        val diff_upper=(getY()-height)-(y-h)
        if(diff_lower<=0&& abs(diff_lower)<=h){
            val factor=abs(diff_lower+0.1f)/h
            text?.setClipLower(1f,factor)
        }
        if(diff_upper<=0&& abs(diff_upper)<=h){
            val factor=abs(diff_upper+0.1f)/h
            text?.setClipUpper(1f,factor)
        }
    }

    override fun draw(batch: Batch) {
        val first=items.first()
        val last=items.last()
        items.forEach {
             clipView(it)
            // clipText(it.getTextView())
             it.draw(batch)

        }
        super.draw(batch)
        groupItems()


        if(last.getY()>=(height*0.5+getY()+last.height*0.5f)){
            offset.sub(0f,0.5f)
        }

        //println(offset.y)
    }
}