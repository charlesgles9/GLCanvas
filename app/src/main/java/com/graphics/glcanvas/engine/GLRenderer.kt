package com.graphics.glcanvas.engine
import android.opengl.GLES32
import android.opengl.GLSurfaceView
import android.os.SystemClock
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLRenderer(private val updatable: Updatable) : GLSurfaceView.Renderer {

    //start time
    private var st=SystemClock.elapsedRealtimeNanos()/1000000L
    //fps limit default at 60 to prevent vsync
    private var cap=60
    //expected cycle
    private var ms_per_frame=1000L/cap
    override fun onDrawFrame(gl: GL10?) {
        val time= SystemClock.elapsedRealtimeNanos()/1000000L
        val elapsed=time-st
        if(elapsed>=ms_per_frame){
            updatable.draw( )
            updatable.update(time)
            st+=ms_per_frame
        }

    }

    fun fpsCap(cap:Int){
        this.cap=cap
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