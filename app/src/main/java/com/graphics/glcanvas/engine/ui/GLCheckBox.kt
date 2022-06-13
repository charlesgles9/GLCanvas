package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.maths.ColorRGBA

class GLCheckBox(width:Float,height:Float,color: ColorRGBA):GLView(width, height) {


    init {
       getForeground().setThickness(10f)
       getBackground().setWidth(width-10f)
       getBackground().setHeight(height-10f)
       setBackgroundColor(color)
       getForeground().setColor(ColorRGBA(1f,1f,1f,1f))
    }


    fun setCheckedColor(color: ColorRGBA){
        setRippleColor(color)
    }

    fun setCheckedBackground(color: ColorRGBA){
        setForegroundColor(color)
    }



}