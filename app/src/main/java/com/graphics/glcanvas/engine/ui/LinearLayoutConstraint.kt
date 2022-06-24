package com.graphics.glcanvas.engine.ui

import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.utils.TextureAtlas


class LinearLayoutConstraint(private val parent:GLView?,width:Float,height:Float):GLView(width ,height) {


    private var items= mutableListOf<GLView>()
    private var orientation= VERTICAL
    private var offset=Vector2f()
    constructor(parent:GLView?,width:Float,height:Float,atlas: TextureAtlas,name:String):this(parent,width, height){
        this.atlas=atlas
        this.name=name
       setBackgroundAtlas(atlas, name)
    }

    fun setBackgroundAtlas(atlas: TextureAtlas, name:String){
        setBackgroundTextureAtlas(atlas)
        setPrimaryImage(name)
        setBackgroundTextureFrame(name)
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

    override fun setVisibility(visible: Boolean) {
        super.setVisibility(visible)
        for(view in items){
            view.setVisibility(visible)
        }
    }
    override fun setZ(z: Float) {
        super.setZ(z)
        items.forEach{
            it.setZ(z)
        }
    }

   fun getItems():MutableList<GLView>{
       return items
   }

    fun setOrientation(orientation:Int){
        this.orientation=orientation
    }


    override fun setEnabled(enable: Boolean) {
        super.setEnabled(enable)
        items.forEach {
                it.setEnabled(enable)
        }
    }

    override fun draw(batch: Batch) {
        super.draw(batch)
        LayoutConstraint.groupItems(orientation,offset ,this,items)
        if(isVisible())
        items.forEach {

            LayoutConstraint.clipView(parent?:this,this,it)
            it.draw(batch)
        }

    }
}