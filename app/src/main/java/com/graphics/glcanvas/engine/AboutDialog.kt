package com.graphics.glcanvas.engine

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.ui.*
import com.graphics.glcanvas.engine.utils.TextureAtlas

class AboutDialog(parent:GLView, atlas: TextureAtlas,font: Font, controller:GLCanvasSurfaceView.TouchController?, width:Float, height:Float) {
    private var scrollLayout=GLScrollLayout(width*0.8f, height*0.4f,atlas,"Card",0)
    private var layout= LinearLayoutConstraint(scrollLayout,width*0.8f, height*0.5f)
    private var title=GLLabel(150f,50f,font,"About",0.4f)
    private var description=GLLabel(layout.width*0.9f,height*0.8f,font,"Hello this is an openGL renderer created by charlie.\n\n You can contribute to the project at my github page @charlesgles9.\n\n I created this for fun and out of curiosity I wanted to demystify some UI concepts.\n\n I also wanted to create a simple but efficient renderer for android phones.\n\n\n\n\n I will keep on improving it and adding more cool features.",0.2f)

    private var visible=false
    init {
        scrollLayout.addItem(layout)
        scrollLayout.setOrientation(GLScrollLayout.VERTICAL)
        scrollLayout.set(width*0.5f,height*0.5f)
        layout.addItem(title)
        layout.addItem(description)
        scrollLayout.setZ(parent.getZ()+1f)
        layout.setBackgroundColor(ColorRGBA.transparent)
        description.setCenterText(false)
        description.getConstraints().layoutMarginTop(50f)
        description.getConstraints().layoutMarginLeft(10f)
        description.getConstraints().layoutMarginBottom(20f)
        title.getConstraints().alignCenterHorizontal(layout)
        title.getConstraints().layoutMarginTop(20f)
        title.setTextColor(ColorRGBA.red)
        scrollLayout.addOnSwipeEvent(object :GLOnSwipeEvent.OnSwipeListener{
           override fun onSwipe() {

           }
       })
        scrollLayout.setScrollBarProgressColor(ColorRGBA.red)
        controller?.getEvents()?.add(scrollLayout)
    }


    fun draw(batch: Batch){
        if(visible)
        scrollLayout.draw(batch)
    }

    fun show(flag:Boolean){
        this.visible=flag

    }

    fun isShowing():Boolean{
        return visible
    }
}