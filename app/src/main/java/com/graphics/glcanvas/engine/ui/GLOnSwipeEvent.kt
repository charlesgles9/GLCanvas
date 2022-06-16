package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import com.graphics.glcanvas.engine.Touch
import com.graphics.glcanvas.engine.maths.Vector2f
import kotlin.math.abs


class GLOnSwipeEvent (private val listener: GLOnSwipeEvent.OnSwipeListener,
                      private val view:GLView): Touch {
    private val threshHold=2f
    private val MAX=50f
    private val velocity=Vector2f(0f,0f)
    private var origin=Vector2f(-1f,-1f)
    private var move=Vector2f(-1f,-1f)
     var UP=false
     var DOWN=false
     var LEFT=false
     var RIGHT=false
    companion object {
        var friction = 0.8f
    }
    private var pointerDown=false
    fun contains(x:Float,y:Float):Boolean{
        return view.contains(x,y)
    }

    fun getVelocity():Vector2f{
        return velocity
    }

    fun setVelocity(x:Float,y:Float){
        this.velocity.set(x,y)
    }


    override fun onTouchEvent(event: MotionEvent) {
        if(event.action ==MotionEvent.ACTION_DOWN){
            origin.set(event.x-view.getThumbSize()/2,event.y-view.getThumbSize()/2)
            ScreenRatio.getInstance().project(origin)
            pointerDown= contains(origin.x,origin.y)
           // println("Down!")
        }else if(event.action ==MotionEvent.ACTION_UP&&pointerDown){
            origin.set(-1f,-1f)
            velocity.set(0f,0f)
            pointerDown=false
        }else if(event.action ==MotionEvent.ACTION_MOVE&&pointerDown){
            move.set(event.x-view.getThumbSize()/2,event.y-view.getThumbSize()/2)
            ScreenRatio.getInstance().project(move)
            pointerDown=contains(move.x,move.y)
            if(pointerDown) {
                listener.onSwipe()
                val dirx=(origin.x - move.x+1 )/ abs(origin.x - move.x +1)
                val diry=(origin.y - move.y +1)/ abs(origin.y - move.y+1 )
                velocity.set(
                    dirx* threshHold,
                    diry * threshHold
                )
                UP=velocity.y<0
                DOWN=velocity.y>0
                LEFT=velocity.x<0
                RIGHT=velocity.x>0
                println("LEFT $LEFT")
                println("RIGHT $RIGHT")
                //println("UP: $UP")
                //println("DOWN: $DOWN")
                //origin.set(move)
              /*  println("Move")
                move.print()
                println("Origin")
                origin.print()
                println("Velocity")*/
              //  velocity.print()
            }
            println("Swiped!")

        }
    }

    interface OnSwipeListener{
       fun onSwipe()
    }

}