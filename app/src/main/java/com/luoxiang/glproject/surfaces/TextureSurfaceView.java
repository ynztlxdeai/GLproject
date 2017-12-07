package com.luoxiang.glproject.surfaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.luoxiang.glproject.R;
import com.luoxiang.glproject.domain.Triangle;
import com.luoxiang.glproject.utils.MatrixState;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.surfaces
 * className:	        TextureSurfaceView
 * author:	            Luoxiang
 * time:	            2017/12/7	11:31
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/12/7
 * upDateDesc:	        TODO
 */
public class TextureSurfaceView extends GLSurfaceView {

    private SceneRenderer mRenderer;
    //纹理ID
    int mTextureId;
    private final float TOUCH_SCALE_FACTOR = 180.0f/320;//角度缩放比例
    private float mPreviousY;//上次的触控位置Y坐标
    private float mPreviousX;//上次的触控位置X坐标

    public TextureSurfaceView(Context context) {
        this(context,null);
    }

    public TextureSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        mRenderer = new SceneRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    //触摸事件回调方法
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;//计算触控笔Y位移
                float dx = x - mPreviousX;//计算触控笔X位移
                mRenderer.mTriangle.yAngle += dx * TOUCH_SCALE_FACTOR;//设置纹理矩形绕y轴旋转角度
                mRenderer.mTriangle.zAngle+= dy * TOUCH_SCALE_FACTOR;//设置第纹理矩形绕z轴旋转角度
        }
        mPreviousY = y;//记录触控笔位置
        mPreviousX = x;//记录触控笔位置
        return true;
    }

    class SceneRenderer implements GLSurfaceView.Renderer{
        Triangle mTriangle;
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
            mTriangle = new Triangle(getContext());
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
            mTriangle.drawSelf(mTextureId);
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
        InputStream inputStream = getResources().openRawResource(R.mipmap.wall);
        Bitmap bitmapTmp        = null;
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
