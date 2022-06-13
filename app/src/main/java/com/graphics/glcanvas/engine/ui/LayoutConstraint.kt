package com.graphics.glcanvas.engine.ui

 class LayoutConstraint(private val view:GLView) {

    private var left:GLView?=null
    private var right:GLView?=null
    private var above:GLView?=null
    private var below:GLView?=null
    private var center:GLView?=null
    private var MARGIN= floatArrayOf(0f,0f,0f,0f)
     
    fun toLeftOf(view: GLView){
        this.left=view
    }

    fun toRightOf(view:GLView){
        this.right=view
    }

    fun alignAbove(view:GLView){
        this.above=view
    }

    fun alignBelow(view:GLView){
        this.below=view
    }

    fun alignCenter(view:GLView){
        this.center=view
    }

    fun layoutMarginLeft(margin:Float){
        MARGIN[0]=margin
    }

    fun layoutMarginRight(margin: Float){
        MARGIN[1]=margin
    }

    fun layoutMarginTop(margin: Float){
        MARGIN[2]=margin
    }

    fun layoutMarginBottom(margin: Float){
        MARGIN[3]=margin
    }

    private fun applyLeft(){
     val width=(left?.width?:0f)
     val lx= (left?.getX()?.minus(width+view.width*0.5f)?:view.getX())
             view.set(lx,view.getY())
    }

    private fun applyRight(){
        val width=(right?.width?:0f)
        val lx= (right?.getX()?.plus(width+view.width*0.5f)?:view.getX())
        view.set(lx,view.getY())
    }

    private fun applyAbove(){
        val height=(above?.height?:0f)
        val ly= (above?.getY()?.minus(height+view.height*0.5f)?:view.getY())
        view.set(view.getX(),ly)
    }

    private fun applyBelow(){
        val height=(below?.height?:0f)
        val ly= (below?.getY()?.plus(height+view.height*0.5f)?:view.getY())
        view.set(view.getX(),ly)
    }

    private fun applyCenter(){
        val width=(center?.width?:0f)
        val height=(center?.height?:0f)
        val lx= (center?.getX()?.minus(width)?:view.getX())
        val ly= (center?.getY()?.minus(height)?:view.getY())
        view.set(lx,ly)
    }


    fun getMarginLeft():Float{
        return MARGIN[0]
    }

    fun getMarginRight():Float{
         return MARGIN[1]
    }

    fun getMarginTop():Float{
         return MARGIN[2]
    }

    fun getMarginBottom():Float{
         return MARGIN[3]
     }
    fun applyConstraints(){
        applyLeft()
        applyRight()
        applyAbove()
        applyBelow()
        applyCenter()


    }
}