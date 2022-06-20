package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.structures.Text
import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLProgressBar(width:Float,height:Float, progress:Float,horizontalBar:Boolean):GLView(width, height) {


       init {
           this.currentProgress=progress
           this.horizontalBar=horizontalBar
           positionBars(horizontalBar,progress, maxProgressBar)
           setRippleColor(getBackground().getColor(0))
           isProgressBar=true
       }

    constructor(width: Float, height: Float,progress: Float,horizontalBar:Boolean, atlas: TextureAtlas,
                primary: String,secondary:String):this(width, height,progress, horizontalBar) {
        this.atlas=atlas
        this.name=secondary
        setBackgroundTextureAtlas(atlas)
        setPrimaryImage(secondary)
        setBackgroundFrame(secondary)
        setForegroundTextureAtlas(atlas)
        setForegroundFrame(primary)

    }

    override fun roundedCorner(value: Float) {
        this.backgroundThickness=value
        getBackground().setConnerRadius(value)
        getForeground().setConnerRadius(value)
        getForeground().setWidth(getForeground().getWidth()-value*2)
        getForeground().setHeight(getForeground().getHeight()-value*2)
    }

     fun setThickness(value:Float){
        this.backgroundThickness=value
        positionBars(horizontalBar,currentProgress, maxProgressBar)
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


    fun getProgress():Float{
        return currentProgress
    }

    fun setMaxProgress(maxProgressBar:Float){
        this.maxProgressBar=maxProgressBar
    }

    fun getMaxProgress():Float{
        return maxProgressBar
    }

    fun isHorizontal():Boolean{
        return horizontalBar
    }
}