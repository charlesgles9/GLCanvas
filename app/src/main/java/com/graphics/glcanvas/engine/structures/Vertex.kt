package com.graphics.glcanvas.engine.structures

import android.content.Context
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.maths.Vector3f
import com.graphics.glcanvas.engine.utils.SpriteSheet
import com.graphics.glcanvas.engine.utils.Texture
import com.graphics.glcanvas.engine.utils.TextureLoader

open class Vertex(pSize: Int) {
    private var visible=true
    private var positions= MutableList(pSize,init = { Vector3f() })
    private var colors=MutableList(pSize,init = {ColorRGBA(1.0f,1.0f,1.0f,1.0f) })
    private var scale=Vector2f(1f,1f)
    private var spriteSheet= SpriteSheet(1,1)
    private var texture=Texture()
    private var angleX=0f
    private var angleY=0f
    private var angleZ=0f
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
        return spriteSheet
    }

    fun setSpriteSheet(spriteSheet: SpriteSheet?){
        this.spriteSheet=spriteSheet!!
    }

    open fun gradient(gradient:MutableList<ColorRGBA>){
        for(i in colors.indices){
            colors[i]=gradient[i]
        }
    }

    fun setColor(color:ColorRGBA){
        colors.forEach { it.set(color) }
    }

    fun setScale(sx:Float,sy:Float){
        scale.set(sx,sy)
    }

    fun setVisibility(visible:Boolean){
        this.visible=visible
    }

    fun getVisibility():Boolean{
        return visible
    }

    fun getColor(index:Int):ColorRGBA{
        return colors[index]
    }

    fun getColor():ColorRGBA{
        return colors[0]
    }

    fun getPosition(index: Int):Vector3f{
        return positions[index]
    }

    fun getScale():Vector2f{
        return scale
    }

    fun vertexCount():Int{
        return positions.size
    }

    fun getTextureCords():FloatArray{
        return spriteSheet.getCurrentFrame()
    }

    fun setRotationX(angleX:Float){
        this.angleX=angleX
    }

    fun setRotationY(angleY:Float){
        this.angleY=angleY
    }

    fun setRotationZ(angleZ:Float){
        this.angleZ=angleZ
    }

    fun getRotationX():Float{
        return angleX
    }
    fun getRotationY():Float{
        return angleY
    }
    fun getRotationZ():Float{
        return angleZ
    }
}