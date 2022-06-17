package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.utils.TextureAtlas


class LinearLayoutConstraint(width:Float,height:Float):GLView(width ,height) {


    private var items= mutableListOf<GLView>()
    private var orientation= VERTICAL
    private var offset=Vector2f()

    constructor(width:Float,height:Float,atlas: TextureAtlas,name:String):this(width, height){
        this.atlas=atlas
        this.name=name
        setBackgroundTextureAtlas(atlas)
        setPrimaryImage(name)
        setBackgroundFrame(name)
    }
    companion object{
        const val VERTICAL=0
        const val HORIZONTAL=1
    }
    //push this view from center origin 0.5,0.5 -> 0,0
     fun setPosition(x:Float,y:Float){
        set(x+width*0.5f,y+height*0.5f)
    }

    fun setColor(color:ColorRGBA){
        setBackgroundColor(color)
    }
    fun setItems(items:MutableList<GLView>){
        this.items=items
        LayoutConstraint.groupItems(orientation,offset ,this,items)
    }
   fun getItems():MutableList<GLView>{
       return items
   }
    fun setOrientation(orientation:Int){
        this.orientation=orientation
    }


    override fun draw(batch: Batch) {
        super.draw(batch)
        LayoutConstraint.groupItems(orientation,offset ,this,items)
        items.forEach {
            LayoutConstraint.clipView(this,it)
            it.draw(batch)
        }

    }
}