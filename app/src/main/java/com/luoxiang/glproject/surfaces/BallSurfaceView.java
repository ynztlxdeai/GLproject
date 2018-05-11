package com.luoxiang.glproject.surfaces;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.luoxiang.glproject.Constant;
import com.luoxiang.glproject.domain.Ball;
import com.luoxiang.glproject.utils.MatrixState;

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


public class BallSurfaceView
        extends GLSurfaceView
{
    final static float TOUCH_SCALE_FACTOR = 180.0f / 320;
    //场景渲染器
    SceneRenderer mRenderer;
    Context       mContext;
    //上一次的触碰Y点
    private float mPreviousY;
    //上一次的触碰X点
    private float mPreviousX;

    Ball mBall;

    public BallSurfaceView(Context context) {
        this(context, null);
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
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;
                float dx = x - mPreviousX;
                if (mBall != null){
                    mBall.xAngle += dx * TOUCH_SCALE_FACTOR;
                    mBall.yAngle += dy * TOUCH_SCALE_FACTOR;
                }
                //break;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;

    }

    class SceneRenderer
            implements Renderer
    {


        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            if (null == mBall){
                mBall = new Ball(mContext);
            }
            //打开深度检测
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //打开卷绕
            GLES20.glEnable(GLES20.GL_CULL_FACE);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //重新设定窗口大小
            GLES20.glViewport(0, 0, width, height);
            //view的宽高比
            Constant.ratio = (float) width / height;

            //产生透视投影矩阵
            MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 20, 100);

            //相机九参数矩阵
            MatrixState.setCamera(0, 0, 30, 0f, 0f, 0f, 0.0f, 1.0f, 0.0f);

            MatrixState.setInitStack();


        }

        @Override
        public void onDrawFrame(GL10 gl) {
            //清楚深度缓冲和颜色缓冲
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            //保护现场
            MatrixState.pushMatrix();

            //绘制
            MatrixState.pushMatrix();
            mBall.drawSelf();
            MatrixState.popMatrix();

            //还原现场
            MatrixState.popMatrix();

        }
    }
}
