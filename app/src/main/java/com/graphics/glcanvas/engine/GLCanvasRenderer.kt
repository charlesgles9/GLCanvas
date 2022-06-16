package com.graphics.glcanvas.engine

import android.content.Context
import android.opengl.GLES32
import android.view.MotionEvent
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
    private val textFPS=Text("FPS: 60",0.8f, candara)
    private val background=RectF(width/2,height/2,width, height)
    private var atlas:TextureAtlas?=null
    private var button:GLImageButton?=null
    private var label:GLLabel?=null
    private var checkBox:GLCheckBox?=null
    private var imageCheckBox:GLImageCheckBox?=null
    private var progressBar:GLProgressBar?=null
    private var linearLayout:LinearLayoutConstraint?=null
    private var labelSound:GLLabel?=null
     //init camera here or resources eg textures
    override fun prepare() {
        batch.initShader(context)
        camera.setOrtho( getCanvasWidth(), getCanvasHeight(),900f,1600f)
        TextureLoader.getInstance().getTexture(context,"fonts/sans.png")
        TextureLoader.getInstance().getTexture(context,"fonts/harrington.png")
        TextureLoader.getInstance().getTexture(context,"fonts/candara.png")
         atlas= TextureAtlas("textures/ui/wenrexa/wenrexa.atlas",context)
         button= GLImageButton(100f,50f,atlas!!,"Checked1")
         button?.set(200f,200f)
         button?.setRippleColor(ColorRGBA(1f,0f,0f,1f))
         button?.setOnClickListener( object :OnClickListener{
             override fun onClick(){

             }
         })

         label= GLLabel(250f,100f, atlas!!,"PanelWindow",candara,"Hello world!",0.3f )
         label?.set(280f,300f)
         label?.roundedCorner(50f)
         label?.setRippleColor(ColorRGBA(0f,1f,0.1f,0.5f))
         label?.setOnClickListener(object :OnClickListener{
             override fun onClick() {

             }
         })

         labelSound= GLLabel(150f,80f,candara,"Enable",0.3f)
         labelSound?.setBackgroundColor(ColorRGBA(1f,0f,0f,1f))
         labelSound?.getTextView()?.setOutlineColor(1f,0f,1f)
         labelSound?.getTextView()?.setInnerEdge(0.1f)
         labelSound?.getTextView()?.setInnerWidth(0.4f)
         checkBox= GLCheckBox(60f,60f, ColorRGBA(1f,0f,0.3f,1f))
         checkBox?.set(300f,460f)
         checkBox?.roundedCorner(10f)
         checkBox?.setRippleColor(ColorRGBA(1f,1f,0f,1f))
         checkBox?.setCheckedBackground(ColorRGBA(0f,1f,1f,1f))
         checkBox?.setOnClickListener(object :OnClickListener{
             override fun onClick() {

             }
         })

         imageCheckBox=GLImageCheckBox(100f,100f, atlas!!,"Checked1","Checked2")
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


         linearLayout= LinearLayoutConstraint(600f,800f)
         linearLayout?.setPosition(50f,100f)
         linearLayout?.setColor(ColorRGBA(0f,1f,0.3f,0.5f))
         progressBar?.getConstraints()?.layoutMarginBottom(20f)
         label?.getConstraints()?.layoutMarginBottom(20f)
         checkBox?.getConstraints()?.layoutMarginLeft(20f)
         val inner=LinearLayoutConstraint(250f,180f)
         inner.setBackgroundColor(ColorRGBA())
         inner.getConstraints().layoutMarginBottom(20f)
         inner.setOrientation(LinearLayoutConstraint.HORIZONTAL)
         inner.setItems(mutableListOf(labelSound!!,checkBox!!))
         checkBox?.getConstraints()?.alignCenterVertical(labelSound!!)
         label?.getConstraints()?.alignCenterHorizontal(linearLayout!!)
         val scrollView=GLScrollLayout(350f,350f)
         scrollView.setBackgroundColor(ColorRGBA(1f,1f,1f,1f))
         scrollView.setOrientation(GLScrollLayout.HORIZONTAL)
         val scrollList= mutableListOf<GLView>()
         for(i in 0 until 10){
             scrollList.add(genLabel("label $i"))
         }
         scrollView.setItems(scrollList)
         linearLayout?.setItems(mutableListOf(label!!,progressBar!!,inner,scrollView))

         scrollView.addOnSwipeEvent(object :GLOnSwipeEvent.OnSwipeListener{
             override fun onSwipe() {

             }
         })
         getRenderer().getTouchController()?.addEvent(button!!)
         getRenderer().getTouchController()?.addEvent(label!!)
         getRenderer().getTouchController()?.addEvent(checkBox!!)
         getRenderer().getTouchController()?.addEvent(progressBar!!)
         getRenderer().getTouchController()?.addEvent(scrollView)
        background.setTexture(context,"textures/ui/wenrexa/Background_green.png")
        text.set(100f,690f)
        text.setMaxWidth(600f)
        FpsCounter.setGUITextView(textFPS)
        textFPS.set(30f,30f)
        getRenderer().fpsCap(60)
        textFPS.setOutlineColor(1f,0f,1f)
        textFPS.setInnerEdge(0.2f)
        textFPS.setInnerWidth(0.4f)
        textFPS.setBorderEdge(0.2f)
        textFPS.setBorderWidth(0.4f)
    }

    private fun genLabel(message:String):GLLabel{
        val lbl= GLLabel(150f,80f,harrington,message,0.3f)
        lbl.setBackgroundColor(ColorRGBA(1f,0f,1f,1f))
        lbl.getTextView()?.setOutlineColor(1f,0f,1f)
        lbl.getTextView()?.setInnerEdge(0.1f)
        lbl.getTextView()?.setInnerWidth(0.4f)
        lbl.getConstraints().layoutMarginBottom(5f)
        return lbl
    }


    override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0.5f,0.5f,0.5f,0.5f)
        batch.setMode(BatchQueue.ORDER)
        batch.begin(camera)
        batch.draw(background)
        text.draw(batch)
        batch.end()
        // draw ui
        batch.setMode(BatchQueue.UNORDER)
        batch.begin(camera)
        linearLayout?.draw(batch)
        batch.end()

        batch.begin(camera)
        FpsCounter.getInstance().draw(batch)
        batch.end()


    }

    override fun update(delta: Long) {
        FpsCounter.getInstance().update(delta)
    }



}