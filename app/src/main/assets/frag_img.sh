precision mediump float;//指定默认的浮点型精度
varying vec2 vTextureCoord;//接收顶点着色器过来的纹理坐标易变量
uniform sampler2D sTexture;//纹理采样器,代表一副纹理
void main(){
    //gl_FragColor = texture2D(sTexture , vTextureCoord);
    vec2 temp = vTextureCoord;
    if(mod(temp.s * 1920 , 2.0) == 0 ){
        temp.s = temp.s / 2;
        gl_FragColor = texture2D(sTexture , temp);
    }else {
         temp.s = temp.s / 2 + 0.5;
        gl_FragColor = texture2D(sTexture , temp);
    }
}