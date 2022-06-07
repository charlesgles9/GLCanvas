package com.graphics.glcanvas.engine

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent
@SuppressLint("ViewConstructor")
class GLCanvasSurfaceView(context: Context, renderer: GLCanvasRenderer) : GLSurfaceView(context) {


    init {
        setEGLContextClientVersion(3)
        holder.setFixedSize(renderer.getCanvasWidth().toInt(), renderer.getCanvasHeight().toInt())
        setRenderer(renderer.getRenderer())
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        println("Touch detected!")
        return super.onTouchEvent(event)
    }



}