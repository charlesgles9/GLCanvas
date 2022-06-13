package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.structures.Text
import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLImageButton(width:Float, height:Float) : GLView(width, height) {
    private var atlas: TextureAtlas?=null
    private var name:String?=null

    constructor(width: Float,height: Float,atlas: TextureAtlas,name: String):this(width, height) {
        this.atlas=atlas
        this.name=name
        setBackgroundTextureAtlas(atlas)
        imageFromAtlas(name)
    }

    fun imageFromAtlas(name:String){
        this.name=name
        setBackgroundFrame(name)
    }

    fun secondaryImageFromAtlas(name:String){
        if(atlas!=null) {
            setSecondaryTextureAtlas(atlas!!)
            setSecondaryFrame(name)
        }
    }

    private fun setBackgroundFrame(name:String){
        if(atlas!=null)
        getBackground().getSpriteSheet().setCurrentFrame(atlas!!.getTextureCoordinate(name))
    }


    private fun setSecondaryFrame(name:String){
        if(atlas!=null)
        getSecondary()?.getSpriteSheet()?.setCurrentFrame(atlas!!.getTextureCoordinate(name))
    }

    fun setText(string:String,font: Font,size:Float){
       text= Text(string,size,font)
       text?.setMaxWidth(width*0.5f)
       text?.setMaxHeight(height)
    }



}