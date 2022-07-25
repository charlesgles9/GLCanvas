precision mediump float;
varying vec2 pos;
uniform int sampleId;
uniform int isText;
uniform sampler2D u_texture;
uniform float isQuad;
uniform vec2 srcRes;
varying vec4 v_center;
varying vec2 v_rounded_properties;
varying vec4 v_trim;
varying vec4 v_color;
varying vec4 v_gradient;
varying vec2 v_TexCoordinate;
varying vec4 v_distanceFieldColor;
varying vec4 v_distanceFieldBounds;


float roundedEdge(vec2 pos,vec2 center,vec2 size,float radius,float thickness){
  // the outer radius
    float arc=length(max(abs(pos-center)-size+radius,0.0));
    float len=length(size)*0.5;
    // the inner radius
    float inner=length(max(abs(pos-center)-size+len,0.0));
    float t=smoothstep(-1.5,1.5,abs(inner)-len+thickness);
    float r=1.0-smoothstep(-1.5,1.5,abs(arc)-radius);
    // make sure thickness and radius values are not zero
     t=t+step(thickness,0.0);
     r=r+step(radius,0.0);

    return (t*r);
}

void main(){
 vec2 src,pos,size;
// modify  coordinates to match screen space coordinates
  src.x=gl_FragCoord.x;
  src.y=srcRes.y-gl_FragCoord.y;
  // pixel position
  pos=v_center.xy;
  // quad dimensions
  size=v_center.zw;
  float radius=v_rounded_properties.y;
  float thickness=v_rounded_properties.x;
        // apply clip rect
        // lower clip Y
         float booleanLowerY=1.0-step(v_trim.w,src.y);
        // upper clip Y
         float booleanUpperY=1.0-step(src.y,v_trim.y);
         // lower clip X
         float booleanLowerX=1.0-step(v_trim.z,src.x);
            // upper clip X
         float booleanUpperX=1.0-step(src.x,v_trim.x);
         float clip=booleanUpperY*booleanLowerY*booleanUpperX*booleanLowerX;
         float quadV=1.0;


/* if its a quad test if it has rounded corners
    ignore text objects since texts are also quads*/
    float rounded=roundedEdge(src,pos,size,radius,thickness);
    /*prevents glitches in non-quad shapes all non-quad shapes should have a
     value of 1.0 */
    rounded=min(1.0,rounded+1.0-isQuad);
    quadV=clip*rounded;
    vec4 quad_color=v_color*quadV;


    if(quad_color.a<(1.0/255.0))
        discard;

   if(isText==1){
      float innerDistance=1.0-texture2D(u_texture,v_TexCoordinate).a;
      float innerAlpha=1.0-smoothstep(v_distanceFieldBounds.x,v_distanceFieldBounds.x+v_distanceFieldBounds.y,innerDistance);
      float borderDistance=1.0-texture2D(u_texture,v_TexCoordinate).a;
      float outlineAlpha=1.0-smoothstep(v_distanceFieldBounds.z,v_distanceFieldBounds.z+v_distanceFieldBounds.w,borderDistance);
      float overallAlpha=innerAlpha+(1.0-innerAlpha)*outlineAlpha;
      vec3 overallColor=mix(v_distanceFieldColor.rgb,v_color.rgb,innerAlpha/overallAlpha);
      quad_color=vec4(overallColor.rgb,overallAlpha)*clip;
      }


    if(sampleId!=0)
     gl_FragColor=quad_color*texture2D(u_texture,v_TexCoordinate);
       else
     gl_FragColor=quad_color;
}