package com.graphics.glcanvas.engine
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLRenderer(private val updatable: Updatable) : GLSurfaceView.Renderer {

    override fun onDrawFrame(gl: GL10?) {
        updatable.draw( )
        updatable.update(0.0f)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {

        GLES32.glViewport(0,0,width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        updatable.prepare()
        setTransparency(gl!!,true)
    }

     private fun setTransparency(gl:GL10, transparency:Boolean){
        if(transparency){
            gl.glEnable(GL10.GL_BLEND)
            gl.glBlendFunc(GL10.GL_ONE,GL10.GL_ONE_MINUS_SRC_ALPHA)
        }else{
            gl.glEnable(GL10.GL_BLEND)
        }
    }
}