package com.luoxiang.glproject.domain;

import android.content.Context;

import java.nio.FloatBuffer;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.domain
 * className:	        Triangle
 * author:	            Luoxiang
 * time:	            2017/12/7	11:40
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/12/7
 * upDateDesc:	        TODO
 */
public class Triangle {
    int mProgram;//着色器程序id
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle;//顶点位置引用
    int maTexCoorHandle;//顶点纹理坐标属性引用

    String mVertexShader;//顶点着色器
    String mFragShader;//片元着色器

    FloatBuffer mVertexBuffer;
    FloatBuffer mTexCoorBuffer;//顶点纹理坐标数据缓冲

    int vCount;
    float xAngle;
    float yAngle;
    float zAngle;

    public Triangle(Context context){
        initVertexData();
        initShader(context);
    }

    private void initShader(Context context) {

    }

    private void initVertexData() {

    }

    public void drawSelf(int texId){

    }
}
