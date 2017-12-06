package com.luoxiang.glproject.domain;

import android.content.Context;
import android.opengl.GLES20;

import com.luoxiang.glproject.Constant;
import com.luoxiang.glproject.utils.MatrixState;
import com.luoxiang.glproject.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.domain
 * className:	        Circle
 * author:	            Luoxiang
 * time:	            2017/12/6	10:12
 * desc:	            三角形扇面绘制类
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/12/6
 * upDateDesc:	        TODO
 */
public class Circle {
    //指定着色器程序
    int mProgram;
    int muMVPMatrixHandle;//总变换矩阵引用
    int maPositionHandle; //顶点位置属性引用
    int maColorHandle; //顶点颜色属性引用

    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本

    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲

    int vCount=0;

    public Circle(Context context){
        //初始化顶点坐标与着色数据
        initVertexData();
        //初始化shader
        initShader(context);
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

    private void initVertexData() {
        int n = 10;
        vCount = n + 2;
        float angleSpan = 360.0f / n;

        float[] vertices = new float[vCount * 3];
        int count = 0;
        //第一个坐标点
        vertices[count++] = 0;
        vertices[count++] = 0;
        vertices[count++] = 0;

        for (float angleTemp = 0; Math.ceil(angleTemp) <= 360 ; angleTemp += angleSpan) {
            double angrad = Math.toRadians(angleTemp);

            vertices[count++] = (float)(-Constant.UNIT_SIZE * Math.sin(angrad));
            vertices[count++] = (float)(Constant.UNIT_SIZE * Math.cos(angrad));
            vertices[count++] = 0;
        }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices).position(0);

        float[] colors = new float[vCount * 4];
        count = 0;
        colors[count++] = 1;
        colors[count++] = 1;
        colors[count++] = 1;
        colors[count++] = 0;
        for (int i = 4; i < colors.length; i += 4) {
            colors[count++] = 0;
            colors[count++] = 1;
            colors[count++] = 0;
            colors[count++] = 0;
        }
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asFloatBuffer();
        mColorBuffer.put(colors).position(0);
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
        //采用三角形扇面方式绘制
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN,0, vCount);
    }

}
