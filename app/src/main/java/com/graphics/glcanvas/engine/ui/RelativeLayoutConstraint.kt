package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA

class RelativeLayoutConstraint(width:Float,height:Float):GLView(width ,height) {


    private var items= mutableListOf<GLView>()
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
    private fun applyMargin(view:GLView){
        view.set(view.getX()+view.getConstraints().getMarginLeft(),view.getY())
        view.set(view.getX()-view.getConstraints().getMarginRight(),view.getY())
        view.set(view.getX(),view.getY()+view.getConstraints().getMarginTop())
        view.set(view.getX(),view.getY()-view.getConstraints().getMarginBottom())
    }

    private fun removeMargin(view:GLView){
        view.set(view.getX()-view.getConstraints().getMarginLeft(),view.getY())
        view.set(view.getX()+getConstraints().getMarginRight(),view.getY())
        view.set(view.getX(),view.getY()-view.getConstraints().getMarginTop())
        view.set(view.getX(),view.getY()+view.getConstraints().getMarginBottom())
    }
    override fun draw(batch: Batch) {
        super.draw(batch)
        items.forEach {
            applyMargin(it)
            it.draw(batch)
            removeMargin(it)
        }

    }
}