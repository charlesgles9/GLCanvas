package com.graphics.glcanvas.engine.utils

class AnimationFrame(frameLength: Array<Long>, frames: Array<Int>) {

    private var frameLength:Array<Long>?= frameLength
    private var frames:Array<Int>?= frames
    private var tick=0

    fun setFrameLength(frameLength: Array<Long>){
        this.frameLength=frameLength
    }

    fun setFrames(frames: Array<Int>){
        this.frames=frames
    }

    fun getFrameLength():Array<Long>{
        return frameLength!!
    }

    fun getFrames():Array<Int>{
        return frames!!
    }

    fun getTick():Int{
        return tick
    }

    fun increment(){
        tick++
    }

    fun getCurrent():Int{
        return frames!![tick]
    }

    fun reset(){
        tick=0
    }


}