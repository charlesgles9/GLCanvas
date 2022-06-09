package com.graphics.glcanvas.engine.structures

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.utils.TextureLoader

class Text(private var text:String,private var fontSize:Float,private var font: Font) {
    private val words=ArrayList<Word>()
    private val color=ColorRGBA()
    private val position=Vector2f()
    private var outline=ColorRGBA()
    private var innerEdge=0f
    private var innerWidth=0f
    private var borderWidth=0f
    private var borderEdge=0f
    private var maxWidth=Float.MAX_VALUE
    init {
        splitText()
    }

    private fun splitText(){
        val paragraphs=text.split("\n")
        words.clear()
        val cursor=Vector2f()
        if(paragraphs.isNotEmpty()) {
            paragraphs.forEach {
                splitParagraph(it, cursor)
                //move to next line
                cursor.x=0f
                cursor.y+=font.lineHeight*fontSize
            }
        }else
            splitParagraph(text, cursor)
    }

    private fun splitParagraph(text: String,cursor:Vector2f){
        val array=text.split(" ")
        // word spacing
        val space=20f
        array.forEach {
            words.add(Word(it,font,cursor,fontSize,color,outline,innerEdge, innerWidth,
                                           borderWidth, borderEdge, position, maxWidth))
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

    fun setOutlineColor(outline:ColorRGBA){
        this.outline=outline
    }

    fun setOutlineColor(r:Float,g:Float,b:Float){
        this.outline.set(r,g,b)
    }

    fun setInnerEdge(innerEdge:Float){
        this.innerEdge=innerEdge
    }

    fun setInnerWidth(innerWidth:Float){
        this.innerWidth=innerWidth
    }

    fun setBorderWidth(borderWidth:Float){
        this.borderWidth=borderWidth
    }

    fun setBorderEdge(borderEdge:Float){
        this.borderEdge=borderEdge
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