precision mediump float;//指定默认的浮点型精度
varying vec2 vTextureCoord;//接收顶点着色器过来的纹理坐标易变量
uniform sampler2D sTexture;//纹理采样器,代表一副纹理
void main(){
    gl_FragColor = texture2D(sTexture , vTextureCoord);
}