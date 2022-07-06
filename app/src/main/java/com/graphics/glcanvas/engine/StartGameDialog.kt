package com.graphics.glcanvas.engine

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.ui.*
import com.graphics.glcanvas.engine.utils.TextureAtlas

class StartGameDialog(parent:GLView,atlas: TextureAtlas, font: Font, controller:GLCanvasSurfaceView.TouchController?, width:Float, height:Float) {

    private var layout = LinearLayoutConstraint(null, width*0.8f, height*0.2f, atlas, "Card", 0)
    private var close = GLImageButton(50f, 50f, atlas)
    private var visible = false

    init {
        close.setBackgroundImageAtlas("Checked", 1)
        layout.setBackgroundSubTexture(atlas, "Card")
        layout.set(width * 0.5f, height * 0.5f)
        val text = GLLabel(
            layout.width*0.95f,
            100f,
            font,
            "There's no gameplay pal! hopefully one day i'll put this library to use.",
            0.3f
        )
        text.setCenterText(false)
        text.setTextColor(ColorRGBA.red)
        text.getConstraints().layoutMarginRight(10f)
        text.getConstraints().layoutMarginLeft(10f)
        layout.addItem(close)
        layout.addItem(text)
        close.getConstraints().layoutMarginRight(30f)
        close.getConstraints().layoutMarginTop(20f)
        close.getConstraints().alignEnd(layout)
        layout.setZ(parent.getZ() + 1f)
        close.setOnClickListener(object : OnClickEvent.OnClickListener {
            override fun onClick() {
                show(false)
                parent.setEnableTouchEvents(true)
            }
        })
        controller?.addEvent(close)

    }

    fun draw(batch: Batch) {
        if (visible)
            layout.draw(batch)
    }

    fun show(flag: Boolean) {
        this.visible = flag
        layout.setEnableTouchEvents(visible)
    }

    fun isShowing(): Boolean {
        return visible
    }
}