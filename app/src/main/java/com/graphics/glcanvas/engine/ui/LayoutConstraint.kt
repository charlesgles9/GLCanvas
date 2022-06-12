package com.graphics.glcanvas.engine.ui

class LayoutConstraint(private val view:GLView) {

    private var left:GLView?=null
    private var right:GLView?=null
    private var above:GLView?=null
    private var below:GLView?=null
    private var center:GLView?=null

    fun toLeftOf(left: GLView){
        this.left=left
    }

    fun toRightOf(right:GLView){
        this.right=right
    }

    fun alignAbove(above:GLView){
        this.above=above
    }

    fun alignBelow(below:GLView){
        this.below=below
    }

    fun alignCenter(center:GLView){
        this.center=center
    }

    private fun applyLeft(){
     val width=(left?.width?:0f)
     val lx= (left?.getX()?.minus(width)?:view.getX())
             view.set(lx,view.getY())
    }

    private fun applyRight(){
        val width=(right?.width?:0f)
        val lx= (right?.getX()?.minus(width)?:view.getX())
        view.set(lx,view.getY())
    }

    private fun applyAbove(){
        val height=(above?.height?:0f)
        val ly= (above?.getY()?.minus(height)?:view.getY())
        view.set(view.getX(),ly)
    }

    private fun applyBelow(){
        val height=(below?.height?:0f)
        val ly= (below?.getY()?.plus(height)?:view.getY())
        view.set(view.getX(),ly)
    }

    private fun applyCenter(){
        val width=(center?.width?:0f)
        val height=(center?.height?:0f)
        val lx= (center?.getX()?.minus(width)?:view.getX())
        val ly= (center?.getY()?.minus(height)?:view.getY())
        view.set(lx,ly)
    }

    fun applyConstraints(){
        applyLeft()
        applyRight()
        applyAbove()
        applyBelow()
        applyCenter()
    }
}