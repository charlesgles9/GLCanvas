package com.graphics.glcanvas.engine

import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.ui.*
import com.graphics.glcanvas.engine.utils.FpsCounter
import com.graphics.glcanvas.engine.utils.TextureAtlas

class HomeScreen(atlas: TextureAtlas,font: Font,controller:GLCanvasSurfaceView.TouchController?,width:Float,height:Float) {


    private var layout=RelativeLayoutConstraint(width, height,atlas,"Card",0)
    private var start=GLImageButton(250f,50f,atlas)
    private var settings=GLImageButton(250f,50f,atlas)
    private var about=GLImageButton(250f,50f,atlas)
    private var exit=GLImageButton(250f,50f,atlas)
    private var fpsLabel=GLLabel(150f,50f,font,"FPS: ",0.3f)
   init {
       layout.setBackgroundColor(ColorRGBA.white)
       layout.setX(width*0.5f)
       layout.setY(height*0.5f)
       val mainContainer=LinearLayoutConstraint(layout,250f,250f)
           mainContainer.setOrientation(LinearLayoutConstraint.VERTICAL)
           mainContainer.setColor(ColorRGBA.transparent)
       // start button
       start.setPrimaryImage("Button Normal",0)
       start.setSecondaryImage("Button Hover",0)
       start.setText("START",font,0.3f)
       start.setRippleColor(ColorRGBA.white)
       start.getConstraints().layoutMarginTop(10f)
       //settings button
       settings.setPrimaryImage("Button Normal",0)
       settings.setSecondaryImage("Button Hover",0)
       settings.setText("SETTINGS",font,0.3f)
       settings.setRippleColor(ColorRGBA.white)
       settings.getConstraints().layoutMarginBottom(10f)
      // about button
       about.setPrimaryImage("Button Normal",0)
       about.setSecondaryImage("Button Hover",0)
       about.setText("ABOUT",font,0.3f)
       about.setRippleColor(ColorRGBA.white)
       about.getConstraints().layoutMarginBottom(10f)
       //exit button
       exit.setPrimaryImage("Button Normal",0)
       exit.setSecondaryImage("Button Hover",0)
       exit.setText("EXIT",font,0.3f)
       exit.setRippleColor(ColorRGBA.white)
       exit.getConstraints().layoutMarginBottom(10f)
       mainContainer.addItem(start)
       mainContainer.addItem(settings)
       mainContainer.addItem(about)
       mainContainer.addItem(exit)
       mainContainer.getConstraints().alignCenterVertical(layout)
       mainContainer.getConstraints().alignCenterHorizontal(layout)
       layout.addItem(mainContainer)
       layout.addItem(fpsLabel)
       fpsLabel.getConstraints().layoutMarginTop(20f)
       fpsLabel.getConstraints().alignCenterHorizontal(layout)
       start.setOnClickListener(object :OnClickEvent.OnClickListener{
           override fun onClick() {

           }
       })
       settings.setOnClickListener(object :OnClickEvent.OnClickListener{
           override fun onClick() {

           }
       })
       about.setOnClickListener(object :OnClickEvent.OnClickListener{
           override fun onClick() {

           }
       })
       exit.setOnClickListener(object :OnClickEvent.OnClickListener{
           override fun onClick() {

           }
       })

       controller?.addEvent(start)
       controller?.addEvent(settings)
       controller?.addEvent(about)
       controller?.addEvent(exit)

   }

     fun draw(batch:Batch){
        layout.draw(batch)
        fpsLabel.setText("FPS: "+FpsCounter.getInstance().getFps())
    }
}