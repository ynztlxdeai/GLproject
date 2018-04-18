package com.luoxiang.glproject.surfaces;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.luoxiang.glproject.domain.Ball;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.surfaces
 * className:	        BallSurfaceView
 * author:	            Luoxiang
 * time:	            2018/4/18	上午11:43
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    luoxiang
 * upDate:	            2018/4/18
 * upDateDesc:	        TODO
 */


public class BallSurfaceView extends GLSurfaceView
{
    SceneRenderer mRenderer;
    Context mContext;

    public BallSurfaceView(Context context) {
        this(context , null);
    }

    public BallSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setEGLContextClientVersion(2);
        mRenderer = new SceneRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    class SceneRenderer implements Renderer
    {
        Ball mBall;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.0f , 0.0f , 0.0f , 0.0f);
            mBall = new Ball(mContext);
            //打开深度检测
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //打开卷绕
            GLES20.glEnable(GLES20.GL_CULL_FACE);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //重新设定窗口大小
            GLES20.glViewport(0 , 0 , width , height);

        }

        @Override
        public void onDrawFrame(GL10 gl) {
            mBall.drawSelf();
        }
    }
}
