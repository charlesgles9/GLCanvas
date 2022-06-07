package com.graphics.glcanvas.engine.structures

import android.content.Context
import android.opengl.GLES32
import com.graphics.glcanvas.engine.utils.ResourceLoader

class Shader(private val vname:String,private val fname:String) {

    private var program=-1
    private var vertexShader:String=""
    private var fragmentShader:String=""
    private fun createShader(string:String,type:Int):Int {
        // load the shader
        val handle = GLES32.glCreateShader(type)
        if (handle != 0) {
            //pass shader source
            GLES32.glShaderSource(handle, string)
            //compile shader
            GLES32.glCompileShader(handle)
            // get compilation status
            val status = IntArray(1)
            GLES32.glGetShaderiv(handle, GLES32.GL_COMPILE_STATUS, status, 0)
            //compilation failed
            if (status[0] == 0) {
                val log=GLES32.glGetShaderInfoLog(handle)
                GLES32.glDeleteShader(handle)
                throw RuntimeException(if (type == GLES32.GL_VERTEX_SHADER) "Error creating vertex Shader$log" else "Error creating fragment shader$log")
            }
        }
        return handle
    }

    fun createProgram(context: Context){
       vertexShader= ResourceLoader().loadTextFromAssets(context,vname)
       fragmentShader= ResourceLoader().loadTextFromAssets(context,fname)
        program=GLES32.glCreateProgram()
        if(program!=0){

            // bind vertex shader
            GLES32.glAttachShader(program,createShader(vertexShader,GLES32.GL_VERTEX_SHADER))
            // bind fragment shader
            GLES32.glAttachShader(program,createShader(fragmentShader,GLES32.GL_FRAGMENT_SHADER))
            // bind attributes
            GLES32.glBindAttribLocation(program,0,"a_position")
            GLES32.glBindAttribLocation(program,1,"a_color")
            GLES32.glLinkProgram(program)
            //link status
            val status=IntArray(1)
            GLES32.glGetProgramiv(program,GLES32.GL_LINK_STATUS,status,0)
            if(status[0]==0){
                GLES32.glDeleteProgram(program)
                throw RuntimeException("Error creating program")
            }

        }
    }

    fun getProgram():Int{
        return program
    }

}