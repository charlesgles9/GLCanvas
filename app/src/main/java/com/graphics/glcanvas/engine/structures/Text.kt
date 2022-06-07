package com.graphics.glcanvas.engine.structures

import android.content.Context
import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.utils.TextureLoader

class Text(private var str:String,private var font: Font) {
    private val words=ArrayList<Word>()
    private val color=ColorRGBA()
    init {
        splitText()
    }
    private fun splitText(){
        val array=str.split(" ")
        words.clear()
        val cursor=Vector2f()
        // word spacing
        val space=20f
        array.forEach {
            words.add(Word(it,font,cursor,0.6f,color))
            cursor.addX(space*0.6f)
        }
    }

    fun setColor(color: ColorRGBA){
        this.color.set(color)
    }

    fun setText(str: String){
        this.str=str
        splitText()
    }

    fun getText():String{
        return str
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