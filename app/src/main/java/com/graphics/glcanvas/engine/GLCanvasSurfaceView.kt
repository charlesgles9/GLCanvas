package com.graphics.glcanvas.engine

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.widget.Toast
import com.graphics.glcanvas.engine.ui.ScreenRatio


@SuppressLint("ViewConstructor")
class GLCanvasSurfaceView(context: Context, private val renderer: GLCanvasRenderer) : GLSurfaceView(context) {

    private val controller=TouchController()
    init {
        setEGLContextClientVersion(3)
        val w=resources.displayMetrics.widthPixels
        val h=resources.displayMetrics.heightPixels
        ScreenRatio.getInstance().setDisplayScreen(w.toFloat(),h.toFloat())
        holder.setFixedSize(renderer.getCanvasWidth().toInt(), renderer.getCanvasHeight().toInt())
        ScreenRatio.getInstance().setSurfaceScreen(renderer.getCanvasWidth(),renderer.getCanvasHeight())
        renderer.getRenderer().setController(controller)
        setRenderer(renderer.getRenderer())
        Toast.makeText(getContext(), "W= $w H= $h",Toast.LENGTH_LONG).show()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        queueEvent {
            controller.getEvents().forEach{
                it.onTouchEvent(event)
            }
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