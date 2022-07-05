 uniform mat4 u_MVPMatrix;
 attribute vec4 a_position;
 attribute vec4 a_transform;
 attribute vec4 a_color;
 attribute vec4 v_center;
 attribute vec2 v_rounded_properties;
 attribute vec4 v_trim;
 varying vec4 a_center;
 varying vec4 v_color;
 varying vec2 a_rounded_properties;
 varying vec4 a_trim;
 varying vec2 pos;
 attribute vec2 a_TexCoordinate;
 varying vec2 v_TexCoordinate;
void main(){
  // transformation matrix
  mat3 rotZ=mat3(cos(a_transform.z),sin(a_transform.z),0.0, -sin(a_transform.z),cos(a_transform.z),0.0, 0.0,0.0,1.0);
  mat3 rotX=mat3(1.0,0.0,0.0, 0.0,cos(a_transform.x),-sin(a_transform.x), 0.0,sin(a_transform.x),cos(a_transform.x));
  mat3 rotY=mat3(cos(a_transform.y),0.0,sin(a_transform.y), 0.0,1.0,0.0, -sin(a_transform.y),0.0,cos(a_transform.y));
  vec4 a_pos=a_position;
  //push this point back to the origin
  a_pos.x-=v_center.x;
  a_pos.y-=v_center.y;
  a_pos.z-=v_center.z;
  a_pos.xyz=rotZ*a_pos.xyz;
  a_pos.xyz=rotY*a_pos.xyz;
  a_pos.xyz=rotX*a_pos.xyz;
  // after transformations push it back to it's original position
  a_pos.x+=v_center.x;
  a_pos.y+=v_center.y;
  a_pos.z+=v_center.z;
  gl_Position=u_MVPMatrix*a_pos;
  v_color=a_color;
  a_center=v_center;
  a_trim=v_trim;
  a_rounded_properties=v_rounded_properties;
  v_TexCoordinate=a_TexCoordinate;
 }