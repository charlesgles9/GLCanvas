package com.graphics.glcanvas.engine

import android.content.Context
import android.opengl.GLES32
import android.opengl.Matrix
import com.graphics.glcanvas.engine.maths.Vector3f
import com.graphics.glcanvas.engine.structures.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import kotlin.math.abs

class Batch(private val ResolutionX:Float,private val ResolutionY:Float) {

// a model matrix used to move models from object space
    private val mModelMatrix=FloatArray(16)
    // transforms world space into eye space
    private val mViewMatrix= FloatArray(16)
    // model view Projection  matrix this we will pass it to the vertex shader
    private val mMVPMatrix=FloatArray(16)

    // buffers
    // positional
    private var vertexBuffer:FloatBuffer?=null
    //indices
    private var drawListBuffer:ShortBuffer?=null
    //color
    private var colorBuffer:FloatBuffer?=null
    //texture
    private var textureBuffer:FloatBuffer?=null
    //center buffer for circles and quads
    private var centerBuffer:FloatBuffer?=null
    // sends the extra quad data that will enable us to
    // create rounded edges
    private var roundedPropBuffer:FloatBuffer?=null
    //vertex count
    private var vcount=0
    //index count
    private var icount=0
    //color count
    private var acount=0
    //texture count
    private var tcount=0
    //center count
    private var mcount=0
    // rounded properties count
    private var rcount=0

    private val VERTEX_COORDS_PER_VERTEX=3
    private val COLOR_COORDS_PER_VERTEX=4
    private val TEXTURE_COORDS_PER_VERTEX=2
    private val BYTES_PER_FLOAT=4

    private val position=Vector3f()
    private val rotation=Vector3f()

    // mesh data
    private val BATCH_SIZE=1000
    private var vertexes= FloatArray(BATCH_SIZE*VERTEX_COORDS_PER_VERTEX*4)
    private var indices=ShortArray(BATCH_SIZE*6)
    private var colors=FloatArray(BATCH_SIZE*COLOR_COORDS_PER_VERTEX*4)
    private var textures=FloatArray(BATCH_SIZE*TEXTURE_COORDS_PER_VERTEX*4)
    // current texture
    private var mTexture=0
    // used to draw batched circles since we need the center position
    // also useful to pass in the center position of our quad to create rounded edges
    private var centerVertex=FloatArray(BATCH_SIZE*4*4)
    private var roundedRectProperties=FloatArray(BATCH_SIZE*2*4)
    private val buffers=IntArray(5)
    private val defaultShader=Shader("shaders/default_vertex_shader.txt","shaders/default_fragment_shader.txt")
    private val circleShader=Shader("shaders/circle_vertex_shader.txt","shaders/circle_fragment_shader.txt")
    private var camera:Camera2D?=null
    private val batchQueue=BatchQueue()
    private var primitiveType=Primitives.QUAD
    private val entities=ArrayList<Vertex>()

    init {
        GLES32.glGenBuffers(5,buffers,0)
        createVertexBuffer()
        createColorBuffer()
        createTextureBuffer()
        createCenterBuffer()
        createRoundedPropertiesBuffer()
        initializeDrawList()
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,1)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,2)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,3)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,4)

    }
    
    fun begin(camera: Camera2D){
        this.camera=camera
        batchQueue.reset()
        reset()
    }

    private fun reset(){
        this.entities.clear()
        vcount=0
        acount=0
        icount=0
        tcount=0
        mcount=0
        rcount=0
    }

    fun draw(vertex: Vertex){
        entities.add(vertex)
    }

    fun end(){
        for( i in 0 until entities.size){
            val entity=entities[i]
            var type=Primitives.CIRCLE
            when (entity) {
                is RectF ->
                    type = Primitives.QUAD
                is Line ->
                    type=Primitives.LINE
                is PolyLine ->
                    type=Primitives.POLYLINE
                is Polygon ->
                    type=Primitives.TRIANGLE
                is Circle ->
                    type = Primitives.CIRCLE
            }
             batchQueue.addVertex(entity,type)
        }

        while (!batchQueue.getBatchedQueue().isEmpty()){
            val bucket=batchQueue.getBatchedQueue().remove()
            val list=bucket.getBatchList()
            reset()
               for(i in 0 until list.size){
                   when(bucket.getPrimitiveType()){
                       Primitives.QUAD ->  addRectF(i, list[i])
                       Primitives.TRIANGLE -> addPolygon(i, list[i])
                       Primitives.CIRCLE ->  addCircle(i,list[i])
                       Primitives.LINE ->  addLine(i,list[i])
                       Primitives.POLYLINE -> addPolyLine(i, list[i])
                   }
               }
            if(list.isNotEmpty()) {
                primitiveType = when(bucket.getPrimitiveType()){
                    Primitives.QUAD -> Primitives.QUAD
                    Primitives.TRIANGLE -> Primitives.TRIANGLE
                    Primitives.CIRCLE -> Primitives.CIRCLE
                    Primitives.LINE -> Primitives.LINE
                    Primitives.POLYLINE -> Primitives.POLYLINE
                }
                draw()
            }

        }
    }



    private fun addCircle(index:Int,vertex: Vertex){
        val rect= vertex as Circle
        val sizeX=rect.getRadius()
        val sizeY=rect.getRadius()
        val x=rect.getX()
        val y=rect.getY()

        //top left
        vertexes[vcount++]=-sizeX+x
        vertexes[vcount++]=sizeY+y
        vertexes[vcount++]=0.0f
        //bottom left
        vertexes[vcount++]=-sizeX+x
        vertexes[vcount++]=-sizeY+y
        vertexes[vcount++]=0.0f
        //bottom right
        vertexes[vcount++]=sizeX+x
        vertexes[vcount++]=-sizeY+y
        vertexes[vcount++]=0.0f
        //top right
        vertexes[vcount++]=sizeX+x
        vertexes[vcount++]=sizeY+y
        vertexes[vcount++]=0.0f


        centerVertex[mcount++]=x
        centerVertex[mcount++]=y
        centerVertex[mcount++]=rect.getRadius()
        centerVertex[mcount++]=rect.getThickness()

        centerVertex[mcount++]=x
        centerVertex[mcount++]=y
        centerVertex[mcount++]=rect.getRadius()
        centerVertex[mcount++]=rect.getThickness()

        centerVertex[mcount++]=x
        centerVertex[mcount++]=y
        centerVertex[mcount++]=rect.getRadius()
        centerVertex[mcount++]=rect.getThickness()

        centerVertex[mcount++]=x
        centerVertex[mcount++]=y
        centerVertex[mcount++]=rect.getRadius()
        centerVertex[mcount++]=rect.getThickness()


        indices[icount++]= (index*4+0).toShort()
        indices[icount++]= (index*4+1).toShort()
        indices[icount++]= (index*4+2).toShort()
        indices[icount++]= (index*4+0).toShort()
        indices[icount++]= (index*4+2).toShort()
        indices[icount++]= (index*4+3).toShort()

        val color1=rect.getColor(0)
        val color2=rect.getColor(1)
        val color3=rect.getColor(2)
        val color4=rect.getColor(3)
        colors[acount++]=color1.get(0)
        colors[acount++]=color1.get(1)
        colors[acount++]=color1.get(2)
        colors[acount++]=color1.get(3)

        colors[acount++]=color2.get(0)
        colors[acount++]=color2.get(1)
        colors[acount++]=color2.get(2)
        colors[acount++]=color2.get(3)

        colors[acount++]=color3.get(0)
        colors[acount++]=color3.get(1)
        colors[acount++]=color3.get(2)
        colors[acount++]=color3.get(3)

        colors[acount++]=color4.get(0)
        colors[acount++]=color4.get(1)
        colors[acount++]=color4.get(2)
        colors[acount++]=color4.get(3)
        mTexture=vertex.getTexture()
    }

    private fun addRectF(index:Int,vertex: Vertex){
        val rect= vertex as RectF
        val sizeX=rect.getWidth()/2
        val sizeY=rect.getHeight()/2
        val x=rect.getX()
        val y=rect.getY()

        //top left
        vertexes[vcount++]=-sizeX+x
        vertexes[vcount++]=sizeY+y
        vertexes[vcount++]=0.0f
        //bottom left
        vertexes[vcount++]=-sizeX+x
        vertexes[vcount++]=-sizeY+y
        vertexes[vcount++]=0.0f
        //bottom right
        vertexes[vcount++]=sizeX+x
        vertexes[vcount++]=-sizeY+y
        vertexes[vcount++]=0.0f
        //top right
        vertexes[vcount++]=sizeX+x
        vertexes[vcount++]=sizeY+y
        vertexes[vcount++]=0.0f

        val texture=rect.getTextureCords()
        textures[tcount++]=texture[0]
        textures[tcount++]=texture[1]

        textures[tcount++]=texture[2]
        textures[tcount++]=texture[3]

        textures[tcount++]=texture[4]
        textures[tcount++]=texture[5]

        textures[tcount++]=texture[6]
        textures[tcount++]=texture[7]

        mTexture=rect.getTexture()

        centerVertex[mcount++]=x
        centerVertex[mcount++]=y
        centerVertex[mcount++]=sizeX
        centerVertex[mcount++]=sizeY

        centerVertex[mcount++]=x
        centerVertex[mcount++]=y
        centerVertex[mcount++]=sizeX
        centerVertex[mcount++]=sizeY

        centerVertex[mcount++]=x
        centerVertex[mcount++]=y
        centerVertex[mcount++]=sizeX
        centerVertex[mcount++]=sizeY

        centerVertex[mcount++]=x
        centerVertex[mcount++]=y
        centerVertex[mcount++]=sizeX
        centerVertex[mcount++]=sizeY

        roundedRectProperties[rcount++]=rect.geThickness()
        roundedRectProperties[rcount++]=rect.getConnerRadius()

        roundedRectProperties[rcount++]=rect.geThickness()
        roundedRectProperties[rcount++]=rect.getConnerRadius()

        roundedRectProperties[rcount++]=rect.geThickness()
        roundedRectProperties[rcount++]=rect.getConnerRadius()

        roundedRectProperties[rcount++]=rect.geThickness()
        roundedRectProperties[rcount++]=rect.getConnerRadius()

        indices[icount++]= (index*4+0).toShort()
        indices[icount++]= (index*4+1).toShort()
        indices[icount++]= (index*4+2).toShort()
        indices[icount++]= (index*4+0).toShort()
        indices[icount++]= (index*4+2).toShort()
        indices[icount++]= (index*4+3).toShort()

        val color1=rect.getColor(0)
        val color2=rect.getColor(1)
        val color3=rect.getColor(2)
        val color4=rect.getColor(3)
        colors[acount++]=color1.get(0)
        colors[acount++]=color1.get(1)
        colors[acount++]=color1.get(2)
        colors[acount++]=color1.get(3)

        colors[acount++]=color2.get(0)
        colors[acount++]=color2.get(1)
        colors[acount++]=color2.get(2)
        colors[acount++]=color2.get(3)

        colors[acount++]=color3.get(0)
        colors[acount++]=color3.get(1)
        colors[acount++]=color3.get(2)
        colors[acount++]=color3.get(3)

        colors[acount++]=color4.get(0)
        colors[acount++]=color4.get(1)
        colors[acount++]=color4.get(2)
        colors[acount++]=color4.get(3)

    }

    private fun addLine(index: Int, vertex: Vertex){
        val line=vertex as Line
        val sizeX= abs( line.getStartX()-line.getStopX())
        val sizeY=abs(line.getStartY()-line.getStopY())
        val x=line.getStartX()
        val y=line.getStartY()
        // top left
        vertexes[vcount++]=x
        vertexes[vcount++]=y
        vertexes[vcount++]=0.0f
        //bottom left
        vertexes[vcount++]=sizeX+x
        vertexes[vcount++]=sizeY+y
        vertexes[vcount++]=0.0f

        indices[icount++]=(index*2+0).toShort()
        indices[icount++]=(index*2+1).toShort()

        val color1=line.getColor(0)
        val color2=line.getColor(1)

        colors[acount++]=color1.get(0)
        colors[acount++]=color1.get(1)
        colors[acount++]=color1.get(2)
        colors[acount++]=color1.get(3)

        colors[acount++]=color2.get(0)
        colors[acount++]=color2.get(1)
        colors[acount++]=color2.get(2)
        colors[acount++]=color2.get(3)
        mTexture=vertex.getTexture()
    }

    private fun addPolyLine(index: Int, vertex: Vertex):Int{
        val polyLine=vertex as PolyLine
        var lcount=index
        for(i in 0 until polyLine.getPaths().size) {
            val path=polyLine.getPaths()[i]
            val x = path.getStart().x
            val y = path.getStart().y
            for(j in 0 until path.getEndPoints().size){
                val line=path.getEndPoints()[j]
                val sizeX = abs(x - line.x)
                val sizeY = abs(y - line.y)
                // top left
                vertexes[vcount++] = x
                vertexes[vcount++] = y
                vertexes[vcount++] = 0.0f
                //bottom left
                vertexes[vcount++] = sizeX + x
                vertexes[vcount++] = sizeY + y
                vertexes[vcount++] = 0.0f

                indices[icount++] = (lcount * 2 + 0).toShort()
                indices[icount++] = (lcount * 2 + 1).toShort()

                val color=path.getColor(0)
                colors[acount++] =color.get(0)
                colors[acount++] =color.get(1)
                colors[acount++] =color.get(2)
                colors[acount++] =color.get(3)

                colors[acount++] =color.get(0)
                colors[acount++] =color.get(1)
                colors[acount++] =color.get(2)
                colors[acount++] =color.get(3)
                lcount++
            }

        }
        mTexture=vertex.getTexture()
        return vcount

    }

    private fun addPolygon(index: Int, vertex: Vertex):Int{
        val poly=vertex as Polygon
        var pcount=index
        for(i in 0 until poly.getPaths().size) {
            val path=poly.getPaths()[i]
            val x = path.getStart().x
            val y = path.getStart().y
            if(path.getEndPoints().size>=2)
            for(j in 0 until path.getEndPoints().size step 2){
                val a=path.getEndPoints()[j]
                val b=path.getEndPoints()[j+1]
                val sizeAX = x - a.x
                val sizeAY = y - a.y
                val sizeBX = x - b.x
                val sizeBY = y - b.y

                vertexes[vcount++] = x
                vertexes[vcount++] = y
                vertexes[vcount++] = 0.0f

                vertexes[vcount++] = sizeAX + x
                vertexes[vcount++] = sizeAY + y
                vertexes[vcount++] = 0.0f

                vertexes[vcount++] = sizeBX + x
                vertexes[vcount++] = sizeBY + y
                vertexes[vcount++] = 0.0f

                indices[icount++] = (pcount * 3 + 0).toShort()
                indices[icount++] = (pcount * 3 + 1).toShort()
                indices[icount++] = (pcount * 3 + 2).toShort()
                val color1=path.getColor(0)
                val color2=path.getColor(1)
                val color3=path.getColor(2)
                colors[acount++] =color1.get(0)
                colors[acount++] =color1.get(1)
                colors[acount++] =color1.get(2)
                colors[acount++] =color1.get(3)

                colors[acount++] =color2.get(0)
                colors[acount++] =color2.get(1)
                colors[acount++] =color2.get(2)
                colors[acount++] =color2.get(3)

                colors[acount++] =color3.get(0)
                colors[acount++] =color3.get(1)
                colors[acount++] =color3.get(2)
                colors[acount++] =color3.get(3)
                pcount++
            }

        }
        mTexture=vertex.getTexture()
        return vcount

    }

    private fun transform(){
        Matrix.setIdentityM(mModelMatrix,0)
        Matrix.translateM(mModelMatrix,0,position.x,position.y,position.z)
        Matrix.rotateM(mModelMatrix,0,rotation.z,0.0f,0.0f,1.0f)
        Matrix.scaleM(mModelMatrix,0,1.0f,1.0f,1.0f)
    }

    //initialize vertex byte buffer for shape coordinates
    private fun createVertexBuffer(){
        val bb=ByteBuffer.allocateDirect((vertexes.size*BYTES_PER_FLOAT))
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer=bb.asFloatBuffer()
        vertexBuffer?.put(vertexes)
        vertexBuffer?.position(0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[0])
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,vertexBuffer!!.capacity()*BYTES_PER_FLOAT,null,GLES32.GL_DYNAMIC_DRAW)
    }

    // initialize the drawList
    private fun initializeDrawList(){
        val dlb=ByteBuffer.allocateDirect(indices.size*2)
        dlb.order(ByteOrder.nativeOrder())
        drawListBuffer=dlb.asShortBuffer()
        drawListBuffer?.put(indices)
        drawListBuffer?.position(0)
    }

    private fun createColorBuffer(){
        val bb=ByteBuffer.allocateDirect((colors.size*BYTES_PER_FLOAT))
        bb.order(ByteOrder.nativeOrder())
        colorBuffer=bb.asFloatBuffer()
        colorBuffer?.put(colors)
        colorBuffer?.position(0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[1])
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,colorBuffer!!.capacity()*BYTES_PER_FLOAT,null,GLES32.GL_DYNAMIC_DRAW)
    }

    private fun createTextureBuffer(){
        val bb=ByteBuffer.allocateDirect((textures.size*BYTES_PER_FLOAT))
        bb.order(ByteOrder.nativeOrder())
        textureBuffer=bb.asFloatBuffer()
        textureBuffer?.put(textures)
        textureBuffer?.position(0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[2])
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,textureBuffer!!.capacity()*BYTES_PER_FLOAT,null,GLES32.GL_DYNAMIC_DRAW)
    }

    private fun createCenterBuffer(){
        val bb=ByteBuffer.allocateDirect((centerVertex.size*BYTES_PER_FLOAT))
        bb.order(ByteOrder.nativeOrder())
        centerBuffer=bb.asFloatBuffer()
        centerBuffer?.put(centerVertex)
        centerBuffer?.position(0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[3])
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,centerBuffer!!.capacity()*BYTES_PER_FLOAT,null,GLES32.GL_DYNAMIC_DRAW)
    }

    private fun createRoundedPropertiesBuffer(){
        val bb=ByteBuffer.allocateDirect((roundedRectProperties.size*BYTES_PER_FLOAT))
        bb.order(ByteOrder.nativeOrder())
        roundedPropBuffer=bb.asFloatBuffer()
        roundedPropBuffer?.put(roundedRectProperties)
        roundedPropBuffer?.position(0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[4])
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER,roundedPropBuffer!!.capacity()*BYTES_PER_FLOAT,null,GLES32.GL_DYNAMIC_DRAW)
    }

    // bind vertex shader attributes
    private fun bindVertexShader(){
        vertexBuffer!!.put(vertexes).position(0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[0])
        GLES32.glBufferSubData(GLES32.GL_ARRAY_BUFFER,0,vcount*4,vertexBuffer)
        val positionHandle=GLES32.glGetAttribLocation(defaultShader.getProgram(),"a_position")
        GLES32.glEnableVertexAttribArray(positionHandle)
        //prepare triangle coordinate data
        GLES32.glVertexAttribPointer(
            positionHandle,
            VERTEX_COORDS_PER_VERTEX,
        GLES32.GL_FLOAT,
        false,
        12,
        vertexBuffer)

        // pass in every circle or quads center position
        centerBuffer!!.put(centerVertex).position(0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[3])
        GLES32.glBufferSubData(GLES32.GL_ARRAY_BUFFER,0,mcount*4,centerBuffer)
        if(primitiveType==Primitives.CIRCLE) {
            val centerPHandle = GLES32.glGetAttribLocation(circleShader.getProgram(), "v_center")
            GLES32.glEnableVertexAttribArray(centerPHandle)
            //prepare triangle coordinate data
            GLES32.glVertexAttribPointer(
                centerPHandle,
                4,
                GLES32.GL_FLOAT,
                false,
                0,
                centerBuffer)
        }else if (primitiveType==Primitives.QUAD) {
            val centerPHandle = GLES32.glGetAttribLocation(defaultShader.getProgram(), "v_center")
            GLES32.glEnableVertexAttribArray(centerPHandle)
            //prepare triangle coordinate data
            GLES32.glVertexAttribPointer(
                centerPHandle,
                4,
                GLES32.GL_FLOAT,
                false,
                0,
                centerBuffer)
            // pass the rounded corners for rectF shape
            roundedPropBuffer!!.put(roundedRectProperties).position(0)
            GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[4])
            GLES32.glBufferSubData(GLES32.GL_ARRAY_BUFFER,0,rcount*4,roundedPropBuffer)
            val roundedRectHandle=GLES32.glGetAttribLocation(defaultShader.getProgram(),"v_rounded_properties")
            GLES32.glEnableVertexAttribArray(roundedRectHandle)
            //prepare triangle coordinate data
            GLES32.glVertexAttribPointer(
                roundedRectHandle,
                2,
                GLES32.GL_FLOAT,
                false,
                0,
                roundedPropBuffer)

        }


    }

    // bind fragment shader attributes
    private fun bindFragmentShader(){
        //bind color
        val colorHandle=GLES32.glGetAttribLocation(defaultShader.getProgram(),"a_color")
        colorBuffer!!.put(colors).position(0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[1])
        GLES32.glBufferSubData(GLES32.GL_ARRAY_BUFFER,0,acount*4,colorBuffer)
        GLES32.glEnableVertexAttribArray(colorHandle)
        //pass color information to the shader
        GLES32.glVertexAttribPointer(
            colorHandle,
            COLOR_COORDS_PER_VERTEX,
            GLES32.GL_FLOAT,
            false,
            0,
            colorBuffer)

        //bind texture
        val textureCordHandle=GLES32.glGetAttribLocation(defaultShader.getProgram(),"a_TexCoordinate")
        val textureUniformHandle=GLES32.glGetUniformLocation(defaultShader.getProgram(),"u_texture")
        //test if its a valid texture
        GLES32.glUniform1i(GLES32.glGetUniformLocation(defaultShader.getProgram(),"sampleId"),mTexture)
        // pass texture coordinate info
        textureBuffer!!.put(textures).position(0)
        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER,buffers[2])
        GLES32.glBufferSubData(GLES32.GL_ARRAY_BUFFER,0,tcount*4,textureBuffer)

        GLES32.glVertexAttribPointer(textureCordHandle,
         2,
         GLES32.GL_FLOAT,
         false,
         0,textureBuffer);
        GLES32.glEnableVertexAttribArray(textureCordHandle)
        //set to unit 0
        GLES32.glActiveTexture(GLES32.GL_TEXTURE0)
        GLES32.glBindTexture(GLES32.GL_TEXTURE_2D,mTexture)
        GLES32.glUniform1i(textureUniformHandle,0)

    }

    private fun render(){
        // use different shader is it's a circle
        if(primitiveType==Primitives.CIRCLE)
            GLES32.glUseProgram(circleShader.getProgram())
        else
            GLES32.glUseProgram(defaultShader.getProgram())
        val location=GLES32.glGetUniformLocation(defaultShader.getProgram(),"u_MVPMatrix")
        GLES32.glUniformMatrix4fv(location,1,false,mMVPMatrix,0)
        GLES32.glUniform2f(GLES32.glGetUniformLocation(circleShader.getProgram(),"srcRes"),ResolutionX,ResolutionY)
        GLES32.glUniform1i(GLES32.glGetUniformLocation(defaultShader.getProgram(),"a_isQuad"),if(primitiveType==Primitives.QUAD)1 else 0)
        bindVertexShader()
        bindFragmentShader()
        if(primitiveType == Primitives.QUAD||primitiveType==Primitives.CIRCLE||primitiveType==Primitives.TRIANGLE)
        GLES32.glDrawElements(GLES32.GL_TRIANGLES,icount,GLES32.GL_UNSIGNED_SHORT,drawListBuffer)
        else if(primitiveType == Primitives.LINE||primitiveType==Primitives.POLYLINE){
        GLES32.glDrawElements(GLES32.GL_LINES,icount,GLES32.GL_UNSIGNED_SHORT,drawListBuffer)

        }
    }

    private fun draw() {
        drawListBuffer!!.put(indices)
        drawListBuffer!!.position(0)
        camera?.update(mViewMatrix)
        Matrix.setIdentityM(mModelMatrix,0)
        transform()
        Matrix.multiplyMM(mMVPMatrix,0,mViewMatrix,0,mModelMatrix,0)
        Matrix.multiplyMM(mMVPMatrix,0,camera?.getProjectionMatrix(),0,mMVPMatrix,0)
        render()

    }

    fun initShader(context: Context){
        circleShader.createProgram(context)
        defaultShader.createProgram(context)

    }
}