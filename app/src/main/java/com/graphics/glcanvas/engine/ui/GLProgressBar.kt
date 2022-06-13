package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.structures.Text

class GLProgressBar(width:Float,height:Float,private var progress:Float,private val horizontal:Boolean):GLView(width, height) {

    private var max=100f

       init {
          positionBars()
       }


    private fun positionBars(){
        if(horizontal) {
            getForeground().setWidth(width * ((progress+1) / (max+1)))
            fOffset.x=((getForeground().getWidth()-width)*0.5f)
        }else{
            getForeground().setHeight(height*((progress+1)/(max+1)))
            fOffset.y=((height-getForeground().getHeight())*0.5f)
        }
    }

    override fun roundedCorner(value: Float) {
        getBackground().setConnerRadius(value)
        setThickness(value)
    }

    private fun setThickness(value:Float){
        positionBars()
        getForeground().setWidth(getForeground().getWidth()-value*2)
        getForeground().setHeight(getForeground().getHeight()-value*2)
        getBackground().setThickness(value)
    }

    fun setText(string:String, font: Font, size:Float){
        text= Text(string,size,font)
        text?.setMaxWidth(width*0.5f)
        text?.setMaxHeight(height)
    }

    fun setTextColor(color:ColorRGBA){
        text?.setColor(color)
    }

    fun getTextView():Text?{
        return text
    }

    fun getProgress():Float{
        return progress
    }

    fun setMaxProgress(max:Float){
        this.max=max
    }

    fun getMaxProgress():Float{
        return max
    }
}