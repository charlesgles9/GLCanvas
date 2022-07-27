package com.graphics.glcanvas.engine.structures

import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.maths.Vector3f


 class Point :Vertex{
    private val position= Vector3f()
    private var width=0.0f
    private var height=0.0f
    private var clipUpper= Vector2f(Float.MIN_VALUE, Float.MIN_VALUE)
    private var clipLower= Vector2f(Float.MAX_VALUE, Float.MAX_VALUE)
    constructor():super(1){}
    constructor(x:Float,y:Float,width:Float,height:Float):super(1){
        this.width=width
        this.height=height
        this.position.set(x,y,0.0f)
    }
    fun set(x:Float, y:Float){
        position.setValueX(x)
        position.setValueY(y)
    }
    fun setZ(z:Float){
        this.position.z=z
    }

    fun setClipUpper(upperX:Float,upperY:Float){
        clipUpper.set(upperX,upperY)
    }

    fun setClipLower(lowerX:Float,lowerY:Float){
        clipLower.set(lowerX,lowerY)
    }

    fun getClipUpper():Vector2f{
        return clipUpper
    }

    fun getClipLower():Vector2f{
        return clipLower
    }

     fun getX():Float{
        return position.x
    }

     fun getY():Float{
        return position.y
    }

     fun getZ():Float{
        return position.z
    }

     fun setWidth(width: Float){
        this.width=width
    }

     fun setHeight(height: Float){
        this.height=height
    }

     fun getWidth():Float{
        return width
    }

     fun getHeight():Float{
        return height
    }
}