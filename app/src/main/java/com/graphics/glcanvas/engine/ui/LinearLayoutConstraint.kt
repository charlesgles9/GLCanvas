package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA


class LinearLayoutConstraint(width:Float,height:Float):GLView(width ,height) {


    private var items= mutableListOf<GLView>()
    private var orientation= VERTICAL
    companion object{
        const val VERTICAL=0
        const val HORIZONTAL=1
    }
    //push this view from center origin 0.5,0.5 -> 0,0
     fun setPosition(x:Float,y:Float){
        set(x+width*0.5f,y+height*0.5f)
    }

    fun setColor(color:ColorRGBA){
        setBackgroundColor(color)
    }
    fun setItems(items:MutableList<GLView>){
        this.items=items
        groupItems()
    }

    fun setOrientation(orientation:Int){
        this.orientation=orientation
    }

    private fun groupItems(){
        if(orientation== VERTICAL) {
            for (i in 0 until items.size) {
                val view = items[i]
                val xOffset = getX() - width * 0.5f
                //if its the first item position it at the top
                if (i == 0)
                    view.set(xOffset + view.width * 0.5f,
                        getY() - height * 0.5f + view.height * 0.5f + view.getConstraints()
                            .getMarginBottom()
                    )
                // set next layout below this layout
                if (view != items.last()) {
                    val next = items[i + 1]
                    next.setX(xOffset + next.width * 0.5f)
                    next.getConstraints().alignBelow(view)
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
        super.draw(batch)
         groupItems()
        items.forEach {
            it.draw(batch)
        }

    }
}