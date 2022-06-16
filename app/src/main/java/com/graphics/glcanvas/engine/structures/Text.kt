package com.graphics.glcanvas.engine.structures

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.utils.TextureLoader
import kotlin.math.max

class Text(private var text:String,private var fontSize:Float,private var font: Font) {
    private val words=ArrayList<Word>()
    private val color=ColorRGBA()
    val position=Vector2f()
    private var outline=ColorRGBA()
    private var clipUpper=Vector2f(1f, Float.MIN_VALUE)
    private var clipLower=Vector2f(1f, Float.MAX_VALUE)
    private var innerEdge=0f
    private var innerWidth=0f
    private var borderWidth=0f
    private var borderEdge=0f
    private var maxWidth=Float.MAX_VALUE
    private var maxHeight=Float.MAX_VALUE
    var width=0f
    var height=0f
    init {
        splitText()
    }

    private fun splitText(){
        val paragraphs=text.split("\n")
        words.clear()
        val cursor=Vector2f()
        if(paragraphs.isNotEmpty()) {
            for(item in paragraphs){
                splitParagraph(item, cursor)
                width= max(cursor.x,width)
                height=max(cursor.y,height)
                //move to next line
                cursor.x=0f
                cursor.y+=font.lineHeight*fontSize

            }
        }else {
            splitParagraph(text, cursor)
            width= max(cursor.x,width)
            height=font.lineHeight*fontSize

        }
    }

    private fun splitParagraph(text: String,cursor:Vector2f){
        val array=text.split(" ")
        // word spacing
        val space=20f
        for(item in array){
            words.add(Word(item,font,cursor,fontSize,clipUpper,clipLower,color,outline,innerEdge, innerWidth,
                                           borderWidth, borderEdge, position, maxWidth,maxHeight))
            cursor.addX(space*fontSize)

        }
    }

    fun set(x:Float,y:Float){
        // update only if necessary
        if(x!=position.x||position.y!=y) {
            this.position.set(x, y)
            splitText()
        }
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

    fun setMaxHeight(maxHeight:Float){
        this.maxHeight=maxHeight
    }

    fun getMaxWidth():Float{
        return maxWidth
    }

    fun getMaxHeight():Float{
        return maxHeight
    }
    fun setFontSize(fontSize: Float){
        this.fontSize=fontSize
        splitText()
    }

    fun setColor(color: ColorRGBA){
        this.color.set(color)
        words.forEach { word->
            word.getCharacter().forEach {
              it.setColor(color)
            }
        }
    }

    fun setText(text: String){
        if(text!=this.text) {
            this.text = text
            splitText()
        }
    }

    fun setClipUpper(x:Float,y:Float) {
        clipUpper.set(x,y)

    }

    fun setClipLower(x:Float,y:Float) {
        clipLower.set(x,y)
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
        val id=TextureLoader.getInstance().getTexture(font.getTextureAtlasPath())
        words.forEach { word->
            word.getCharacter().forEach {
                it.getTexture().setId(id)
                batch.draw(it)
            }
        }
    }

}