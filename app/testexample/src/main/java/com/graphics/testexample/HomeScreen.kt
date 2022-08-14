package com.graphics.testexample

import com.graphics.glcanvas.engine.*
import com.graphics.glcanvas.engine.maths.ColorRGBA
import com.graphics.glcanvas.engine.structures.Font
import com.graphics.glcanvas.engine.ui.*
import com.graphics.glcanvas.engine.utils.FpsCounter
import com.graphics.glcanvas.engine.utils.TextureAtlas
import kotlin.system.exitProcess

class HomeScreen(atlas: TextureAtlas, font: Font, controller: GLCanvasSurfaceView.TouchController?, width:Float, height:Float) {


    private var layout=RelativeLayoutConstraint(null,width, height,atlas,"Card",0)
    private var marquee=GLMarqueeText(300f,50f,"This is a marquee text. This is a marquee text. This is a marquee text.",font,0.3f)
    private var start=GLButton(250f,50f,atlas)
    private var settings=GLButton(250f,50f,atlas)
    private var about=GLButton(250f,50f,atlas)
    private var exit=GLButton(250f,50f,atlas)
    private var fpsLabel=GLLabel(150f,50f,font,"FPS: ",0.3f)
    private var aboutDialog= AboutDialog(layout,atlas, font, controller, width, height)
    private var settingDialog= SettingDialog(layout,atlas, font, controller, width, height)
    private var startGameDialog= StartGameDialog(layout,atlas, font, controller, width, height)

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
        settings.getConstraints().layoutMarginBottom(20f)
        // about button
        about.setPrimaryImage("Button Normal",0)
        about.setSecondaryImage("Button Hover",0)
        about.setText("ABOUT",font,0.3f)
        about.setRippleColor(ColorRGBA.white)
        about.getConstraints().layoutMarginBottom(20f)
        //exit button
        exit.setPrimaryImage("Button Normal",0)
        exit.setSecondaryImage("Button Hover",0)
        exit.setText("EXIT",font,0.3f)
        exit.setRippleColor(ColorRGBA.white)
        exit.getConstraints().layoutMarginBottom(20f)
        mainContainer.addItem(start)
        mainContainer.addItem(settings)
        mainContainer.addItem(about)
        mainContainer.addItem(exit)
        mainContainer.getConstraints().alignCenterVertical(layout)
        mainContainer.getConstraints().alignCenterHorizontal(layout)
        layout.addItem(mainContainer)
        layout.addItem(fpsLabel)
        layout.addItem(marquee)
        marquee.getConstraints().layoutMarginTop(80f)
        marquee.getConstraints().alignCenterHorizontal(layout)
        marquee.setBackgroundAtlas(atlas,"Frame",0)
        fpsLabel.getConstraints().layoutMarginTop(20f)
        fpsLabel.getConstraints().alignCenterHorizontal(layout)
        start.setOnClickListener(object :OnClickEvent.OnClickListener{
            override fun onClick() {
                startGameDialog.show(!startGameDialog.isShowing())
                layout.setEnableTouchEvents(!startGameDialog.isShowing())
            }
        })
        settings.setOnClickListener(object :OnClickEvent.OnClickListener{
            override fun onClick() {
                settingDialog.show(!settingDialog.isShowing())
                layout.setEnableTouchEvents(!settingDialog.isShowing())
            }
        })
        about.setOnClickListener(object :OnClickEvent.OnClickListener{
            override fun onClick() {
                aboutDialog.show(!aboutDialog.isShowing())
                layout.setEnableTouchEvents(!aboutDialog.isShowing())

            }
        })
        exit.setOnClickListener(object :OnClickEvent.OnClickListener{
            override fun onClick() {
                exitProcess(0)
            }
        })

        val list= mutableListOf<GLView>()
        for(i in 0 until 50 ) {
            val color=ColorRGBA(ColorRGBA.cyan)
            list.add(
                genButton(
                    i.toString(),
                    100f,
                    50f,
                    ColorRGBA.darken(0.7f, color),
                    10f,font
                )
            )
        }

        val scrollView=GLScrollLayout(width,280f)
        val gridLayout=GLGridLayout(scrollView,400f,250f,5,10)
        gridLayout.setItems(list)
        gridLayout.setBackgroundColor(ColorRGBA.transparent)
        scrollView.setOrientation(GLScrollLayout.HORIZONTAL)
        scrollView.addItem(gridLayout)
        scrollView.getConstraints().alignCenterHorizontal(layout)
        scrollView.getConstraints().alignBelow(mainContainer)
      // layout.addItem(scrollView)


        scrollView.addOnSwipeEvent(object :GLOnSwipeEvent.OnSwipeListener{
            override fun onSwipe() {

            }
        })

        gridLayout.setOnItemClickListener(object :OnItemClickEvent.OnItemClickListener{
            override fun onItemClick(view: GLView) {
                println("Clicked: "+view.Id)
            }
        })


        controller?.addEvent(start)
        controller?.addEvent(settings)
        controller?.addEvent(about)
        controller?.addEvent(exit)
        controller?.addEvent(gridLayout)
        controller?.addEvent(scrollView)


    }


    fun genButton(id:String,w:Float,h:Float,color:ColorRGBA,rounded:Float,font: Font):GLButton{
        val button =GLButton(w,h)
        button.roundedCorner(rounded)
        button.setBackgroundColor(color)
        button.setText(id,font,0.25f)
        button.Id=id
        return button
    }
    fun draw(batch: Batch){
        layout.draw(batch)
        aboutDialog.draw(batch)
        settingDialog.draw(batch)
        startGameDialog.draw(batch)
        fpsLabel.setText("FPS: "+FpsCounter.getInstance().getFps())

    }
}