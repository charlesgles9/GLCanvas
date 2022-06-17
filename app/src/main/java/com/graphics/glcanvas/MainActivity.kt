package com.graphics.glcanvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.graphics.glcanvas.engine.GLCanvasRenderer
import com.graphics.glcanvas.engine.GLCanvasSurfaceView


class MainActivity : AppCompatActivity() {

    private var surface:GLCanvasSurfaceView?=null
    private var renderer:GLCanvasRenderer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        renderer=GLCanvasRenderer(this ,720.0f,1024.0f)
        surface= GLCanvasSurfaceView(this, renderer!!)
        setContentView(surface)


    }
}