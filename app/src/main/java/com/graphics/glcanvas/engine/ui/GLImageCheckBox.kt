package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLImageCheckBox(width:Float, height:Float, atlas: TextureAtlas, primary: String,primaryIndex:Int,checked:String,checkedIndex:Int):GLView(width, height){

    init {
        this.atlas=atlas
        getForeground().setWidth(width*0.8f)
        getForeground().setHeight(height*0.8f)
        setBackgroundTextureAtlas(atlas)
        setForegroundTextureAtlas(atlas)
        setForegroundTextureFrame(primary,primaryIndex)
        setBackgroundTextureFrame(primary,primaryIndex)
        setForegroundColor(ColorRGBA(1f))
        setRippleColor(ColorRGBA.transparent)
        setPrimaryImage(checked,checkedIndex)
        setBackgroundTextureFrame(primary,primaryIndex)
        isCheckBox=true
    }




}