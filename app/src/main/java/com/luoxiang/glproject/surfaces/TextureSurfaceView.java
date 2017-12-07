package com.luoxiang.glproject.surfaces;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.luoxiang.glproject.domain.Triangle;

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

    class SceneRenderer implements GLSurfaceView.Renderer{
        Triangle mTriangle;
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0,0,0,0);
            mTriangle = new Triangle(getContext());
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0 , 0 , width , height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            mTriangle.drawSelf(mTextureId);
        }
    }
}
