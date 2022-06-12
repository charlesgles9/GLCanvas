package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.Touch
import com.graphics.glcanvas.engine.Update
import com.graphics.glcanvas.engine.maths.AxisABB
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.structures.RectF
import com.graphics.glcanvas.engine.structures.Text
import com.graphics.glcanvas.engine.utils.Texture
import com.graphics.glcanvas.engine.utils.TextureAtlas
import kotlin.math.abs


open class GLView(width:Float,height:Float) :GLLayoutParams(width, height),Update, Touch{

      private var background= RectF(0f,0f,width, height)
      private var foreground=RectF(0f,0f,width, height)
      protected var text:Text?=null
      private var secondary:RectF?=null
      private var ripple=ColorRGBA(0f,0f,0f,0f)
      private var default=ColorRGBA()
      private var collision=AxisABB()
      private val thumb=50f
      private val position=Vector2f()
      private val onClickEvents= mutableListOf<OnClickEvent>()
      init {
          foreground.setColor(ColorRGBA(1f,1f,1f,0f))
      }

      fun setBackgroundColor(color: ColorRGBA){
            background.setColor(color)
            default.set(color)
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
          position.set(x,y)
      }

      fun setX(x:Float){
          position.x=x
      }

      fun setY(y:Float){
          position.y=y
      }

      override fun draw(batch: Batch) {
          background.set(position.x,position.y)
          foreground.set(position.x,position.y)
          secondary?.set(position.x,position.y)
          batch.draw(background)
      //    batch.draw(foreground)
          background.setWidth(width)
          background.setHeight(height)
          foreground.setWidth(width)
          foreground.setHeight(height)
          text?.draw(batch)
          // center the text if available
          val tw= (text?.width?.times(0.5f)?: 0f)
          val th=(text?.height?.times(0.5f)?:0f)
          text?.set(position.x-tw, position.y-th)
          // update click events
          onClickEvents.forEach {
              if(it.getPointerDown())
                      background.setColor(ripple)
                  else
                      background.setColor(default)
          }
      }


    fun clearOnClick(){
        onClickEvents.clear()
    }

    protected fun getBackground():RectF{
        return background
    }

    protected fun getSecondary():RectF?{
        return secondary
    }
    override fun update(delta: Long) {

    }

   fun getX():Float{
       return position.x
   }

    fun getY():Float{
        return position.y
    }

    fun getThumbSize():Float{
        return thumb
    }
    fun contains(x:Float,y:Float):Boolean{
        return collision.isIntersecting(background.getX(),
                                 background.getY(),
                                 background.getWidth(),
                                 background.getHeight(),x,y,thumb,thumb)

    }

    fun setOnClickListener(onclick: OnClickEvent.OnClickListener){
        onClickEvents.add(OnClickEvent(onclick,this))
    }

    override fun onTouchEvent(event: MotionEvent?) {
            onClickEvents.forEach {
                it.onTouchEvent(event)
           /* MotionEvent.ACTION_DOWN->{
                if(contains(event.x,event.y))
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
                if(contains(event.x,event.y))
                    background.setColor(ripple)
                else
                    background.setColor(default)
            }*/

        }
    }




}