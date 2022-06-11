package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import android.view.View
import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.Touch
import com.graphics.glcanvas.engine.Update
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.RectF
import com.graphics.glcanvas.engine.structures.Text
import com.graphics.glcanvas.engine.utils.Texture
import com.graphics.glcanvas.engine.utils.TextureAtlas


open class GLView(width:Float,height:Float) :GLLayoutParams(width, height),Update,Touch{

      private var background= RectF(0f,0f,width, height)
      private var foreground=RectF(0f,0f,width, height)
      protected var text:Text?=null
      private var secondary:RectF?=null
      private var ripple=ColorRGBA(0f,0f,0f,0f)
      private var default=ColorRGBA()
      init {
          foreground.setColor(ColorRGBA(1f,1f,1f,0f))
      }

      fun setBackgroundColor(color: ColorRGBA){
            background.setColor(color)
      }

      private fun setBackgroundImage(texture: Texture?){
         this.background.setTexture(texture!!)
      }

      fun setBackgroundTextureAtlas(atlas: TextureAtlas){
          background.setSpriteSheet(atlas.getSheet())
          setBackgroundImage(atlas.getTexture())
      }

     fun setSecondaryTextureAtlas(atlas: TextureAtlas){
        foreground.setSpriteSheet(atlas.getSheet())
        setSecondaryImage(atlas.getTexture())
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


    private fun setSecondaryImage(texture: Texture?){
        if(secondary==null&&texture!=null){
            secondary= RectF(background.getX(),background.getY(),width, height)
            secondary?.setTexture(texture )
        }else if(texture==null)
            secondary=null
    }

    fun setRippleColor(color: ColorRGBA){
        this.ripple.set(color)
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
      //    batch.draw(foreground)
          background.setWidth(width)
          background.setHeight(height)
          foreground.setWidth(width)
          foreground.setHeight(height)
          text?.draw(batch)

      }

    protected fun getBackground():RectF{
        return background
    }

    protected fun getSecondary():RectF?{
        return secondary
    }
      override fun update(delta: Long) {

      }


    override fun onTouchEvent(event: MotionEvent?) {

        when(event?.actionMasked?.and(event.action)){
            MotionEvent.ACTION_DOWN->{
               background.setColor(ripple)
            }

            MotionEvent.ACTION_UP->{
               background.setColor(default)
            }

            MotionEvent.ACTION_POINTER_DOWN->{
                println("Second finger Touched")
            }

            MotionEvent.ACTION_POINTER_UP->{
                println("Second finger Not Touch")
            }
            MotionEvent.ACTION_MOVE->{
                println("Moving")
            }

        }
    }




}