package com.graphics.glcanvas.engine.structures

import android.content.Context
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector3f
import com.graphics.glcanvas.engine.utils.SpriteSheet
import com.graphics.glcanvas.engine.utils.Texture
import com.graphics.glcanvas.engine.utils.TextureLoader

open class Vertex(pSize: Int, tSize: Int) {
    private var positions: MutableList<Vector3f>? =null
    private var colors:MutableList<ColorRGBA>?=null
    private var spriteSheet:SpriteSheet?=null
    private var texture=Texture()
    init {
        positions= MutableList(pSize,init = { Vector3f() })
        colors=MutableList(pSize,init = {ColorRGBA(1.0f,1.0f,1.0f,1.0f) })
        spriteSheet= SpriteSheet(1,1)
    }
    fun setTexture(context: Context, path:String){
        this.texture.load(context, path)
    }
    fun setTexture(texture:Texture){
        this.texture=texture
    }

    fun getTexture():Texture{
        return texture
    }

    fun getSpriteSheet():SpriteSheet{
        return spriteSheet!!
    }

    fun gradient(start:ColorRGBA, stop:ColorRGBA){
        // must be a square shape
        if(colors!!.size==4){
            val color1=colors!![0]
            val color2=colors!![1]
            val color3=colors!![2]
            val color4=colors!![3]
            color1.set(start)
            color2.set(start)
            color3.set(stop)
            color4.set(stop)
        }
        // else must be a triangle
    }

    fun setColor(color:ColorRGBA){
        colors!!.forEach { it.set(color) }
    }

    fun getColor(index:Int):ColorRGBA{
        return colors!![index]
    }

    fun getPosition(index: Int):Vector3f{
        return positions!![index]
    }

    fun vertexCount():Int{
        return positions!!.size
    }

    fun getTextureCords():FloatArray{
        return spriteSheet!!.getCurrentFrame()
    }

}