package com.graphics.glcanvas.engine.structures

class Character(private var char:Char,private val font:Font): RectF(0f,0f,50f,50f) {
     fun set(x:Float, y:Float,width: Float,height: Float){
         val meta=font.getCharMetaData(char)
          set(x,y)
          setWidth(width)
          setHeight(height)
        getSpriteSheet().setSTMatrix(meta!!.getX(),meta.getY(),
            meta.getWidth(),meta.getHeight(),
            font.scaleW,font.scaleH,0)

    }

    fun getChar():Char{
        return char
    }

}