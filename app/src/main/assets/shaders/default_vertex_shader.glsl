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
  v_color=a_color;
  mat2 rotZ=mat2(cos(a_transform.z),sin(a_transform.z),-sin(a_transform.z),cos(a_transform.z));
  mat2 rotX=mat2(cos(a_transform.x),-sin(a_transform.x),sin(a_transform.x),cos(a_transform.x));
  mat2 rotY=mat2(cos(a_transform.y),sin(a_transform.y),-sin(a_transform.y),cos(a_transform.y));
  vec4 a_pos=a_position;
  a_pos.xy=rotZ*a_position.xy;
  a_pos.xy=rotX*a_pos.xy;
  gl_Position=u_MVPMatrix*a_pos;
  a_center=v_center;
  a_trim=v_trim;
  a_rounded_properties=v_rounded_properties;
  v_TexCoordinate=a_TexCoordinate;
 }