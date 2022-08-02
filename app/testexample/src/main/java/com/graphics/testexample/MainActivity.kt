package com.graphics.testexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.graphics.glcanvas.engine.GLCanvasSurfaceView

class MainActivity : AppCompatActivity() {

    private var surface: GLCanvasSurfaceView?=null
    private var renderer:GLCanvasRenderer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        renderer=GLCanvasRenderer(this ,600.0f,1024.0f)
        surface= GLCanvasSurfaceView(this, renderer!!)
        setContentView(surface)

    }


    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,"Hello!",Toast.LENGTH_SHORT).show()
        renderer?.onRelease()
    }
}
