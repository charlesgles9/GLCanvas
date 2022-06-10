package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import android.view.View
import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.Update
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.RectF
import com.graphics.glcanvas.engine.utils.Texture


open class GLView(width:Float,height:Float) :GLLayoutParams(width, height),Update,View.OnTouchListener{

      private var background= RectF(0f,0f,width, height)
      private var foreground=RectF(0f,0f,width, height)
      init {
          foreground.setColor(ColorRGBA(1f,1f,1f,0f))
      }

      fun setBackgroundColor(color: ColorRGBA){
            background.setColor(color)
      }

      fun setBackgroundImage(texture: Texture){
         this.background.setTexture(texture)
      }

      fun setForegroundColor(color: ColorRGBA){
        this.foreground.setColor(color)
      }

      fun set(x:Float,y:Float){
            background.set(x,y)
            foreground.set(x,y)
      }

      fun setX(x:Float){
            background.set(x,background.getY())
            foreground.set(x,foreground.getY())
      }

      fun setY(y:Float){
            background.set(background.getY(),y)
            foreground.set(foreground.getY(),y)
      }

      override fun draw(batch: Batch) {
          batch.draw(background)
          batch.draw(foreground)
          background.setWidth(width)
          background.setHeight(height)
          foreground.setWidth(width)
          foreground.setHeight(height)
      }

      override fun update(delta: Long) {

      }


      override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
            TODO("Not yet implemented")
      }


}