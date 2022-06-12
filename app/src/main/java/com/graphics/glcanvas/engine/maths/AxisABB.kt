package com.graphics.glcanvas.engine.maths

import com.graphics.glcanvas.engine.structures.RectF

open class AxisABB {

     fun isIntersecting(Ax:Float,Ay:Float,Aw:Float,Ah:Float,
                       Bx:Float,By:Float,Bw:Float,Bh:Float):Boolean{
        return axis(Ax,Bx,(Aw+Bw)*0.5f)&&
               axis(Ay,By,(Ah+Bh)*0.5f)
    }

     fun isIntersecting(a:RectF,b:RectF):Boolean{
        val sizeABx=a.getWidth()+b.getWidth()
        val sizeABy=a.getHeight()+b.getHeight()
        return axis(a.getX()+a.getWidth()*0.5f,b.getX()+b.getWidth()*0.5f,sizeABx*0.5f)&&
                axis(a.getY()+a.getHeight()*0.5f,b.getY()+b.getHeight()*0.5f,sizeABy*0.5f)
    }

    private fun axis(centerA:Float,centerB:Float,sizeAB:Float):Boolean{
        return centerA>=(centerB-sizeAB)&&
                centerA<=(centerB+sizeAB)
    }
}