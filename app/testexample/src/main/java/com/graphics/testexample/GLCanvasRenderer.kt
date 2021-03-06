package com.graphics.testexample

import android.content.Context
import android.opengl.GLES32
import com.graphics.glcanvas.engine.*
import com.graphics.glcanvas.engine.structures.*
import com.graphics.glcanvas.engine.utils.*

class GLCanvasRenderer(private val context: Context,width: Float, height: Float) : GLRendererView(width, height) {

    private val batch = Batch()
    private val camera= Camera2D(10.0f)
    private var atlas:TextureAtlas?=null
    private var home: HomeScreen?=null

    //init camera here or resources eg textures
    override fun prepare() {
        batch.initShader(context)
        camera.setOrtho( getCanvasWidth(), getCanvasHeight())
        TextureLoader.getInstance().getTexture(context,"fonts/sans.png")
        TextureLoader.getInstance().getTexture(context,"textures/ui/test_UI.png")
        atlas= TextureAtlas("textures/ui/test_UI.atlas",context)
        home= HomeScreen(atlas!!,Font("fonts/sans.fnt",context),getController(),getCanvasWidth(), getCanvasHeight())
    }

    private var rect=RectF(300f,300f,100f,100f)
    private var circle=Circle(100f,600f,50f)
    private var polyline=PolyLine()
    private var angle=0f

    override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0f,0f,0f,1f)

        batch.setMode(BatchQueue.UNORDER)
        batch.begin(camera)
        home?.draw(batch)
        batch.end()
        rect.setTexture(atlas?.getTexture()!!)

        batch.begin(camera)
        batch.draw(rect)
        batch.end()

        batch.begin(camera)
        batch.draw(circle)
        batch.end()

        polyline.moveTo(100f,100f)
        polyline.lineTo(200f,100f)
        polyline.moveTo(200f,100f)
        polyline.lineTo(200f,300f)
        polyline.moveTo(50f,300f)
        polyline.lineTo(200f,300f)
        polyline.moveTo(50f,300f)
        polyline.lineTo(50f,400f)
        polyline.moveTo(50f,400f)
        polyline.lineTo(100f,400f)

        batch.setMode(BatchQueue.UNORDER)
        batch.begin(camera)
        batch.draw(polyline)
        batch.end()

        rect.setAngleZ(angle)
        angle+=1f
        angle %= 360
        polyline.reset()
    }

    override fun update(delta: Long) {
        batch.resetStats()

    }



}