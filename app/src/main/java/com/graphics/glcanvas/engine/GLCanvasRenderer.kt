package com.graphics.glcanvas.engine

import android.content.Context
import android.opengl.GLES32
import android.widget.Toast
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.maths.Vector2f
import com.graphics.glcanvas.engine.structures.*
import com.graphics.glcanvas.engine.ui.*
import com.graphics.glcanvas.engine.ui.OnClickEvent.OnClickListener
import com.graphics.glcanvas.engine.utils.*

class GLCanvasRenderer(private val context: Context,width: Float, height: Float) : GLRendererWrapper(width, height) {

    private val batch = Batch(width,height)
    private val camera=Camera2D(10.0f)
    private val candara=Font("fonts/candara.fnt",context)
    private val harrington=Font("fonts/harrington.fnt",context)
    private val text=Text("Ideas are shit and worthless what really matters is execution. The idea focuses mostly on the event, execution is the difficult process that makes the event possible.\n\nTime passes, dreams die and what remains? An old withered body forlorn for what could have been.\n\n I once heard that a smart man learns from his mistakes. A wise man learns from the mistakes of others. The fear of failure is normal yet failure creates experince and experience breeds wisdom.\n\n All skill growth is derived from the friction and the struggle in the process. There will be no growth if you are spoon fed the answers with zero struggles of your own. You can't move boulders until you've learnt how to move the pebbles. This is a piece of paragraph to test an annoying scrollView implementation, I have spent hours and hours, commit after commit. Every time thinking i've solved it to no avail.",0.3f,harrington)
    private var atlas:TextureAtlas?=null
    private var titleLabel:GLLabel?=null
    private var fpsLabel:GLLabel?=null
    private var labelDrawCall:GLLabel?=null
    private var imageCheckBox:GLImageCheckBox?=null
    private var progressBar:GLProgressBar?=null
    private var linearLayout:LinearLayoutConstraint?=null
    private var labelSound:GLLabel?=null
    private var labelParagraph:GLLabel?=null
    private var dropDown:GLDropDown?=null
     //init camera here or resources eg textures
    override fun prepare() {
        batch.initShader(context)
        camera.setOrtho( getCanvasWidth(), getCanvasHeight())
        TextureLoader.getInstance().getTexture(context,"fonts/sans.png")
        TextureLoader.getInstance().getTexture(context,"fonts/harrington.png")
        TextureLoader.getInstance().getTexture(context,"fonts/candara.png")
        TextureLoader.getInstance().getTexture(context,"textures/ui/wenrexa/Background_green.png")

         atlas= TextureAtlas("textures/ui/UI.atlas",context)

         titleLabel= GLLabel(380f,100f, atlas!!,"Textfield2",0,candara,"HELLO GUI!",0.3f )
         titleLabel?.setRippleColor(ColorRGBA(0f,1f,0.1f,0.5f))
         titleLabel?.setMultiTouchListener(object :MultiTouchEvent.OnMultiTouchListener{
             override fun onTouch(vector2f: Vector2f) {

             }

             override fun onRelease() {

             }
         })


         fpsLabel= GLLabel(200f,60f,candara,"FPS: 60",0.3f)
         fpsLabel?.setBackgroundColor(ColorRGBA.transparent)
         labelDrawCall= GLLabel(getCanvasWidth()*0.8f,60f,atlas!!,"Textfield2",0,candara,"DrawCalls: ",0.3f)
         fpsLabel?.setBackgroundColor(ColorRGBA.transparent)

         labelSound= GLLabel(200f,50f,atlas!!,"Textfield2",0,candara,"Enable Sound",0.2f)
         labelSound?.setBackgroundColor(ColorRGBA(1f,0f,0f,1f))
         labelSound?.getTextView()?.setOutlineColor(1f,0f,1f)
         labelSound?.getTextView()?.setInnerEdge(0.1f)
         labelSound?.getTextView()?.setInnerWidth(0.4f)
         labelSound?.getConstraints()?.layoutMarginRight(15f)

         imageCheckBox=GLImageCheckBox(50f,50f, atlas!!,"Checkbox1",0,"Checkmark1",0)
         imageCheckBox?.set(300f,600f)
         imageCheckBox?.setOnClickListener(object :OnClickListener{
             override fun onClick() {

             }
         })

         progressBar= GLProgressBar(300f,25f,80f,true,atlas!!,"Expbar2",0,"Expbar1",0)
         progressBar?.set(200f,700f)
         progressBar?.setForegroundColor(ColorRGBA(1f,1f,1f,1f))
         progressBar?.setOnClickListener(object :OnClickListener{
             override fun onClick() {

             }
         })


         val titleLayout=RelativeLayoutConstraint(getCanvasWidth(),250f,atlas!!,"Window1",0)
         titleLabel?.getConstraints()?.layoutMarginTop(5f)
         fpsLabel?.getConstraints()?.layoutMarginLeft(100f)
         fpsLabel?.getConstraints()?.layoutMarginTop(20f)
         fpsLabel?.getConstraints()?.alignAbove(titleLabel!!)
         labelDrawCall?.getConstraints()?.alignCenterHorizontal(titleLayout)
         labelDrawCall?.getConstraints()?.alignBelow(titleLabel!!)
         titleLayout.setItems(mutableListOf(titleLabel!!,labelDrawCall!!,fpsLabel!!))

         linearLayout= LinearLayoutConstraint(null,getCanvasWidth(), getCanvasHeight(),atlas!!,"Background2",0)
         linearLayout?.setPosition(0f,0f)
         linearLayout?.setColor(ColorRGBA(1f,1f,1f,1f))
         progressBar?.getConstraints()?.layoutMarginBottom(20f)
         imageCheckBox?.getConstraints()?.layoutMarginLeft(20f)
         val inner=RelativeLayoutConstraint(400f,180f,atlas!!,"Window2",0)
         inner.setBackgroundColor(ColorRGBA())
         inner.setItems(mutableListOf(labelSound!!,imageCheckBox!!,progressBar!!))
         labelSound?.getConstraints()?.layoutMarginLeft(35f)
         imageCheckBox?.getConstraints()?.toRightOf(labelSound!!)
         titleLabel?.getConstraints()?.alignCenterHorizontal(titleLayout)
         titleLabel?.getConstraints()?.alignCenterVertical(titleLayout)
         progressBar?.getConstraints()?.alignBelow(labelSound!!)
         progressBar?.getConstraints()?.layoutMarginLeft(25f)

         val scrollView=GLScrollLayout(getCanvasWidth(),180f,atlas!!,"Window2",0)
         scrollView.setBackgroundColor(ColorRGBA(1f,1f,1f,1f))
         scrollView.setScrollBarBackgroundColor(ColorRGBA.red)
         scrollView.setOrientation(GLScrollLayout.HORIZONTAL)
         val scrollList= mutableListOf<GLView>()
         scrollView.setItems(scrollList)
         scrollView.addOnSwipeEvent(object :GLOnSwipeEvent.OnSwipeListener{
             override fun onSwipe() {

             }
         })

         val gridView=GLGridLayout(scrollView,scrollView.width,scrollView.height,2,7)
             gridView.setBackgroundColor(ColorRGBA.transparent)
             gridView.getConstraints().layoutMarginBottom(20f)
         val gridList= mutableListOf<GLView>()
         for(i in 0 until 20)
             gridList.add(genLabel("label"))
         gridView.setItems(gridList)
         scrollList.add(gridView)
         scrollView.setItems(scrollList)

         gridView.setOnItemClickListener(object :OnItemClickEvent.OnItemClickListener{
             override fun onItemClick(view: GLView) {

             }
         })
         val paraScrollView=GLScrollLayout(getCanvasWidth(),200f,atlas!!,"Window1",0)
              paraScrollView.setOrientation(GLScrollLayout.VERTICAL)
         paraScrollView.setScrollBarBackgroundColor(ColorRGBA(1f,0f,0f,1f))

         val paraInner=LinearLayoutConstraint(paraScrollView,paraScrollView.width,800f)
             paraInner.setOrientation(LinearLayoutConstraint.VERTICAL)
             paraInner.setColor(ColorRGBA.transparent)

         labelParagraph= GLLabel(paraInner.width*0.8f,paraInner.height,harrington,text.getText(),0.25f)
         labelParagraph?.setBackgroundColor(ColorRGBA.transparent)
         labelParagraph?.setCenterText(false)
          paraInner.setItems(mutableListOf(labelParagraph!!))
          paraScrollView.setItems(mutableListOf(paraInner))
          paraScrollView.addOnSwipeEvent(object :GLOnSwipeEvent.OnSwipeListener{
              override fun onSwipe() {

              }
          })
          dropDown= GLDropDown(150f,40f,atlas!!,"Window1",0,harrington,"Hello",0.2f)
          dropDown?.setDropMaxHeight(200f)
          dropDown?.setItems(mutableListOf("kenya","sudan","ethiopia",
              "russia","iran","somalia","USA","israel","poland","UK",
              "UAE","ukraine","congo","germany","hungary","Czech"))
          dropDown?.setBackgroundAtlas(atlas!!,"Window1",0)
          dropDown?.addEvents(getRenderer().getTouchController())
         dropDown?.setOnItemClickListener(object :OnItemClickEvent.OnItemClickListener{
             override fun onItemClick(view: GLView) {

             }
         })
         linearLayout?.setItems(mutableListOf(titleLayout,scrollView,inner,paraScrollView,dropDown!!))

         getRenderer().getTouchController()?.addEvent(titleLabel!!)
         getRenderer().getTouchController()?.addEvent(imageCheckBox!!)
         getRenderer().getTouchController()?.addEvent(imageCheckBox!!)
         getRenderer().getTouchController()?.addEvent(progressBar!!)
         getRenderer().getTouchController()?.addEvent(scrollView)
         getRenderer().getTouchController()?.addEvent(paraScrollView)
         getRenderer().getTouchController()?.addEvent(dropDown!!)
        text.set(100f,690f,0f)
        text.setMaxWidth(600f)
        getRenderer().fpsCap(60)
         fpsLabel?.getTextView()?.setOutlineColor(1f,0f,1f)
         fpsLabel?.getTextView()?.setInnerEdge(0.2f)
         fpsLabel?.getTextView()?.setInnerWidth(0.4f)
         fpsLabel?.getTextView()?.setBorderEdge(0.2f)
         fpsLabel?.getTextView()?.setBorderWidth(0.4f)

    }

    private fun genLabel(message:String):GLLabel{
        val lbl= GLLabel(120f,70f,candara,message,0.3f)
        lbl.setBackgroundColor(ColorRGBA.transparent)
        lbl.getTextView()?.setOutlineColor(1f,0f,1f)
        lbl.getTextView()?.setInnerEdge(0.1f)
        lbl.getTextView()?.setInnerWidth(0.4f)
        lbl.getConstraints().layoutMarginTop(5f)
        return lbl
    }


    override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(1f,1f,1f,1f)

        // draw ui
        batch.setMode(BatchQueue.UNORDER)
        batch.begin(camera)
        linearLayout?.draw(batch)
        batch.end()

        fpsLabel?.getTextView()?.setText("FPS: "+FpsCounter.getInstance().getFps())
        labelDrawCall?.getTextView()?.setText("DrawCalls: "+batch.getDrawCallCount())
        batch.resetStats()

    }

    override fun update(delta: Long) {
        FpsCounter.getInstance().update(delta)
    }



}