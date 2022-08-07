package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLImageView(width:Float,height:Float):GLView(width, height)  {


    constructor(width: Float,height: Float,atlas: TextureAtlas, name:String,index: Int)
            :this(width, height) {
        this.atlas=atlas
        setBackgroundTextureAtlas(atlas)
        setForegroundTextureAtlas(atlas)
        setBackgroundImageAtlas(name,index)
    }

    constructor(width: Float,height: Float,atlas: TextureAtlas, name:String)
            :this(width, height,atlas,name,0) {

    }

    fun setBackgroundImageAtlas(name:String, index:Int){
        setPrimaryImage(name,index)
        setBackgroundSubTexture(name,index)
    }

    fun setBackgroundImageAtlas(name:String){
        setBackgroundImageAtlas(name,0)
    }



    override fun setVisibility(visible: Boolean) {
        super.setVisibility(visible)
    }

}