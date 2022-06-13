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


open class GLView(width:Float,height:Float) :GLLayoutParams(width, height),Update, Touch{

      private var background= RectF(0f,0f,width, height)
      private var foreground=RectF(0f,0f,width, height)
      protected var text:Text?=null
      private var tp=""
      private var ts=""
      private var check=false
      protected var isCheckBox=false
      private   var clicked=false
      protected var atlas: TextureAtlas?=null
      protected var name:String?=null
      private var ripple=ColorRGBA(0f,0f,0f,0f)
      private var default=ColorRGBA()
      private var collision=AxisABB()
      private val thumb=50f
      private val position=Vector2f()
      private val onClickEvents= mutableListOf<OnClickEvent>()
      private val constraint=LayoutConstraint(this)
      private var center=true
      init {
          foreground.setColor(ColorRGBA(0f,0f,0f,0f))
      }

      fun setBackgroundColor(color: ColorRGBA){
            background.setColor(color)
            default.set(color)
      }

      private fun setBackgroundImage(texture: Texture?){
         this.background.setTexture(texture!!)
      }

      fun setBackgroundTextureAtlas(atlas: TextureAtlas){
          background.setSpriteSheet(atlas.getSheet()?.clone())
          setBackgroundImage(atlas.getTexture())
      }

     fun setSecondaryTextureAtlas(atlas: TextureAtlas){
        foreground.setSpriteSheet(atlas.getSheet())
     }

     fun setForegroundColor(color: ColorRGBA){
        this.foreground.setColor(color)
      }

     fun setBackgroundFrame(name:String){
        if(atlas!=null)
            getBackground().getSpriteSheet().setCurrentFrame(atlas!!.getTextureCoordinate(name))
    }

     fun setSecondaryImage(ts:String){
        this.ts=ts
    }

     fun setPrimaryImage(tp:String){
        this.tp=tp
    }

    fun setRippleColor(color: ColorRGBA){
        this.ripple.set(color)
    }

    open fun roundedCorner(value:Float){
        getBackground().setConnerRadius(value)
        getForeground().setConnerRadius(value)
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


     private fun applyMargin(){
         position.set(getX()+getConstraints().getMarginLeft(),getY())
         position.set(getX()-getConstraints().getMarginRight(),getY())
         position.set(getX(),getY()+getConstraints().getMarginTop())
         position.set(getX(),getY()-getConstraints().getMarginBottom())
     }

    private fun removeMargin(){
        position.set(getX()-getConstraints().getMarginLeft(),getY())
        position.set(getX()+getConstraints().getMarginRight(),getY())
        position.set(getX(),getY()-getConstraints().getMarginTop())
        position.set(getX(),getY()+getConstraints().getMarginBottom())
    }
      override fun draw(batch: Batch) {
          constraint.applyConstraints()
          applyMargin()
          background.set(position.x,position.y)
          foreground.set(position.x,position.y)
          batch.draw(background)
          batch.draw(foreground)
          background.setWidth(width)
          background.setHeight(height)
          foreground.setWidth(width)
          foreground.setHeight(height)
          // center the text if available
          var tw= (text?.width?.times(0.5f)?: 0f)
          var th=(text?.height?.times(0.5f)?:0f)
          tw=if(center)tw else 0f
          th=if(center)th else 0f
          text?.set(position.x-tw, position.y-th)
          // make sure this text width is less than the the view width
          if((width-tw)>=0)
           text?.draw(batch)
          // update click events
          onClickEvents.forEach {
              clicked = it.getPointerDown()

          }
          //click effects for views
          changeTextureAndColors(clicked)
          if(isCheckBox)
          changeTextureAndColors(check)
         // prevent the position from changing
          removeMargin()
      }

    fun setChecked(check:Boolean){
        this.check=check
    }

    fun getChecked():Boolean{
        return check
    }
    private fun changeTextureAndColors(flag:Boolean){
        if(flag){
            background.setColor(ripple)
            if(!ts.isEmpty())
                setBackgroundFrame(ts)
        }else {
            background.setColor(default)
            if (!tp.isEmpty())
                setBackgroundFrame(tp)
        }
    }
    fun centerText(center:Boolean){
        this.center=center
    }

    fun clearOnClick(){
        onClickEvents.clear()
    }

    protected fun getBackground():RectF{
        return background
    }

    protected fun getForeground():RectF{
        return foreground
    }

    override fun update(delta: Long) {

    }

   fun getX():Float{
       return position.x
   }

    fun getY():Float{
        return position.y
    }

    fun getConstraints():LayoutConstraint{
        return constraint
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