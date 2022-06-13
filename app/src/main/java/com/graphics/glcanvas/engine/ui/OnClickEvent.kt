package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import com.graphics.glcanvas.engine.Touch

class OnClickEvent(private val listener:OnClickListener,
                   private val view:GLView):Touch {

    private var pointerDown=false
    fun contains(x:Float,y:Float):Boolean{
        return view.contains(x,y)
    }

    override fun onTouchEvent(event: MotionEvent?) {
         if(event?.action==MotionEvent.ACTION_DOWN){
             pointerDown= contains(event.x-view.getThumbSize()/2,event.y-view.getThumbSize())
         }else if(event?.action==MotionEvent.ACTION_UP&&pointerDown){
             listener.onClick()
             //for checkboxes
             view.setChecked(!view.getChecked())
             pointerDown=false
         }else if(event?.action==MotionEvent.ACTION_MOVE&&pointerDown){
             pointerDown=contains(event.x-view.getThumbSize()/2,event.y-view.getThumbSize())
         }
    }

    fun getPointerDown():Boolean{
        return pointerDown
    }

    interface  OnClickListener{
       fun onClick(){

        }
    }
}