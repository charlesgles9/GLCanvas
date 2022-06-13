package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.structures.Text
import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLLabel(width:Float,height:Float,private var font: Font,private var string:String,private var size: Float) : GLView(width,height) {
    private var atlas: TextureAtlas?=null
    private var name:String?=null
    constructor(width: Float, height: Float, atlas: TextureAtlas, name: String,font:Font,string: String,size: Float):this(width, height,font,string,size) {
        this.atlas=atlas
        this.name=name
        this.font=font
        this.string=string
        this.size=size
        setBackgroundTextureAtlas(atlas)
        imageFromAtlas(name)
        setText(string,font,size)
    }

    fun imageFromAtlas(name:String){
        this.name=name
        setBackgroundFrame(name)
    }

    private fun setBackgroundFrame(name:String){
        if(atlas!=null)
            getBackground().getSpriteSheet().setCurrentFrame(atlas!!.getTextureCoordinate(name))
    }


    fun setText(string:String, font:Font, size:Float){
        text= Text(string,size,font)
        text?.setMaxWidth(width*0.5f)
        text?.setMaxHeight(height)
    }
}