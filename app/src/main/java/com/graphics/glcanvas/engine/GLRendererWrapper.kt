package com.graphics.glcanvas.engine


open class GLRendererWrapper( width: Float, height: Float) :Updatable {
    companion object{
        var RESOLUTION_WIDTH= 0f
        var RESOLUTION_HEIGHT=0f
    }

    init {
        RESOLUTION_HEIGHT=height
        RESOLUTION_WIDTH=width

    }
    private val renderer = GLRenderer(this)

    override fun draw() {


    }

    override fun update(delta: Long) {

    }

    override fun prepare() {


    }

    fun getRenderer(): GLRenderer {
        return renderer
    }

    fun fpsCap(cap:Int){
        renderer.fpsCap(cap+10)
    }

    fun getCanvasWidth():Float{
       return RESOLUTION_WIDTH
    }

    fun getCanvasHeight():Float{
        return RESOLUTION_HEIGHT
    }
}