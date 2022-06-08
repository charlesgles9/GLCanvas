package com.graphics.glcanvas.engine.structures

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.utils.TextureLoader

class Text(private var text:String,private var fontSize:Float,private var font: Font) {
    private val words=ArrayList<Word>()
    private val color=ColorRGBA()
    private val position=Vector2f()
    private var maxWidth=Float.MAX_VALUE
    init {
        splitText()
    }
    private fun splitText(){
        val array=text.split(" ")
        words.clear()
        val cursor=Vector2f()
        // word spacing
        val space=20f
        array.forEach {
            words.add(Word(it,font,cursor,fontSize,color,position,maxWidth))
            cursor.addX(space*fontSize)
        }

    }

    fun set(x:Float,y:Float){
        this.position.set(x,y)
        splitText()
    }

    fun setX(x:Float){
        this.position.x=x
    }

    fun setY(y:Float){
        this.position.y=y
    }

    fun setMaxWidth(maxWidth:Float){
        this.maxWidth=maxWidth
        splitText()
    }

    fun setFontSize(fontSize: Float){
        this.fontSize=fontSize
        splitText()
    }

    fun setColor(color: ColorRGBA){
        this.color.set(color)
    }

    fun setText(text: String){
        this.text=text
        splitText()
    }

    fun getText():String{
        return text
    }

    fun draw(batch: Batch){
        words.forEach { word->
            word.getCharacter().forEach {
                it.setTexture(TextureLoader.getInstance().getTexture(font.getTextureAtlasPath()))
                batch.draw(it)
            }
        }
    }

}