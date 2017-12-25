package com.luoxiang.glproject.surfaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.AttributeSet;

import com.luoxiang.glproject.R;
import com.luoxiang.glproject.domain.ImageAngle;
import com.luoxiang.glproject.domain.ImageLR;
import com.luoxiang.glproject.utils.MatrixState;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.surfaces
 * className:	        TextureImgSurfaceView
 * author:	            Luoxiang
 * time:	            2017/12/8	9:27
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/12/8
 * upDateDesc:	        TODO
 */
public class TextureAngleSurfaceView
        extends GLSurfaceView {
    //纹理ID
    int mTextureId;
    SceneRenderer mRenderer;

    public TextureAngleSurfaceView(Context context) {
        this(context , null);
    }

    public TextureAngleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        mRenderer = new SceneRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    class SceneRenderer implements Renderer{
        ImageAngle mImageAngle;
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            mImageAngle = new ImageAngle(getContext());
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            initTexture();
            GLES20.glDisable(GLES20.GL_CULL_FACE);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置
            GLES20.glViewport(0, 0, width, height);
            //计算GLSurfaceView的宽高比
            float ratio = (float) width / height;
            //调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 1, 10);
            //调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0,0,3,0f,0f,0f,0f,1.0f,0.0f);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            mImageAngle.drawSelf(mTextureId);
        }
    }

    private void initTexture() {
        //生成纹理id
        int[] textures = new int[1];
        /**
         * 生成纹理的数量1
         * 生成纹理id的数组
         * 生成纹理id的偏移量0
         */
        GLES20.glGenTextures(1 , textures , 0);
        mTextureId = textures[0];
        //绑定纹理id
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D , mTextureId);
        //设置min采样方式
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D , GLES20.GL_TEXTURE_MIN_FILTER , GLES20.GL_NEAREST);
        //MAG采样方式
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D , GLES20.GL_TEXTURE_MAG_FILTER , GLES20.GL_LINEAR);
        //设置S轴拉伸方式
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D , GLES20.GL_TEXTURE_WRAP_S , GLES20.GL_CLAMP_TO_EDGE);
        //设置T轴拉伸样式
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D , GLES20.GL_TEXTURE_WRAP_T , GLES20.GL_CLAMP_TO_EDGE);

        //通过流载入图片
        InputStream inputStream = getResources().openRawResource(R.mipmap.study);
        Bitmap      bitmapTmp   = null;
        try {
            bitmapTmp = BitmapFactory.decodeStream(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /**
         * 加载纹理进入显存
         * GLES20.GL_TEXTURE_2D纹理类型
         * 纹理层次0,0表示基本图像层,可以理解为直接贴图
         * bitmapTmp纹理图片
         * 纹理边框0
         */
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D , 0 , bitmapTmp , 0);
        bitmapTmp.recycle();
    }
}
