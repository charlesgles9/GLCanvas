package com.graphics.glcanvas.engine.structures

import com.graphics.glcanvas.engine.maths.ColorRGBA

class Polygon:Vertex(0,0) {

    private val paths= MutableList(0,init = { Path(0.0f,0.0f) })

    fun moveTo(x:Float,y:Float){
        paths.add(Path(x,y))
    }

    fun lineTo(x:Float,y:Float){
        paths.last().lineTo(x,y)
    }

    fun lineColor(color: ColorRGBA){
        paths.last().setColor(color)
    }

    fun reset(){
        paths.clear()
    }

    fun getPaths():MutableList<Path>{
        return paths
    }
}