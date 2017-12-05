package com.luoxiang.glproject.surfaces;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.luoxiang.glproject.Constant;
import com.luoxiang.glproject.domain.Cube;
import com.luoxiang.glproject.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.surfaces
 * className:	        TranslationSurfaceView
 * author:	            Luoxiang
 * time:	            2017/11/30	9:20
 * desc:	            GLES缩放用的surfaceview
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/11/30
 * upDateDesc:	        TODO
 */


public class ScaleSurfaceView
        extends GLSurfaceView {

    //场景渲染器
    private SceneRenderer mSceneRenderer;


    public ScaleSurfaceView(Context context) {
        this(context , null);
    }

    public ScaleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    /**
     * 初始化
     */
    private void init() {
        //设置使用OpenGL ES 2.0
        setEGLContextClientVersion(2);
        //渲染器初始化
        mSceneRenderer = new SceneRenderer();
        //设置渲染器
        setRenderer(mSceneRenderer);
        //模式是连续不断地
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    private class SceneRenderer
            implements Renderer
    {
        //立方体的引用对象
        Cube mCube;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置背景颜色
            GLES20.glClearColor(0.5f , 0.5f , 0.5f , 1.0f);
            mCube = new Cube(ScaleSurfaceView.this);
            //深度测试
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //背面裁剪
            GLES20.glEnable(GLES20.GL_CULL_FACE);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0 , 0 , width , height);
            Constant.ratio = (float)width / height;
            //透视投影矩阵
            MatrixState.setProjectFrustum(-Constant.ratio * 0.8f , Constant.ratio * 1.2f , -1 , 1 , 7 , 100);
            //设置摄像机位置矩阵
            MatrixState.setCamera(-16f , 8f , 45 , 0f , 0f , 0f , 0f , 1.0f , 0.0f );
            //初始化矩阵堆栈
            MatrixState.setInitStack();
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清楚深度和颜色缓冲
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

            //保护现场
            MatrixState.pushMatrix();
            //绘制原来的立方体
            mCube.drawSelf();
            //恢复现场
            MatrixState.popMatrix();


            //绘制变换后的立方体
            MatrixState.pushMatrix();
            //Ｘ轴移动４
            MatrixState.translate(4 , 0 , 0);
            //绕Ｚ轴旋转３０度
            MatrixState.rotate(30 , 0 , 0 , 1);
            //缩放
            MatrixState.scale(0.4f , 2f , 0.6f);
            mCube.drawSelf();
            //恢复现场
            MatrixState.popMatrix();
        }
    }
}
