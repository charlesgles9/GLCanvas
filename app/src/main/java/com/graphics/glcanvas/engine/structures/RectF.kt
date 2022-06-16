package com.graphics.glcanvas.engine.structures
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.maths.Vector3f
import com.graphics.glcanvas.engine.utils.SpriteAnimator

open class RectF :Vertex {
    private val position=Vector3f()
    private var width=0.0f
    private var height=0.0f
    private var connerRadius=0.0f
    private var thickness=0.0f
    private var clipUpper=Vector2f(Float.MIN_VALUE, Float.MIN_VALUE)
    private var clipLower=Vector2f(Float.MAX_VALUE, Float.MAX_VALUE)
    private var animator:SpriteAnimator?=null
    constructor():super(4,4){}
    constructor(x:Float,y:Float,width:Float,height:Float):super(4,4){
        this.width=width
        this.height=height
        this.position.set(x,y,0.0f)
    }

     fun set(x:Float, y:Float){
        position.setValueX(x)
        position.setValueY(y)
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

    open fun getX():Float{
        return position.x
    }

    open fun getY():Float{
        return position.y
    }

    open fun setWidth(width: Float){
        this.width=width
    }

    open fun setHeight(height: Float){
        this.height=height
    }

    open fun getWidth():Float{
        return width
    }

    open fun getHeight():Float{
        return height
    }



    fun setThickness(thickness:Float){
        this.thickness=thickness
    }

    fun geThickness():Float{
        return thickness
    }
    fun setConnerRadius(connerRadius:Float){
        this.connerRadius=connerRadius
    }

    fun getConnerRadius():Float{
        return connerRadius
    }

    fun setAnimator(animator: SpriteAnimator){
        this.animator=animator
    }

    fun getAnimator():SpriteAnimator?{
        return animator
    }

   fun update(delta:Long){
       animator?.update(delta)
   }

}