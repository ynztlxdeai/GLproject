package com.luoxiang.glproject.domain;

import android.opengl.GLES20;
import android.view.SurfaceView;

import com.luoxiang.glproject.Constant;
import com.luoxiang.glproject.utils.MatrixState;
import com.luoxiang.glproject.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.domain
 * className:	        PonitOrLines
 * author:	            Luoxiang
 * time:	            2017/12/5	16:02
 * desc:	            绘制方式点和线的学习
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/12/5
 * upDateDesc:	        TODO
 */
public class PonitOrLines {
    int    mProgram;//自定义渲染管线着色器程序id
    int    muMVPMatrixHandle;//总变换矩阵引用
    int    maPositionHandle; //顶点位置属性引用
    int    maColorHandle; //顶点颜色属性引用
    String mVertexShader;//顶点着色器代码脚本
    String mFragmentShader;//片元着色器代码脚本

    FloatBuffer mVertexBuffer;//顶点坐标数据缓冲
    FloatBuffer mColorBuffer;//顶点着色数据缓冲
    int vCount = 0;

    public PonitOrLines(SurfaceView surfaceView) {
        //初始化顶点坐标与着色数据
        initVertexData();
        //初始化shader
        initShader(surfaceView);
    }

    private void initVertexData() {
        vCount = 5;
        //顶点数据处理
        float[] vertices = new float[]{0, 0, 0,
                                       Constant.UNIT_SIZE, Constant.UNIT_SIZE, 0,
                                       -Constant.UNIT_SIZE, Constant.UNIT_SIZE, 0,
                                       -Constant.UNIT_SIZE, -Constant.UNIT_SIZE, 0,
                                       Constant.UNIT_SIZE, -Constant.UNIT_SIZE, 0};
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices)
                     .position(0);

        //颜色数据处理
        float[] colors = new float[]{
                //每个顶点的颜色ＲＧＢＡ
                1, 1, 0, 0,
                1, 1, 1, 0,
                0, 1, 0, 0,
                1, 1, 1, 0,
                1, 1, 0, 0};
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asFloatBuffer();
        mColorBuffer.put(colors)
                    .position(0);

    }

    private void initShader(SurfaceView surfaceView) {
        // 加载顶点着色器的脚本内容
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex.sh", surfaceView.getResources());
        // 加载片元着色器的脚本内容
        mFragmentShader = ShaderUtil.loadFromAssetsFile("frag.sh", surfaceView.getResources());
        // 基于顶点着色器与片元着色器创建程序
        mProgram = ShaderUtil.createProgram(mVertexShader, mFragmentShader);
        // 获取程序中顶点位置属性引用id
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
        // 获取程序中顶点颜色属性引用id
        maColorHandle = GLES20.glGetAttribLocation(mProgram, "aColor");
        // 获取程序中总变换矩阵引用id
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
    }

    public void drawSelf() {
        // 制定使用某套shader程序
        GLES20.glUseProgram(mProgram);
        // 将最终变换矩阵传入shader程序
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false,
                                  MatrixState.getFinalMatrix(), 0);
        // 为画笔指定顶点位置数据
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT,
                                     false, 3 * 4, mVertexBuffer);
        // 为画笔指定顶点着色数据
        GLES20.glVertexAttribPointer(maColorHandle, 4, GLES20.GL_FLOAT, false,
                                     4 * 4, mColorBuffer);
        // 允许顶点位置数据数组
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maColorHandle);

        GLES20.glLineWidth(10);//设置线的宽度
        //绘制点或线
        switch (Constant.CURR_DRAW_MODE) {
            case Constant.GL_POINTS:// GL_POINTS方式
                GLES20.glDrawArrays(GLES20.GL_POINTS, 0, vCount);
                break;
            case Constant.GL_LINES:// GL_LINES方式
                GLES20.glDrawArrays(GLES20.GL_LINES, 0, vCount);
                break;
            case Constant.GL_LINE_STRIP:// GL_LINE_STRIP方式
                GLES20.glDrawArrays(GLES20.GL_LINE_STRIP, 0, vCount);
                break;
            case Constant.GL_LINE_LOOP:// GL_LINE_LOOP方式
                GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vCount);
                break;
        }
    }
}
