package com.graphics.glcanvas.engine

interface Updatable {

    fun draw()
    fun update(delta:Float)
    fun prepare()
}