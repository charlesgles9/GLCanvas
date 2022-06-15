package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
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

    override fun draw(batch: Batch) {
        val first=items.first()
        val last=items.last()
        items.forEach {
            val diff_lower=(it.getY()+it.height*0.5f)-(getY()+height)
            val diff_upper=(getY()-height)-(it.getY()-it.height)
             it.setVisibility(diff_lower<=0||diff_upper>=0)
             if(diff_lower<=0&& abs(diff_lower)<=it.height){
                 val factor=abs(diff_lower+0.1f)/it.height
                 it.trimView(1f,factor)
             }
            if(diff_upper<=0&& abs(diff_upper)<=it.height){
                val factor=abs(diff_upper+0.1f)/it.height
                it.trimView(1f,factor)
            }
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