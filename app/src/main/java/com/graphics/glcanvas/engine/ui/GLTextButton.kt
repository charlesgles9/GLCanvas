package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.structures.Text

class GLTextButton(width:Float,height:Float) : GLView(width, height) {


    fun setText(string:String,font: Font,size:Float){
       text= Text(string,size,font)
    }



}