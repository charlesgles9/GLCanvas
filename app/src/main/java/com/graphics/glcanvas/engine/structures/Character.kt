package com.graphics.glcanvas.engine.structures

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f



class Character(private var char:Char,private val font:Font): RectF(0f,0f,50f,50f) {
    private val xAdvance=0f
    private val offset=Vector2f()
     fun set(x:Float, y:Float,width: Float,height: Float){
         val meta=font.getCharMetaData(char)
          set(x,y)
          setWidth(width)
          setHeight(height)
        getSpriteSheet().setSTMatrix(
            meta!!.getX(),meta.getY(),
            meta.getWidth(),meta.getHeight(),
            font.scaleW,font.scaleH,0)

    }

    fun getXAdvance():Float{
        return xAdvance
    }

    fun getOffset():Vector2f{
        return offset
    }

    fun getChar():Char{
        return char
    }

}