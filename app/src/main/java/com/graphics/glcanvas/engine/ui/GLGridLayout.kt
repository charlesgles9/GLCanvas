package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f

class GLGridLayout(private val parent:GLView?,width:Float,height:Float,private val rows:Int,private val cols:Int):GLView(width ,height) {
    private var items= mutableListOf<GLView>()
    private var offset= Vector2f()
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

    fun getItems():MutableList<GLView>{
        return items
    }

    override fun setEnabled(enable: Boolean) {
        super.setEnabled(enable)
        items.forEach {
            it.setEnabled(enable)
        }
    }

    override fun draw(batch: Batch) {
        super.draw(batch)
        LayoutConstraint.groupItems(offset,this,items, rows, cols)
        items.forEach {
            LayoutConstraint.clipView(parent?:this,this,it)
            it.draw(batch)
        }
    }
}