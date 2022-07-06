package com.graphics.glcanvas.engine

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.ui.*
import com.graphics.glcanvas.engine.utils.TextureAtlas

class SettingDialog(private val parent: GLView, atlas: TextureAtlas, font: Font,
                    controller:GLCanvasSurfaceView.TouchController?, width:Float, height:Float) {
    private var layout=RelativeLayoutConstraint(parent, width*0.8f, height*0.7f)
    private var close= GLImageButton(50f,50f,atlas)
    private var visible=false
    init {
        close.setBackgroundImageAtlas("Checked",1)
        layout.setBackgroundAtlas(atlas,"Card")
        layout.set(width*0.5f,height*0.5f)
        // title
        val title=GLLabel(150f,80f,font,"Settings",0.4f)
        //sound settings
        val soundLayout=LinearLayoutConstraint(layout,layout.width,50f)
            soundLayout.setOrientation(LinearLayoutConstraint.HORIZONTAL)
            soundLayout.setBackgroundColor(ColorRGBA.transparent)
        val checkSoundLabel=GLLabel(200f,50f,atlas,"Button Disable",0,font,"Enable Sound",0.3f)
        val checkSoundBox=GLImageCheckBox(50f,50f,atlas,"Checkbox",0,"Checkmark",0)
           checkSoundBox.getConstraints().alignCenterVertical(soundLayout)
           soundLayout.addItem(checkSoundLabel)
           soundLayout.addItem(checkSoundBox)
        soundLayout.getConstraints().layoutMarginLeft(30f)
        soundLayout.getConstraints().layoutMarginTop(50f)
        checkSoundBox.getConstraints().layoutMarginLeft(10f)
       //music settings
        val musicLayout=LinearLayoutConstraint(layout,layout.width,50f)
            musicLayout.setOrientation(LinearLayoutConstraint.HORIZONTAL)
            musicLayout.setBackgroundColor(ColorRGBA.transparent)
        val checkMusicLabel=GLLabel(200f,50f,atlas,"Button Disable",0,font,"Enable Music",0.3f)
        val checkMusicBox=GLImageCheckBox(50f,50f,atlas,"Checkbox",0,"Checkmark",0)
        checkMusicBox.getConstraints().alignCenterVertical(musicLayout)
        musicLayout.addItem(checkMusicLabel)
        musicLayout.addItem(checkMusicBox)
        musicLayout.getConstraints().layoutMarginLeft(30f)
        musicLayout.getConstraints().layoutMarginBottom(40f)
        checkMusicBox.getConstraints().layoutMarginLeft(10f)
      //progress bar
        val volumeLayout=LinearLayoutConstraint(layout,layout.width,50f)
            volumeLayout.setOrientation(LinearLayoutConstraint.VERTICAL)
            volumeLayout.setBackgroundColor(ColorRGBA.transparent)
        val volumeLabel=GLLabel(100f,50f,font,"Volume",0.3f)
        val volumeProgress=GLProgressBar(layout.width*0.8f,30f,50f,
            true,atlas,"SliderEmpty",0,"CurrencyContainer",0)
        volumeLayout.getConstraints().layoutMarginLeft(30f)
        volumeLayout.getConstraints().layoutMarginBottom(30f)
        volumeLayout.addItem(volumeLabel)
        volumeLayout.addItem(volumeProgress)
        close.getConstraints().alignEnd(layout)
        close.getConstraints().layoutMarginTop(15f)
        close.getConstraints().layoutMarginRight(30f)
        title.getConstraints().alignCenterHorizontal(layout)
        title.setTextColor(ColorRGBA.red)
        soundLayout.getConstraints().layoutMarginTop(50f)
        soundLayout.getConstraints().alignBelow(title)
        musicLayout.getConstraints().alignBelow(soundLayout)
        volumeLayout.getConstraints().alignBelow(musicLayout)
        layout.addItem(title)
        layout.addItem(soundLayout)
        layout.addItem(musicLayout)
        layout.addItem(volumeLayout)
        layout.addItem(close)
        layout.setZ(parent.getZ()+1f)
        close.setOnClickListener(object :OnClickEvent.OnClickListener{
            override fun onClick() {
                show(false)
                parent.setEnableTouchEvents(true)
            }
        })
        checkSoundBox.setOnClickListener(object :OnClickEvent.OnClickListener{
            override fun onClick() {

            }
        })
        checkMusicBox.setOnClickListener(object :OnClickEvent.OnClickListener{
            override fun onClick() {

            }
        })
        volumeProgress.setOnClickListener(object :OnClickEvent.OnClickListener{
            override fun onClick() {

            }
        })
        controller?.getEvents()?.add(close)
        controller?.getEvents()?.add(checkSoundBox)
        controller?.getEvents()?.add(checkMusicBox)
        controller?.getEvents()?.add(volumeProgress)
    }
    fun draw(batch: Batch){
        if(visible)
            layout.draw(batch)
    }

    fun show(flag:Boolean){
        this.visible=flag
        layout.setEnableTouchEvents(visible)
    }

    fun isShowing():Boolean{
        return visible
    }
}