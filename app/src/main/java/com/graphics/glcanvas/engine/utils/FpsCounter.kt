package com.graphics.glcanvas.engine.utils

import android.os.SystemClock
import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.structures.Text

class FpsCounter() {


    companion object{
        private val instance=FpsCounter()
        private var fps=60
        private var pt=0L
        private var dt=0L
        private var st=0L
        private var et=0L
        private var ms_per_frame=0L
        private var counter=0L
        private var delay=1000L
        private var text:Text?=null
        private var fpsCap=0L
        fun getInstance():FpsCounter{
            return instance
        }
        fun setGUITextView(view: Text){
           this.text=view
        }
        fun setCap(fpsCap:Long){
            this.fpsCap= fpsCap
        }
    }

   private fun limitFps(){
       if(fpsCap<=0)
           return
       ms_per_frame=1000L/ fpsCap
       et=SystemClock.currentThreadTimeMillis()
       dt=et- st
       if(dt< ms_per_frame)
           SystemClock.sleep(ms_per_frame-dt)
       st=SystemClock.currentThreadTimeMillis()

   }

    fun update(time:Long){
        val delta=pt+ delay
        limitFps()
        if(delta<=time){
            fps+= counter.toInt()
            fps=(fps*0.5f).toInt()
            text?.setText("FPS: $fps")
            pt=time
            counter=0
        }else {
            counter++
        }

    }


    fun draw(batch: Batch){
        text?.draw(batch)
    }

    fun getFps():Int{
        return fps
    }








}

