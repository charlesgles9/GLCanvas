package com.graphics.glcanvas.engine

import android.content.Context
import android.opengl.GLES32
import android.os.SystemClock
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.*
import com.graphics.glcanvas.engine.utils.FpsCounter
import com.graphics.glcanvas.engine.utils.TextureLoader

class GLCanvasRenderer(private val context: Context,width: Float, height: Float) : GLRendererWrapper(width, height) {

    private val batch = Batch(width,height)
    private val camera=Camera2D(1.0f)
    private val rect=RectF(300.0f,100.0f,190.0f,90.0f)
    private val block=RectF(300.0f,390.0f,190.0f,190.0f)
    private val line=Line(200.0f,200.0f,300.0f,200.0f)
    private val text=Text("My test paragraph.\n\nThis is an amazing project good learning experience i am down for amazing work. Hello world people! it takes alot of hard work and commitment to be a good software engineer. one day i know i will triumph and rise above mediocrity. Most people live average mediocre ignorant lives and i must fight this thing inside me that makes me extremely lazy and foolish. I don't come from a rich background but i know I will one day rise to glory. This is the one struggle that i must win because I have tried so many times and failed over and over again. I promised myself that one day I will have a victory at last, all these years of struggle will pay off I can feel it. I must win!",0.3f,Font("fonts/sans.fnt",context))
    private val polyLine=PolyLine()
    private val polygon=Polygon()
    private val circle=Circle(300f,580f,100f)
    private val textFPS=Text("FPS: 60",0.4f, Font("fonts/harrington.fnt",context))
    private val drawCalls=Text("DrawCalls",0.4f,Font("fonts/sans.fnt",context))
    private val triangles=Text("Triangles",0.4f,Font("fonts/sans.fnt",context))
    // init camera here or resources
    override fun prepare() {
        batch.initShader(context)
        camera.setOrtho( getCanvasWidth(), getCanvasHeight())
        TextureLoader.getInstance().getTexture(context,"fonts/sans.png")
        TextureLoader.getInstance().getTexture(context,"fonts/harrington.png")

        rect.gradient(ColorRGBA(1.0f,0.0f,0.0f,0.0f),
            ColorRGBA(0.0f,1.0f,0.0f,0.0f))
        block.setColor(ColorRGBA(0.5f,1f,0f,1f))
        block.setTexture(context,"fonts/sans.png")
        rect.setConnerRadius(20f)
        rect.setThickness(20f)
        polyLine.moveTo(300.0f,200.0f)
        polyLine.lineTo(300.0f,500.0f)
        polyLine.lineColor(ColorRGBA(0.0f,1.0f,0.0f,1.0f))
        polyLine.moveTo(300.0f,500.0f)
        polyLine.lineTo(400.0f,500.0f)
        polyLine.lineColor(ColorRGBA(1.0f,0.0f,0.0f,1.0f))
        polygon.moveTo(300f,300f)
        polygon.lineTo(300f,380f)
        polygon.lineTo(400f,380f)
        polygon.lineColor(ColorRGBA(0.51f,0f,0f,.6f))
        circle.setColor(ColorRGBA(1f,1f,0f,1f))
        circle.setThickness(25f)

        text.set(200f,690f)
        text.setMaxWidth(450f)

        FpsCounter.setGUITextView(textFPS)

        textFPS.set(30f,30f)

        drawCalls.set(500f,30f)
        drawCalls.setColor(ColorRGBA(1f,1f,0f,1f))
        triangles.set(500f,80f)
        triangles.setColor(ColorRGBA(1f,1f,0f,1f))
        getRenderer().fpsCap(60)
    }



    override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0.5f,0.5f,0.5f,0.5f)
        batch.begin(camera)
        batch.draw(circle)
        batch.draw(line)
        batch.draw(rect)
        batch.draw(polyLine)
        batch.draw(block)
        batch.draw(polygon)
        text.draw(batch)
        drawCalls.draw(batch)
        triangles.draw(batch)
        FpsCounter.getInstance().draw(batch)
        batch.end()
        drawCalls.setText("DrawCalls: "+batch.getDrawCallCount())
        triangles.setText("Triangles: "+batch.getTriangleCount())
    }

    override fun update(delta: Long) {
        FpsCounter.getInstance().update(delta)
    }



}