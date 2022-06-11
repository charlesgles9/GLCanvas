package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import android.view.View
import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.Update
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.RectF
import com.graphics.glcanvas.engine.structures.Text
import com.graphics.glcanvas.engine.utils.Texture


open class GLView(width:Float,height:Float) :GLLayoutParams(width, height),Update,View.OnTouchListener{

      private var background= RectF(0f,0f,width, height)
      private var foreground=RectF(0f,0f,width, height)
      protected var text:Text?=null
      private var secondary:RectF?=null
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

      fun setSecondaryColor(color: ColorRGBA?){
          if(secondary==null&&color!=null){
              secondary= RectF(background.getX(),background.getY(),width, height)
              secondary?.setColor(color)
          }else if(color==null)
              secondary=null
      }


    fun setSecondaryImage(texture: Texture?){
        if(secondary==null&&texture!=null){
            secondary= RectF(background.getX(),background.getY(),width, height)
            secondary?.setTexture(texture )
        }else if(texture==null)
            secondary=null
    }

      fun set(x:Float,y:Float){
          background.set(x,y)
          foreground.set(x,y)
          secondary?.set(x,y)
      }

      fun setX(x:Float){
          background.set(x,background.getY())
          foreground.set(x,foreground.getY())
          secondary?.getY()?.let { secondary?.set(x, it) }
      }

      fun setY(y:Float){
          background.set(background.getY(),y)
          foreground.set(foreground.getY(),y)
          secondary?.getX()?.let { secondary?.set(it, y) }
      }

      override fun draw(batch: Batch) {
          batch.draw(background)
          batch.draw(foreground)
          background.setWidth(width)
          background.setHeight(height)
          foreground.setWidth(width)
          foreground.setHeight(height)
          text?.draw(batch)

      }

      override fun update(delta: Long) {

      }


      override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
            TODO("Not yet implemented")
      }


}