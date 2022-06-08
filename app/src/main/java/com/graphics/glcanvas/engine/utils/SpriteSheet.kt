package com.graphics.glcanvas.engine.utils

class SpriteSheet(width: Int, height: Int) {


    private var WIDTH= width
    private var HEIGHT= height
    private var position=0
    private var frames=ArrayList<FloatArray>()

    init {
        initialize()
    }

    private fun initialize(){
        for(j in 0 until HEIGHT)
            for(i in 0 until WIDTH )
                frames.add(getSTMatrix(i,j))
    }

    fun getCurrentFrame():FloatArray{
        return frames[position]
    }

    fun getFrameAt(position:Int):FloatArray{
        return frames[position]
    }

    fun setCurrentFrame(position: Int){
        this.position=position
    }

    //fill the texture matrix with the ST values
    private fun getSTMatrix(row:Int,col:Int):FloatArray{
        val matrix=FloatArray(8)
        setSTMatrix(row, col, matrix)
        return matrix
    }

    private fun setSTMatrix(row:Int,col:Int,matrix:FloatArray){
        val fSizeX=1.0f/WIDTH
        val fSizeY=1.0f/HEIGHT
        val originS=row*fSizeX
        val originT=col*fSizeY
        matrix[0]=originS
        matrix[1]=originT+fSizeY
        matrix[2]=originS
        matrix[3]=originT
        matrix[4]=originS+fSizeX
        matrix[5]=originT
        matrix[6]=originS+fSizeX
        matrix[7]=originT+fSizeY

    }

    fun setSTMatrix(row:Float,col:Float,width:Float,height:Float,scaleW:Float,scaleH:Float,index:Int){
        WIDTH=width.toInt()
        HEIGHT=height.toInt()
        val fSizeX=width/scaleW
        val fSizeY=height/scaleH
        val originS=row/scaleW
        val originT=col/scaleH
        val matrix:FloatArray= frames[index]
        matrix[0]=originS
        matrix[1]=originT+fSizeY
        matrix[2]=originS
        matrix[3]=originT
        matrix[4]=originS+fSizeX
        matrix[5]=originT
        matrix[6]=originS+fSizeX
        matrix[7]=originT+fSizeY

    }

}