package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLImageProgressBar(width:Float, height:Float, progress:Float,horizontalBar:Boolean, atlas: TextureAtlas,
                         bgImage: String,fgImage:String):GLView(width, height) {

            init {
                this.atlas=atlas
                this.currentProgress=progress
                this.horizontalBar=horizontalBar
                setBackgroundTextureAtlas(atlas)
                setPrimaryImage(bgImage)
                setBackgroundFrame(bgImage)
                setSecondaryTextureAtlas(atlas)
                setForegroundFrame(fgImage)
                positionBars(horizontalBar,progress, maxProgressBar)
                setRippleColor(getBackground().getColor(0))
                isProgressBar=true
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