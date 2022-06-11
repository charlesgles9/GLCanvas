package com.graphics.glcanvas.engine.utils

import android.content.Context
import com.graphics.glcanvas.engine.maths.Vector2f

import java.io.InputStream


class TextureAtlas(path:String,context: Context) {
    private var texturePath=""
    private var texture:Texture?=null
    private var resolution=Vector2f()
    private var format=""
    private val map=HashMap<String,Atlas>()
    private val coordinates=HashMap<String,Int>()
    private var sheet:SpriteSheet?=null
    private var current:Atlas?=null
    init {
        parse(path,context)
        sheet= SpriteSheet(1,1)
        sheet?.resize(map.size)
        var counter=0
        map.forEach{(k,v)->
             sheet?.setSTMatrix(v.getPosition().x,v.getPosition().y,
                                v.getSize().x,v.getSize().y,
                                resolution.x,resolution.y,counter)
            coordinates[k] = counter
            counter++
        }
       texture= Texture(context,texturePath)
    }

    private fun split(text:String):List<String>{
        return text.split(":")
    }

    private fun parse(path: String,context: Context){
        val stream: InputStream =context.assets.open(path)
        stream.bufferedReader().forEachLine {
            // if it doesn't contain this sign then this line is
            //a title
             if(!it.contains(":")){
                 // test if this data is the image details located at the
                 // start of the text file
                 if (map.isEmpty()&&texturePath=="") {
                     texturePath = it
                 }else {
                     //if map isn't empty then these are the sprite coordinates
                     current = Atlas()
                     map[it] = current!!
                 }
             }else{
                 // sprite coordinate data and texture information
                 if(map.isEmpty()) {
                     if(it.indexOf("size") != -1){
                         val arr = split(it)[1].split(',')
                         resolution.set(arr[0].trim().toFloat(), arr[1].trim().toFloat())
                     }else if (it.indexOf("format") != -1) {
                         format = split(it)[1]
                     }
                 }else{

                     when {
                         it.indexOf("size") != -1->{
                             val arr = split(it)[1].split(',')
                             current?.setSize(arr[0].trim().toFloat(), arr[1].trim().toFloat())
                         }
                         it.indexOf("rotate") != -1 -> {
                             val rotate = split(it)[1].trim()
                             current?.setRotate(rotate.toBoolean())
                         }
                         it.indexOf("xy") != -1 -> {
                             val xy = split(it)[1].split(",")
                             current?.setPosition(xy[0].trim().toFloat(), xy[1].trim().toFloat())
                         }
                         it.indexOf("orig") != -1 -> {
                             val origin = split(it)[1].split(",")
                             current?.setOrigin(
                                 origin[0].trim().toFloat(),
                                 origin[1].trim().toFloat()
                             )
                         }

                         it.indexOf("offset") != -1 -> {
                             val offset = split(it)[1].split(",")
                             current?.setOffset(
                                 offset[0].trim().toFloat(),
                                 offset[1].trim().toFloat()
                             )
                         }
                         it.indexOf("index") != -1 -> {
                             val index = split(it)[1].trim()
                             current?.setIndex(index.toInt())
                         }
                     }
                 }
             }
        }
    }


    fun getItem(key:String):Atlas?{
        return map[key]
    }

    fun getSheet():SpriteSheet?{
        return sheet
    }

    fun getTextureCoordinate(key: String):Int{
        return coordinates[key]!!
    }

    fun getTexture():Texture?{
        return texture
    }
    inner class Atlas(){
        private var rotate=false
        private var position=Vector2f()
        private var size= Vector2f()
        private var origin=Vector2f()
        private var offset=Vector2f()
        private var index=-1
        fun setRotate(rotate: Boolean){
            this.rotate=rotate
        }
        fun setPosition(x:Float,y:Float){
            this.position.set(x,y)
        }
        fun setSize(x:Float,y:Float){
            this.size.set(x,y)
        }
        fun setOrigin(x:Float,y:Float){
            this.origin.set(x,y)
        }
        fun setOffset(x:Float,y:Float){
            this.offset.set(x,y)
        }
        fun setIndex(index:Int){
            this.index=index
        }

        fun getRotate():Boolean{
            return rotate
        }

        fun getPosition():Vector2f{
            return position
        }
        fun getSize():Vector2f{
            return size
        }


    }
}