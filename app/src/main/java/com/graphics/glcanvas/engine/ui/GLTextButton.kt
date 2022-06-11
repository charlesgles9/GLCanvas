package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.structures.Text
import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLTextButton(width:Float,height:Float,private val atlas: TextureAtlas,private var name:String) : GLView(width, height) {


    init {
        setBackgroundTextureAtlas(atlas)
        setBackgroundImageFromAtlas(name)
    }

    fun setBackgroundImageFromAtlas(name:String){
        this.name=name
        setBackgroundFrame(name)
    }

    fun setSecondaryImageFromAtlas(name:String){
        setSecondaryTextureAtlas(atlas)
        setSecondaryFrame(name)
    }

    private fun setBackgroundFrame(name:String){
        getBackground().getSpriteSheet().setCurrentFrame(atlas.getTextureCoordinate(name))
    }
    private fun setSecondaryFrame(name:String){
        getSecondary()?.getSpriteSheet()?.setCurrentFrame(atlas.getTextureCoordinate(name))
    }

    fun setText(string:String,font: Font,size:Float){
       text= Text(string,size,font)
    }



}