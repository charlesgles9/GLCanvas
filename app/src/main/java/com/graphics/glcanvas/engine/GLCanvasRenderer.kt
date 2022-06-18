package com.graphics.glcanvas.engine

import android.content.Context
import android.opengl.GLES32
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.*
import com.graphics.glcanvas.engine.ui.*
import com.graphics.glcanvas.engine.ui.OnClickEvent.OnClickListener
import com.graphics.glcanvas.engine.utils.*

class GLCanvasRenderer(private val context: Context,width: Float, height: Float) : GLRendererWrapper(width, height) {

    private val batch = Batch(width,height)
    private val camera=Camera2D(1.0f)
    private val candara=Font("fonts/candara.fnt",context)
    private val harrington=Font("fonts/harrington.fnt",context)
    private val text=Text("My test paragraph.\n\nDead target zombies and monsters. Charge bro charge and let's kill every one of the scums.Let me test my skills using hardness and courage I am very holy.This is an amazing project good learning experience i am down for amazing work. Hello world people! it takes alot of hard work and commitment to be a good software engineer. one day i know i will triumph and rise above mediocrity. Most people live average mediocre ignorant lives and i must fight this thing inside me that makes me extremely lazy and foolish. I don't come from a rich background but i know I will one day rise to glory. This is the one struggle that i must win because I have tried so many times and failed over and over again. I promised myself that one day I will have a victory at last, all these years of struggle will pay off I can feel it. I must win!",0.3f,harrington)
    private var atlas:TextureAtlas?=null
    private var titleLabel:GLLabel?=null
    private var fpsLabel:GLLabel?=null
    private var labelDrawCall:GLLabel?=null
    private var imageCheckBox:GLImageCheckBox?=null
    private var progressBar:GLProgressBar?=null
    private var linearLayout:LinearLayoutConstraint?=null
    private var labelSound:GLLabel?=null
     //init camera here or resources eg textures
    override fun prepare() {
        batch.initShader(context)
        camera.setOrtho( getCanvasWidth(), getCanvasHeight())
        TextureLoader.getInstance().getTexture(context,"fonts/sans.png")
        TextureLoader.getInstance().getTexture(context,"fonts/harrington.png")
        TextureLoader.getInstance().getTexture(context,"fonts/candara.png")
        TextureLoader.getInstance().getTexture(context,"textures/ui/wenrexa/Background_green.png")

         atlas= TextureAtlas("textures/ui/UI.atlas",context)

         titleLabel= GLLabel(380f,100f, atlas!!,"Textfield2",harrington,"HELLO GUI!",0.3f )
         titleLabel?.setRippleColor(ColorRGBA(0f,1f,0.1f,0.5f))
         titleLabel?.setOnClickListener(object :OnClickListener{
             override fun onClick() {

             }
         })

         fpsLabel= GLLabel(200f,60f,candara,"FPS: 60",0.3f)
         fpsLabel?.setBackgroundColor(ColorRGBA.transparent)
         labelDrawCall= GLLabel(getCanvasWidth()*0.8f,60f,atlas!!,"Textfield2",candara,"DrawCalls: ",0.3f)
         fpsLabel?.setBackgroundColor(ColorRGBA.transparent)

         labelSound= GLLabel(200f,50f,atlas!!,"Textfield1",harrington,"Enable Sound",0.2f)
         labelSound?.setBackgroundColor(ColorRGBA(1f,0f,0f,1f))
         labelSound?.getTextView()?.setOutlineColor(1f,0f,1f)
         labelSound?.getTextView()?.setInnerEdge(0.1f)
         labelSound?.getTextView()?.setInnerWidth(0.4f)
         labelSound?.getConstraints()?.layoutMarginRight(15f)

         imageCheckBox=GLImageCheckBox(50f,50f, atlas!!,"Checkbox1","Checkmark1")
         imageCheckBox?.set(300f,600f)
         imageCheckBox?.setOnClickListener(object :OnClickListener{
             override fun onClick() {

             }
         })

         progressBar= GLProgressBar(300f,25f,80f,true)
         progressBar?.set(200f,700f)
         progressBar?.roundedCorner(2f)
         progressBar?.setForegroundColor(ColorRGBA(1f,0f,0f,0.7f))
         progressBar?.setOnClickListener(object :OnClickListener{
             override fun onClick() {

             }
         })


         val titleLayout=RelativeLayoutConstraint(getCanvasWidth(),250f,atlas!!,"Window1")
         titleLabel?.getConstraints()?.layoutMarginTop(5f)
         fpsLabel?.getConstraints()?.layoutMarginLeft(100f)
         fpsLabel?.getConstraints()?.layoutMarginTop(20f)
         fpsLabel?.getConstraints()?.alignAbove(titleLabel!!)
         labelDrawCall?.getConstraints()?.alignCenterHorizontal(titleLayout)
         labelDrawCall?.getConstraints()?.alignBelow(titleLabel!!)
         titleLayout.setItems(mutableListOf(titleLabel!!,labelDrawCall!!,fpsLabel!!))

         linearLayout= LinearLayoutConstraint(getCanvasWidth(), getCanvasHeight(),atlas!!,"Background2")
         linearLayout?.setPosition(0f,0f)
         linearLayout?.setColor(ColorRGBA(1f,1f,1f,1f))
         progressBar?.getConstraints()?.layoutMarginBottom(20f)
         imageCheckBox?.getConstraints()?.layoutMarginLeft(20f)
         val inner=RelativeLayoutConstraint(400f,180f,atlas!!,"Window2")
         inner.setBackgroundColor(ColorRGBA())
         inner.setItems(mutableListOf(labelSound!!,imageCheckBox!!,progressBar!!))
         labelSound?.getConstraints()?.layoutMarginLeft(35f)
         imageCheckBox?.getConstraints()?.toRightOf(labelSound!!)
         titleLabel?.getConstraints()?.alignCenterHorizontal(titleLayout)
         titleLabel?.getConstraints()?.alignCenterVertical(titleLayout)
         progressBar?.getConstraints()?.alignBelow(labelSound!!)
         progressBar?.getConstraints()?.layoutMarginLeft(25f)

         val scrollView=GLScrollLayout(getCanvasWidth(),400f,atlas!!,"Window3")
         scrollView.setBackgroundColor(ColorRGBA(1f,1f,1f,1f))
         scrollView.setOrientation(GLScrollLayout.HORIZONTAL)
         val scrollList= mutableListOf<GLView>()
         scrollView.setItems(scrollList)
         scrollView.addOnSwipeEvent(object :GLOnSwipeEvent.OnSwipeListener{
             override fun onSwipe() {

             }
         })

         val gridView=GLGridLayout(scrollView,scrollView.width,scrollView.height,4,10)
             gridView.setBackgroundColor(ColorRGBA.transparent)
             gridView.getConstraints().layoutMarginBottom(20f)
         val gridList= mutableListOf<GLView>()
         for(i in 0 until 40)
             gridList.add(genLabel("label"))
         gridView.setItems(gridList)
         scrollList.add(gridView)
         scrollView.setItems(scrollList)
         linearLayout?.setItems(mutableListOf(titleLayout,scrollView,inner))

         getRenderer().getTouchController()?.addEvent(titleLabel!!)
         getRenderer().getTouchController()?.addEvent(imageCheckBox!!)
         getRenderer().getTouchController()?.addEvent(imageCheckBox!!)
         getRenderer().getTouchController()?.addEvent(progressBar!!)
         getRenderer().getTouchController()?.addEvent(scrollView)
        text.set(100f,690f)
        text.setMaxWidth(600f)
        getRenderer().fpsCap(60)
         fpsLabel?.getTextView()?.setOutlineColor(1f,0f,1f)
         fpsLabel?.getTextView()?.setInnerEdge(0.2f)
         fpsLabel?.getTextView()?.setInnerWidth(0.4f)
         fpsLabel?.getTextView()?.setBorderEdge(0.2f)
         fpsLabel?.getTextView()?.setBorderWidth(0.4f)

    }

    private fun genLabel(message:String):GLLabel{
        val lbl= GLLabel(120f,70f,harrington,message,0.3f)
        lbl.setBackgroundColor(ColorRGBA.transparent)
        lbl.getTextView()?.setOutlineColor(1f,0f,1f)
        lbl.getTextView()?.setInnerEdge(0.1f)
        lbl.getTextView()?.setInnerWidth(0.4f)
        lbl.getConstraints().layoutMarginTop(5f)
        return lbl
    }


    override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0.5f,0.5f,0.5f,0.5f)

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