package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import com.graphics.glcanvas.engine.Touch
import com.graphics.glcanvas.engine.maths.Vector2f

class OnClickEvent(private val listener:OnClickListener,
                   private val view:GLView):Touch {

    private var pointerDown=false
    private val screen=Vector2f()
    private val display=Vector2f()
    private var position=Vector2f(-1f,-1f)
    fun contains(x:Float,y:Float):Boolean{
        return view.contains(x,y)
    }

    override fun onTouchEvent(event: MotionEvent) {
         if(event.action ==MotionEvent.ACTION_DOWN){
             position.set(event.x-view.getThumbSize()/2,event.y-view.getThumbSize()/2)
             println("x "+event.x+" y "+event.y)
             ScreenRatio.getInstance().project(position)
             pointerDown= contains(position.x,position.y)
         }else if(event.action ==MotionEvent.ACTION_UP&&pointerDown){
             position.set(-1f,-1f)
             listener.onClick()
             //for checkboxes
             view.setChecked(!view.getChecked())
             pointerDown=false
         }else if(event.action ==MotionEvent.ACTION_MOVE&&pointerDown){
             position.set(event.x-view.getThumbSize()/2,event.y-view.getThumbSize())
             ScreenRatio.getInstance().project(position)
             pointerDown=contains(position.x,position.y)
         }
    }

    fun getPointerDown():Boolean{
        return pointerDown
    }

    fun getPosition():Vector2f{
        return position
    }

    interface  OnClickListener{
       fun onClick(){

        }
    }
}