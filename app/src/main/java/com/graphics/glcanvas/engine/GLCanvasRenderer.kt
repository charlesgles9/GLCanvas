package com.graphics.glcanvas.engine

import android.content.Context
import android.opengl.GLES32
import android.os.SystemClock
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.*
import com.graphics.glcanvas.engine.ui.GLTextButton
import com.graphics.glcanvas.engine.utils.*

class GLCanvasRenderer(private val context: Context,width: Float, height: Float) : GLRendererWrapper(width, height) {

    private val batch = Batch(width,height)
    private val camera=Camera2D(1.0f)
    private val text=Text("My test paragraph.\n\nDead target zombies and monsters. Charge bro charge and let's kill every one of the scums.Let me test my skills using hardness and courage I am very holy.This is an amazing project good learning experience i am down for amazing work. Hello world people! it takes alot of hard work and commitment to be a good software engineer. one day i know i will triumph and rise above mediocrity. Most people live average mediocre ignorant lives and i must fight this thing inside me that makes me extremely lazy and foolish. I don't come from a rich background but i know I will one day rise to glory. This is the one struggle that i must win because I have tried so many times and failed over and over again. I promised myself that one day I will have a victory at last, all these years of struggle will pay off I can feel it. I must win!",0.3f,Font("fonts/harrington.fnt",context))
    private val textFPS=Text("FPS: 60",0.8f, Font("fonts/candara.fnt",context))
    private val background=RectF(width/2,height/2,width, height)
    private var atlas:TextureAtlas?=null
    private var button:GLTextButton?=null
     //init camera here or resources eg textures
    override fun prepare() {
        batch.initShader(context)
        camera.setOrtho( getCanvasWidth(), getCanvasHeight())
        TextureLoader.getInstance().getTexture(context,"fonts/sans.png")
        TextureLoader.getInstance().getTexture(context,"fonts/harrington.png")
        TextureLoader.getInstance().getTexture(context,"fonts/candara.png")
         atlas= TextureAtlas("textures/ui/wenrexa/wenrexa.atlas",context)
         button= GLTextButton(100f,50f,atlas!!,"Checked2")
         button?.set(200f,200f)
         getRenderer().getController()?.addEvent(button!!)
        background.setTexture(context,"textures/ui/wenrexa/Background_green.png")
        text.set(100f,690f)
        text.setMaxWidth(600f)
        FpsCounter.setGUITextView(textFPS)
        textFPS.set(30f,30f)
        getRenderer().fpsCap(60)
        textFPS.setOutlineColor(1f,0f,1f)
        textFPS.setInnerEdge(0.2f)
        textFPS.setInnerWidth(0.4f)
    }



    override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0.5f,0.5f,0.5f,0.5f)
        batch.begin(camera)
        batch.draw(background)
        text.draw(batch)
        button?.draw(batch)
        FpsCounter.getInstance().draw(batch)
        batch.end()

    }

    override fun update(delta: Long) {
        FpsCounter.getInstance().update(delta)
    }



}