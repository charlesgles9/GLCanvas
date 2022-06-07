package com.graphics.glcanvas.engine.structures

class Line(private var startX: Float, private var startY: Float,
           private var stopX: Float, private var stopY: Float) : Vertex(2, 2) {

    fun set(startX: Float,startY: Float,stopX: Float,stopY: Float){
        this.startX=startX
        this.startY=startY
        this.stopX=stopX
        this.stopY=stopY
    }

    fun setStartX(startX: Float){
        this.startX=startX
    }
    fun setStartY(startY: Float){
        this.startY=startY
    }
    fun setStopX(stopX: Float){
        this.stopX=stopX
    }
    fun setStopY(stopY: Float){
        this.stopY=stopY
    }
    fun getStartX():Float{
        return startX
    }
    fun getStartY():Float{
        return startY
    }
    fun getStopX():Float{
        return stopX
    }
    fun getStopY():Float{
        return stopY
    }
}