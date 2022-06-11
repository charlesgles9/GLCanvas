package com.graphics.glcanvas.engine

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent


@SuppressLint("ViewConstructor")
class GLCanvasSurfaceView(context: Context, renderer: GLCanvasRenderer) : GLSurfaceView(context) {

    private val controller=TouchController()
    init {
        setEGLContextClientVersion(3)
        holder.setFixedSize(renderer.getCanvasWidth().toInt(), renderer.getCanvasHeight().toInt())
        renderer.getRenderer().setController(controller)
        setRenderer(renderer.getRenderer())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        controller.getEvents().forEach{
            it.onTouchEvent(event)
        }
        return true
    }

 inner class TouchController{
     private val events= mutableListOf<Touch>()
     fun addEvent(touch: Touch){
         events.add(touch)
     }
     fun getEvents():MutableList<Touch>{
         return events
     }
 }

}