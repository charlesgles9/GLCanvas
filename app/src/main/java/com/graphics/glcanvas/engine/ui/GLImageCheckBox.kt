package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLImageCheckBox(width:Float, height:Float, atlas: TextureAtlas, primary: String,checked:String):GLView(width, height){

    init {
        this.atlas=atlas
        this.name=primary

        setBackgroundTextureAtlas(atlas)
        setPrimaryImage(primary)
        setSecondaryImage(checked)
        setRippleColor(ColorRGBA(1f,1f,1f,1f))
        setBackgroundFrame(primary)
        isCheckBox=true
    }




}