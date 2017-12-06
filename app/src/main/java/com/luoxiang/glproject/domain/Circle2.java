package com.luoxiang.glproject.domain;

import android.content.Context;
import android.opengl.GLES20;

import com.luoxiang.glproject.utils.MatrixState;
import com.luoxiang.glproject.utils.ShaderUtil;

import java.nio.FloatBuffer;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.domain
 * className:	        Circle2
 * author:	            Luoxiang
 * time:	            2017/12/6	16:55
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/12/6
 * upDateDesc:	        TODO
 */
public class Circle2 {
    //指定着色器程序
    int mProgram;
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle; //顶点位置属性引用
    int maColorHandle; //顶点颜色属性引用

    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本

    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲
    FloatBuffer mIndexBuffer;//索引缓冲

    int vCount=0;
    int iCount=0;


    public Circle2(Context context){
        //初始化顶点坐标与着色数据
        initVertexData();
        //初始化shader
        initShader(context);
    }

    private void initVertexData() {

    }

    private void initShader(Context context) {
        //加载顶点着色器的脚本内容
        mVertexShader= ShaderUtil.loadFromAssetsFile("vertex.sh", context.getResources());
        //加载片元着色器的脚本内容
        mFragmentShader= ShaderUtil.loadFromAssetsFile("frag.sh", context.getResources());
        //基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        //获取程序中顶点位置属性引用id
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        //获取程序中顶点颜色属性引用id
        maColorHandle= GLES20.glGetAttribLocation(mProgram, "aColor");
        //获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf()
    {
        //制定使用某套shader程序
        GLES20.glUseProgram(mProgram);
        //将最终变换矩阵传入shader程序
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, MatrixState.getFinalMatrix(), 0);
        //为画笔指定顶点位置数据
        GLES20.glVertexAttribPointer
                (
                        maPositionHandle,
                        3,
                        GLES20.GL_FLOAT,
                        false,
                        3*4,
                        mVertexBuffer
                );
        //为画笔指定顶点着色数据
        GLES20.glVertexAttribPointer
                (
                        maColorHandle,
                        4,
                        GLES20.GL_FLOAT,
                        false,
                        4*4,
                        mColorBuffer
                );
        //允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maColorHandle);
        //采用三角形扇面方式绘制    用索引法绘制
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_FAN,iCount,GLES20.GL_UNSIGNED_BYTE , mIndexBuffer);
    }


}
