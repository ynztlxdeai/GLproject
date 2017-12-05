package com.luoxiang.glproject.surfaces;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

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
 * desc:	            GLES平移用的surfaceview
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/11/30
 * upDateDesc:	        TODO
 */


public class TranslationSurfaceView extends GLSurfaceView {

    //场景渲染器
    private SceneRenderer mSceneRenderer;


    public TranslationSurfaceView(Context context) {
        this(context , null);
    }

    public TranslationSurfaceView(Context context, AttributeSet attrs) {
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
            implements GLSurfaceView.Renderer
    {
        //立方体的引用对象
        Cube mCube;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清楚深度和颜色缓冲
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            //绘制原来的立方体

        }
    }
}
