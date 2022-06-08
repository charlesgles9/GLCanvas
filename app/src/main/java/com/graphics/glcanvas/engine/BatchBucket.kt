package com.graphics.glcanvas.engine

import com.graphics.glcanvas.engine.structures.Primitives
import com.graphics.glcanvas.engine.structures.Vertex

class BatchBucket(private val primitiveType: Primitives):Comparable<BatchBucket> {

    private val batchList=ArrayList<Vertex>();
    private var priority=Int.MAX_VALUE

    fun add(vertex: Vertex){
        batchList.add(vertex)
    }

    fun setPriority(priority:Int){
        this.priority=priority
    }

    private fun getPriority():Int{
        return priority
    }

    fun getPrimitiveType():Primitives{
        return primitiveType
    }

    fun getBatchList():ArrayList<Vertex>{
        return batchList
    }

    fun getFirst():Vertex{
        return batchList.first()
    }

    override fun compareTo(other: BatchBucket): Int {
        return if (priority<=other.getPriority())-1 else 1
    }

}