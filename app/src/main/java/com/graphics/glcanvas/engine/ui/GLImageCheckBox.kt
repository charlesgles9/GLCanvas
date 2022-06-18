package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLImageCheckBox(width:Float, height:Float, atlas: TextureAtlas, primary: String,checked:String):GLView(width, height){

    init {
        this.atlas=atlas
        this.name=primary
        getForeground().setWidth(width*0.8f)
        getForeground().setHeight(height*0.8f)
        setBackgroundTextureAtlas(atlas)
        setForegroundTextureAtlas(atlas)
        setForegroundFrame(primary)
        setBackgroundFrame(primary)
        setForegroundColor(ColorRGBA(1f))
        setRippleColor(ColorRGBA.transparent)
        setPrimaryImage(checked)
        setBackgroundFrame(primary)
        isCheckBox=true
    }




}