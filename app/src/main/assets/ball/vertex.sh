//总变换矩阵
uniform mat4 uMVPMatrix;
//顶点坐标属性 从管线接收
attribute vec3 aPosition;
//用于传递给片元着色器的顶点位置
varying vec3 vPosition;
void main(){
    //根据总变换矩阵计算当前的顶点位置
    gl_Position = uMVPMatrix * vec4(aPsition , 1);
    //原始的顶点位置传递给片元着色器
     vPosition = aPosition;
}