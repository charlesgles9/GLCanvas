# Documentation

This is an opengl Es 3.0 graphics library for android devices. You can use it for prototyping, 2D games and simulations. The texture atlas and the UI system are not yet fully stable still have some improvements to make. Would be cool if i add feature like lighting, custom shaders and postprocessing effects.


## Setup and Intergration


You can import the library by downloading the release extract the zip file then import it to android studio or intellij as a module project.
Click on File->New-> importModule

![impoortFromSource](https://user-images.githubusercontent.com/41951671/184604501-f1d6a704-24b3-4145-ba2f-8c488f75e4dc.png)

*Alternative*

1. Download the release extract the zip file then copy it directly to the project directory. 
2. Open setting.gradle and add the following line

```kotlin
include ':GLCanvas'
```
![setup1](https://user-images.githubusercontent.com/41951671/184604918-13cf4fa5-4fa2-4d94-b382-67291a311c8c.png)


3. Open app:build.gradle and in the dependancies tree add the following line.

```kotlin
implementation project(':GLCanvas')
```

![setup2](https://user-images.githubusercontent.com/41951671/184605238-c4911be0-1061-4df1-9883-b00d114f5022.png)


## How to Use

### 1.0 Setting up a renderer

 Create a renderer class and extend GLRendererView. GLRendererView is a wrapper that overrides GLSurfaceView.Renderer methods and accepts two arguments the surfaceWidth and surfaceHeight. It keeps all the bloat away.

```kotlin

class GLCanvasRenderer(private val context: Context,width: Float, height: Float) : GLRendererView(width, height) {

//init  resources eg textures, fonts, camera and batches
    override fun prepare() {
       
    }

// draw objects every frame here
 override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0f,0f,0f,1f)

  }

// update your logic here 
 override fun update(delta: Long) {
        

    }

//delete all textures and clean opengl memory here when the app exits
 override fun onRelease() {
     
    }
```

### 1.1 Setting up a SurfaceView

 In your mainActivity create a GLCanvasSurfaceView object and set the renderer.

```kotlin

class MainActivity : AppCompatActivity() {

    private var surface: GLCanvasSurfaceView?=null
    private var renderer:GLCanvasRenderer?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //create a renderer
        renderer=GLCanvasRenderer(this ,1280.0f,720.0f)
        // pass in the application context and renderer
        surface= GLCanvasSurfaceView(this, renderer!!)
        // set this surface to view
        setContentView(surface)

    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }


    override fun onDestroy() {
        super.onDestroy()
        renderer?.onRelease()
    }
}
```

## 1.2 Create a Camera and a Batch

 The camera helps us project stuff on the screen.
 The batch helps us draw various objects on the screen using OpenGL functions.

```kotlin

class GLCanvasRenderer(private val context: Context,width: Float, height: Float) : GLRendererView(width, height) {
 
    private val batch = Batch()
    // creates a camera with a height value 
    private val camera= Camera2D(10.0f)

//init  resources eg textures, fonts, camera and batches
    override fun prepare() {
      // initializes the shaders being used by the batch 
       batch.initShader(context)
     // creates an orthogonal projection 
      camera.setOrtho( getCanvasWidth(), getCanvasHeight())
    }

// draw objects every frame here
 override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0f,0f,0f,1f)

  }

// update your logic here 
 override fun update(delta: Long) {
        

    }

//delete all textures and clean opengl memory here when the app exits
 override fun onRelease() {
     
    }
```

## 2.0 Rendering Objects


**A. Quads**

```kotlin
 
// creates a square of coordinates x,y, width and height
private var rect=RectF(300f,300f,100f,100f)


// draw quad in the draw loop
override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0f,0f,0f,1f)

        // prepare the batch for drawing
        batch.begin(camera)
        // pass in the rect object for drawing
        batch.draw(rect)
        //close the batch, render everything on the screen and flush
        batch.end()

}
```

**functions**
   1. *setConnerRadius-* creates a rounded rect object
   2. *setThickness-* creates a hollow quad.
  

**B. Circles**

```kotlin

// creates circle at coordinates x,y radius
 private var circle=Circle(100f,600f,50f)

// draw quad in the draw loop
override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        //clear the surface to color black
        GLES32.glClearColor(0f,0f,0f,1f)

        // prepare the batch for drawing
        batch.begin(camera)
        // pass in the circle object for drawing
        batch.draw(circle)
        //close the batch, render everything on the screen and flush
        batch.end()

}
```

**C. Triangle**

```kotlin
// creates a polygon object
 private var triangle=Polygon()

       //move to origin
        triangle.moveTo(300f,790f)
        //first triangle
        triangle.lineTo(300f,850f)
        triangle.lineTo(350f,850f)
        //second triangle
        triangle.lineTo(250f,850f)
        triangle.lineTo(300f,850f)

// draw quad in the draw loop
   override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0f,0f,0f,1f)
        // prepare the batch for drawing
        batch.begin(camera)
        // pass in the polygon object for drawing
        batch.draw(polygon)
        //close the batch, render everything on the screen and flush
        batch.end()

}
```

The polygon object is not limited to just two triangles you can create complex shapes too. If the polygon coordinates keep changing every frame call the reset function to clear previous vertex data.

```kotlin

//clear previous vertex data
triangle.reset()
```

**D. PolyLine**


```kotlin

private var polyline=PolyLine()

       //creates an an array of lines based on an origin point
       // moveTo creates an origin 
       // lineTo creates a line connected to the defined origin
        polyline.moveTo(100f,100f)
        polyline.lineTo(200f,100f)
        polyline.moveTo(200f,100f)
        polyline.lineTo(200f,300f)
        polyline.moveTo(50f,300f)
        polyline.lineTo(200f,300f)
        polyline.moveTo(50f,300f)
        polyline.lineTo(50f,400f)
        polyline.moveTo(50f,400f)
        polyline.lineTo(100f,400f)

// draw quad in the draw loop
   override fun draw() {
        GLES32.glClear(GLES32.GL_DEPTH_BUFFER_BIT or  GLES32.GL_COLOR_BUFFER_BIT)
        GLES32.glClearColor(0f,0f,0f,1f)
        // prepare the batch for drawing
        batch.begin(camera)
        // pass in the polyline object for drawing
        batch.draw(polyline)
        //close the batch, render everything on the screen and flush
        batch.end()

}
```

Just like the polygon object you can clear the data from the polyline if it changes
every frame. Using the reset function ofcourse.

```kotlin

//clear previous vertex data
polyline.reset()
```

## 3.0 Colors and Gradients

In **GLCanvas** there is a **ColorRGBA** object that is used to define colors data.

**constructors**
  1. *ColorRGBA(value)-* Initializes all the values in the object with a single scalar value.
  2. *ColorRGBA()-* Creates a color object with default values of 1.0. <sup>(White)</sup>
  3. *ColorRGBA(color)-* Copies data from another color object.
  4. *ColorRGBA(r,g,b,a)-* Initializes the color object with different RGBA values.

**functions**
 1. *darken-* accepts two arguments as inputs.
    a. A factor of float ranging from (0.0f-1.0f). Lower values will make the color darker.
    b. A target ColorRGBA object.
 2. *set-* modifies the RGBA valuesa of the color object .
 3. *setAlpha-* modifies the alpha value. 
 4. *get-* returns a single RGBA value using an index position.
 5. *getData-* returns an array of RGBA values.

**constants**
  a *R- * red value.
  b *G- * green value.
  c *B- * blue value.
  d *A- * alpha value.
  e *default colors-* transparent, red, white ,blue, green, gray, lightGray, yellow, pink, cyan.
 
Each of the rendereable objects contains properties like colors and gradients that can be tweaked according to your needs.

**functions**

1. *setColor-* accepts a ColorRGBA object as an input to change the model's color.
2. *gradient-* accepts an array of colors per vertex arrays to create a smooth gradient.


## 3.1 Texures

Textures can be loaded from the asset folder using the Texture class.
Only load the textures in the onPrepare method.

```kotlin
private var texture:Texture?=null

override fun prepare(){

 texture=Texture(context,"texture.png")
}
```

Texures are binded to objects using the *setTexure* function.

## 3.2 Transformations

**functions**

 1. *rotations:-*
     - setRotationX(angleInDegrees)- rotates the object across the x axis.
     - setRotationY(angleInDegrees)- rotates the object across the y axis.
     - setRotationZ(angleInDegrees)- rotates the object across the center.

 2. *scale:-*
     - setScale(scaleX,ScaleY)- scales an entity.

## 4.0 SpriteSheet and Animation

GLCanvas supports spriteSheet 2D animations. You can also define the spritesheet coordinates manually or populate the data from an external parser.

Spritesheets animations can be created for shapes such as circles and quads. The animations are defines by an animationFrame object passed in the animator class. Every object is automatically initialized with a default (1,1) spriteSheet.

```kotlin
//define a quad object
private var rect=RectF(300f,300f,200f,200f)

 init{
//loads a 4x4 spriteSheet
 rect.setTexture(Texture(context,"flyingDragon.png")
//specify the width and height of the spritesheet
 rect.setSpriteSheet(SpriteSheet(4,4))

/*create an animator object and pass in animation frames using the AnimationFrame class. Sprite animator accepts an animation id as a string , animationFrame object and the spritesheet object*/

// first let's create the frames
 val flyFrame=AnimationFrame(
            //the animation time fro each frame
            arrayOf(100L,100L,100L,100L),
            //frames to show in the texture
            arrayOf(0,1,2,3))

/*then create the animator object this will update the animation every frame in the update method*/
val animator=SpriteAnimator("fly",flyFrame,rect.getSpriteSheet())

rect.setAnimator(animator)

//other constraints
//activates the animation as active
animator.setActivated(true)
//loops throught the animation forever
animator.setLooping(true)
// sets the current animation
animator.setCurrent("fly")

}

....

// the animation will be automatically be updated in the update method
 override fun update(delta: Long) {
   rect.update(delta)

  }
```

**SpriteSheet Functions**

 - *resize(width,height)- resizes the spritesheet with new values.
 - *clone*-creates an identical copy of the sheet.
 - *getCurrentFrame*- returns the current active array of frames.
 - *getFrameAt*-returns an array of floats for a given frame.


**AnimationFrame Functions**

 - *setFrameLength(arrayofLong)*-sets the timer for each frame.
 - *setFrames(arrayOfInts)*- sets the frames for this animation.
 - *getFrames()*-returns the array of frames.
 - *getFrameLength()*-returns the array of frameLength.
 - *getCurrent()*-returns the current active frame index.
 - *reset()*- resets the animationFrame.

**SpriteAnimator functions**

 - *put(name, animationFrame,sheet)*- adds an animationFrame to the animator.
 - *setCurrent(key)*-sets the current animationFrame.
 - *getCurrentFrame()*-returns the current active animationFrame.
 - *isActivated()*- returns a boolean flag indicating whether the animation is active.
 - *isLooping()*- returns a boolean flag. Test is looping is enabled.
 - *setLooping(boolean)*-set the loop flag.
 - *reset()*-reset the animation counter.
 - *update(delta)*-updates the animation everyFrame.
 
