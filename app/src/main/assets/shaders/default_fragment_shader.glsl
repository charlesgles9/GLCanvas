precision mediump float;
varying vec4 v_color;
varying vec2 pos;
uniform int a_isQuad;
uniform vec2 srcRes;
varying vec4 a_center;
varying vec2 a_rounded_properties;
varying vec4 a_trim;
uniform int sampleId;
uniform int isText;
uniform sampler2D u_texture;
varying vec2 v_TexCoordinate;
uniform float textEdge;
uniform float textWidth;
uniform float textBorderWidth;
uniform float textBorderEdge;
uniform vec3 outlineColor;

float roundedEdge(vec2 pos,vec2 center,vec2 size,float radius,float thickness){
    float hyp=sqrt(size.x*size.x+size.y*size.y);
    float maxHyp=hyp-radius*0.5;
    vec2 pd=abs(pos-center);
    float pd_hyp=sqrt(pd.x*pd.x+pd.y*pd.y);
    float max_pd_hyp=pd_hyp;
    return 1.0-min(step(maxHyp,max_pd_hyp)*radius,1.0);
}

void main(){
 vec2 src,pos,size,min_v,max_v,topLeft,topRight,bottomLeft,bottomRight;
// modify  coordinates to match screen space coordinates
  src.x=gl_FragCoord.x;
  src.y=srcRes.y-gl_FragCoord.y;
  // pixel position
  pos=a_center.xy;
  // quad dimensions
  size=a_center.zw;
  float radius=a_rounded_properties.y;
  float thickness=a_rounded_properties.x;
  // thickness cannot be zero
  if(thickness==0.0)
   thickness=size.x*size.y;
  min_v.x=size.x-radius;
  min_v.y=size.y-radius;
        // apply clip rect
        // lower clip Y
         float booleanLowerY=1.0-step(a_trim.w,src.y);
        // upper clip Y
         float booleanUpperY=1.0-step(src.y,a_trim.y);
         // lower clip X
         float booleanLowerX=1.0-step(a_trim.z,src.x);
            // upper clip X
         float booleanUpperX=1.0-step(src.x,a_trim.x);
         float clip=booleanUpperY*booleanLowerY*booleanUpperX*booleanLowerX;
         float quadV=1.0;


/* if its a quad test if it has rounded corners
    ignore text objects since texts are also quads*/
    float rounded=roundedEdge(src,pos,size,radius,thickness);
    quadV=clip*rounded;
    vec4 quad_color=v_color*quadV;


    if(quad_color.a<(1.0/255.0))
      discard;


   if(isText==1){
      float innerDistance=1.0-texture2D(u_texture,v_TexCoordinate).a;
      float innerAlpha=1.0-smoothstep(textWidth,textWidth+textEdge,innerDistance);
      float borderDistance=1.0-texture2D(u_texture,v_TexCoordinate).a;
      float outlineAlpha=1.0-smoothstep(textBorderWidth,textBorderWidth+textBorderEdge,borderDistance);
      float overallAlpha=innerAlpha+(1.0-innerAlpha)*outlineAlpha;
      vec3 overallColor=mix(outlineColor.rgb,v_color.rgb,innerAlpha/overallAlpha);
      quad_color=vec4(overallColor.rgb,overallAlpha)*clip;
      }


    if(sampleId!=0){
     gl_FragColor=quad_color*texture2D(u_texture,v_TexCoordinate);//*quadV;
      } else{
     gl_FragColor=quad_color;
     }
}