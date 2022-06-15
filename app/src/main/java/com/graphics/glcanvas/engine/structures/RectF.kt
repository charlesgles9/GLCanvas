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
    private var trim=Vector2f(1f,1f)
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

    fun setTrim(x:Float,y:Float){
        trim.set(x,y)

    }

    fun getTrim():Vector2f{
        return trim
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