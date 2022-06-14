package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.structures.Text
import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLImageButton(width:Float, height:Float) : GLView(width, height) {


    constructor(width: Float,height: Float,atlas: TextureAtlas,name: String):this(width, height) {
        this.atlas=atlas
        this.name=name
        setBackgroundTextureAtlas(atlas)

        imageFromAtlas(name)
    }

    private fun imageFromAtlas(name:String){
        this.name=name
        setPrimaryImage(name)
        setBackgroundFrame(name)
    }


    fun setText(string:String,font: Font,size:Float){
       text= Text(string,size,font)
       text?.setMaxWidth(width*0.5f)
       text?.setMaxHeight(height)
    }

    fun setTextColor(color: ColorRGBA){
        text?.setColor(color)
    }


}