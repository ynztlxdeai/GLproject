package com.luoxiang.glproject.domain;

import android.content.Context;
import android.opengl.GLES20;

import com.luoxiang.glproject.utils.ShaderUtil;

import java.nio.FloatBuffer;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.domain
 * className:	        ImageLR
 * author:	            Luoxiang
 * time:	            2017/12/8	9:30
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/12/8
 * upDateDesc:	        TODO
 */
public class ImageLR {
    int mProgram;//着色器程序id
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle;//顶点位置引用
    int maTexCoorHandle;//顶点纹理坐标属性引用

    String mVertexShader;//顶点着色器
    String mFragShader;//片元着色器

    FloatBuffer mVertexBuffer;
    FloatBuffer mTexCoorBuffer;//顶点纹理坐标数据缓冲

    int vCount;

    public ImageLR(Context context){
        initVertexData();
        initShader(context);
    }

    private void initVertexData() {

    }

    private void initShader(Context context) {
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex_img.sh" , context.getResources());

        mFragShader = ShaderUtil.loadFromAssetsFile("frag_img.sh" , context.getResources());

        mProgram = ShaderUtil.createProgram(mVertexShader , mFragShader);

        maPositionHandle = GLES20.glGetAttribLocation(mProgram , "aPosition");
        maTexCoorHandle = GLES20.glGetAttribLocation(mProgram , "aTexCoor");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram , "uMVPMatrix");
    }

    public void drawSelf(int textureId) {

    }
}
