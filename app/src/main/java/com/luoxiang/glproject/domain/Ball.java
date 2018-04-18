package com.luoxiang.glproject.domain;

import android.content.Context;
import android.opengl.GLES20;

import java.nio.FloatBuffer;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.domain
 * className:	        Ball
 * author:	            Luoxiang
 * time:	            2018/4/18	上午10:15
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    luoxiang
 * upDate:	            2018/4/18
 * upDateDesc:	        TODO
 */


public class Ball {
    //渲染管着色程序
    int mProgram;
    //总变换矩阵引用
    int muMVPMatrixHandle;
    //顶点位置属性引用
    int maPositionHandle;
    //球的半径参数引用
    int muRHandle;
    //顶点着色器代码
    String mVertexShader;
    //片元着色器代码
    String mFragmentShader;
    //顶点坐标数据缓冲区
    FloatBuffer mVertexBuffer;
    //顶点数量
    int vCount;
    //绕X Y Z 旋转的角度
    float yAngle = 0;
    float xAngle = 0;
    float zAngle = 0;
    //球半径
    float r = 0.8f;


    public Ball(Context context) {
        initVertexData();
        initShader(context);
    }

    //顶点着色器和片元着色器加载
    private void initShader(Context context) {

        muRHandle = GLES20.glGetUniformLocation(mProgram , "uR");
    }

    //顶点坐标初始化
    private void initVertexData() {

    }

    public void drawSelf(){


    }
}
