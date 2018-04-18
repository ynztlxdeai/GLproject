package com.luoxiang.glproject.domain;

import android.content.Context;
import android.opengl.GLES20;

import com.luoxiang.glproject.utils.MatrixState;
import com.luoxiang.glproject.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

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
    public static final float UNIT_SIZE = 1.0f;
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
        mVertexShader = ShaderUtil.loadFromAssetsFile("/ball/vertex.sh" , context.getResources());

        mFragmentShader = ShaderUtil.loadFromAssetsFile("/ball/fragment.sh" , context.getResources());

        mProgram = ShaderUtil.createProgram(mVertexShader , mFragmentShader);

        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram , "uMVPMatrix");
        maPositionHandle = GLES20.glGetAttribLocation(mProgram , "aPosition");

        muRHandle = GLES20.glGetUniformLocation(mProgram , "uR");
    }

    //顶点坐标初始化
    private void initVertexData() {
        ArrayList<Float> alVertexs = new ArrayList<>();
        //切分角度 越小越是精密
        int angleSpan = 10;
        for (int vAngle = -90; vAngle < 90; vAngle += angleSpan) {
            for (int hAngle = 0; hAngle < 360; hAngle += angleSpan) {
                float x0 = (float) (r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math.toRadians(hAngle)));
                float y0 = (float) (r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math.toRadians(hAngle)));
                float z0 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));

                float x1 = (float) (r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle)) * Math.cos(Math.toRadians(hAngle + angleSpan)));
                float y1 = (float) (r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle)) * Math.sin(Math.toRadians(hAngle + angleSpan)));
                float z1 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle)));

                float x2 = (float) (r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.cos(Math.toRadians(hAngle + angleSpan)));
                float y2 = (float) (r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.sin(Math.toRadians(hAngle + angleSpan)));
                float z2 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle + angleSpan)));

                float x3 = (float) (r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.cos(Math.toRadians(hAngle)));
                float y3 = (float) (r * UNIT_SIZE * Math.cos(Math.toRadians(vAngle + angleSpan)) * Math.sin(Math.toRadians(hAngle)));
                float z3 = (float) (r * UNIT_SIZE * Math.sin(Math.toRadians(vAngle + angleSpan)));

                //四个顶点的坐标按照卷绕成两个三角形的需要依次存入列表
                alVertexs.add(x1);alVertexs.add(y1);alVertexs.add(z1);
                alVertexs.add(x3);alVertexs.add(y3);alVertexs.add(z3);
                alVertexs.add(x0);alVertexs.add(y0);alVertexs.add(z0);
                alVertexs.add(x1);alVertexs.add(y1);alVertexs.add(z1);
                alVertexs.add(x2);alVertexs.add(y2);alVertexs.add(z2);
                alVertexs.add(x3);alVertexs.add(y3);alVertexs.add(z3);
            }
        }
        vCount = alVertexs.size() / 3;
        float[] vertexs =  new float[vCount * 3];
        for (int i = 0; i < vertexs.length; i++) {
            vertexs[i]= alVertexs.get(i);
        }
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertexs.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertexs).position(0);

    }

    public void drawSelf(){

        //指定shader程序
        GLES20.glUseProgram(mProgram);
        //将最终的变换矩阵传入shader程序
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle , 1 , false , MatrixState.getFinalMatrix(), 0);

        //半径传给渲染管线
        GLES20.glUniform1f(muRHandle , r * UNIT_SIZE);
        //顶点传给渲染管
        GLES20.glVertexAttribPointer(maPositionHandle , 3 , GLES20.GL_FLOAT ,
                                     false , 3 * 4 , mVertexBuffer);
        //允许渲染顶点位置
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        //绘制球
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES , 0 , vCount);
    }
}
