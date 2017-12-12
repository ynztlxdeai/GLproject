precision mediump float;//指定默认的浮点型精度
varying vec2 vTextureCoord;//接收顶点着色器过来的纹理坐标易变量
uniform sampler2D sTexture;//纹理采样器,代表一副纹理  gl_FragColor = texture2D(sTexture , vTextureCoord);
void main(){
    vec2 temp = vTextureCoord;
    float modA = temp.s * 1920.0;
    if(mod( modA , 2.0) <= 0.0 ){
        temp.s = temp.s / 2.0;
        gl_FragColor = texture2D(sTexture , temp);
    }else {
         temp.s = temp.s / 2.0 + 0.5;
        gl_FragColor = texture2D(sTexture , temp);
    }

    /* gl_FragColor = texture2D(sTexture , vTextureCoord);*/
}