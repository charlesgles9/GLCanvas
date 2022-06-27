package com.graphics.glcanvas.engine.ui

import android.view.MotionEvent
import com.graphics.glcanvas.engine.Batch
import com.graphics.glcanvas.engine.GLCanvasSurfaceView
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.utils.TextureAtlas

class GLDropDown (width:Float, height:Float,
                  private var font: Font, private var string:String, private var size: Float)
                  : GLLabel(width,height,font, string, size)  {
    private var scrollView:GLScrollLayout?=null
    private var itemDropMaxHeight=Float.MAX_VALUE
    init {
        setText(string,font,size)
    }
    constructor(width: Float, height: Float, atlas: TextureAtlas, name: String, index:Int, font:Font, string: String, size: Float)
            :this(width, height,font,string,size) {
        this.atlas=atlas
        this.font=font
        this.string=string
        this.size=size
        setBackgroundTextureAtlas(atlas)
        imageFromAtlas(name,index)
        setPrimaryImage(name,index)
        setBackgroundTextureFrame(name,index)
        setText(string,font,size)
        setRippleColor(ColorRGBA.white)
        setDefaultColor(ColorRGBA.white)
    }

    fun toggleDropDown(){
        val bool=scrollView?.isVisible()?:false
        scrollView?.setVisibility(!bool)
    }

    fun setDropMaxHeight(itemDropMaxHeight:Float){
        this.itemDropMaxHeight=itemDropMaxHeight
    }

    fun setItems(strings:MutableList<String>){
        //calculate maximum height of the wrapper
          var totalViewHeight=0f
          val views= mutableListOf<GLView>()
           strings.forEach {
               views.add(genLabel(it))
           }
        for (view in views) {
            totalViewHeight += view.height
            view.setVisibility(false)
        }
        itemDropMaxHeight=if(itemDropMaxHeight== Float.MAX_VALUE)totalViewHeight else itemDropMaxHeight
        scrollView=GLScrollLayout(width, itemDropMaxHeight)
        scrollView?.setOrientation(GLScrollLayout.VERTICAL)
        val layout=LinearLayoutConstraint(scrollView,width, totalViewHeight)
        layout.setOrientation(LinearLayoutConstraint.VERTICAL)
        layout.setBackgroundColor(ColorRGBA.transparent)
        layout.setItems(views)
        scrollView?.setItems(mutableListOf(layout))
        scrollView?.setScrollBarProgressColor(ColorRGBA.white)
        scrollView?.setScrollBarBackgroundColor(ColorRGBA.red)
        scrollView?.getConstraints()?.alignBelow(this)
        scrollView?.getConstraints()?.alignCenterHorizontal(this)
        scrollView?.setVisibility(false)
        scrollView?.setZ(this.getZ()+1f)
    }

    private fun genLabel(message:String):GLLabel{
        val lbl= GLLabel(width,height,font,message,size)
        lbl.setBackgroundColor(ColorRGBA.transparent)
        lbl.getTextView()?.setOutlineColor(1f,0f,1f)
        lbl.getTextView()?.setInnerEdge(0.1f)
        lbl.getTextView()?.setInnerWidth(0.4f)
        lbl.getConstraints().layoutMarginTop(5f)
        return lbl
    }

    fun setBackgroundAtlas(atlas: TextureAtlas, name:String,index: Int){
        scrollView?.setBackgroundAtlas(atlas, name,index)
    }


    override fun draw(batch: Batch) {
        super.draw(batch)
        scrollView?.draw(batch)

    }

    fun addEvents(controller:GLCanvasSurfaceView.TouchController?){
         scrollView?.addOnSwipeEvent(object :GLOnSwipeEvent.OnSwipeListener{
             override fun onSwipe() {

             }
         })
        this.setOnClickListener(object :OnClickEvent.OnClickListener{
            override fun onClick() {
                toggleDropDown()
            }
        })
        scrollView?.setEnabled(true)
        controller?.addEvent(this)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
         scrollView?.onTouchEvent(event)

        return super.onTouchEvent(event)
    }
}