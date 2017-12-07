package com.luoxiang.glproject.domain;

import android.content.Context;
import android.opengl.GLES20;

import com.luoxiang.glproject.utils.MatrixState;
import com.luoxiang.glproject.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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
    public float xAngle;
    public float yAngle;
    public float zAngle;

    public Triangle(Context context){
        initVertexData();
        initShader(context);
    }

    private void initShader(Context context) {
        mVertexShader = ShaderUtil.loadFromAssetsFile("vertex_texture.sh" , context.getResources());

        mFragShader = ShaderUtil.loadFromAssetsFile("frag_texture.sh" , context.getResources());

        mProgram = ShaderUtil.createProgram(mVertexShader , mFragShader);

        maPositionHandle = GLES20.glGetAttribLocation(mProgram , "aPosition");
        maTexCoorHandle = GLES20.glGetAttribLocation(mProgram , "aTexCoor");
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram , "uMVPMatrix");
    }

    private void initVertexData() {
        vCount = 3;
        final float UNIT_SIZE = 0.15F;
        float[] vertices = new float[]{
                0 * UNIT_SIZE , 11 * UNIT_SIZE , 0,
                -11* UNIT_SIZE , -11 * UNIT_SIZE , 0,
                11 * UNIT_SIZE , -11 * UNIT_SIZE , 0,
        };
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices).position(0);

        float[] texCoor = new float[]{
                0.5f , 0,
                0,1,
                1,1
        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length *4);
        cbb.order(ByteOrder.nativeOrder());
        mTexCoorBuffer = cbb.asFloatBuffer();
        mTexCoorBuffer.put(texCoor).position(0);
    }

    public void drawSelf(int texId){
        GLES20.glUseProgram(mProgram);
        MatrixState.setInitStack();
        //Z轴移动1
        MatrixState.translate(0,0,1);

        MatrixState.rotate(yAngle , 0 , 1 , 0);
        MatrixState.rotate(zAngle , 0 , 0 , 1);
        MatrixState.rotate(xAngle , 1 , 0 , 1);

        //最终变换矩阵传给渲染管线
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle , 1 , false , MatrixState.getFinalMatrix() , 0);

        GLES20.glVertexAttribPointer(maPositionHandle , 3 , GLES20.GL_FLOAT , false , 3 *4 , mVertexBuffer);
        GLES20.glVertexAttribPointer(maTexCoorHandle , 2 , GLES20.GL_FLOAT , false , 2 *4 , mTexCoorBuffer);

        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maTexCoorHandle);

        //设置使用纹理编号
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        //绑定指定纹理id
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D , texId);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES , 0 , vCount);

    }
}
