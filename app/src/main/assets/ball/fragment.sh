//精度设置为中等
precision medium float;
//球体的半径
uniform float uR;
//接收顶点着色器传递的顶点位置
varying vec3 vPosition;

void main(){

    vec3 color;
    //外接立方体每个坐标轴方向切分的分数
    float n = 8.0;
    //每一份的尺寸大小
    float span = 2.0*uR / n;
    //当前片元小方块的行数
    int i = int((vPosition.x +uR) /span);
    //当前片元小方块的层数
    int j = int((vPosition.y +uR) /span);
    //当前片元小方块的列数
    int k = int((vPosition.z + uR) /span);

    //计算当前的片元 行 层 列数的和并且对2取模
    int whichColor = int(mod((float)(i + k + j) , 2.0));

    if(whichColor == 1){
        //单数的时候为红色
        color = vec3(0.678 , 0.231 , 0.129);
    }else {
        //偶数的时候为白色
        color = vec3(1.0 , 1.0 , 1.0);
    }

    //计算出片元颜色 传递给管线
    gl_FragColor = vec4(color , 1.0);
}
