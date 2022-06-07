package com.graphics.glcanvas.engine.structures

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import kotlin.math.max

class Word(str:String, font: Font, cursor:Vector2f,size:Float,color: ColorRGBA,position:Vector2f,maxWidth:Float) {
    private val characters=ArrayList<Character>()
    init {
        var maxHeight=0f
        for(i in str.indices){
            val char=Character(str[i],font)
            val meta=font.getCharMetaData(str[i])
            //how further this character is spaced to the next
            val advance=meta!!.getAdvanceX()* size
            //character width and height
            val fontSizeX=meta.getWidth()* size
            val fontSizeY=meta.getHeight()* size
            maxHeight= max(maxHeight,fontSizeY)
            char.setColor(color)
            char.set(
                /* lets offset of x and y by advance/2 since our origin
                   is at the center of each quad. Took me hours to figure this shit out
                 */
                position.x+cursor.x+advance/2f,position.y+cursor.y+(meta.getOffsetY()* size)/2,
                fontSizeX,
                fontSizeY )
                characters.add(char)
            // subtract the padding for proper char spacing
            cursor.addX(advance-font.padding[Font.PADDING_LEFT])
           //@debug println("char = ${char.getChar()} w $fontSizeX h $fontSizeY advX ${meta!!.getAdvanceX()} OffsetX ${meta.getOffsetX()} OffsetY ${meta.getOffsetY()}")

            //move to next line if the cursor has reached maximum width
            if(cursor.x>=maxWidth){
                cursor.x=0f
                cursor.y+=maxHeight
            }
        }

    }
    fun getCharacter():ArrayList<Character>{
        return characters
    }
}
