package com.luoxiang.glproject.surfaces;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.luoxiang.glproject.Constant;
import com.luoxiang.glproject.domain.Belt;
import com.luoxiang.glproject.domain.Circle;
import com.luoxiang.glproject.domain.Circle2;
import com.luoxiang.glproject.utils.MatrixState;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject.surfaces
 * className:	        BeltCircleSurfaceView
 * author:	            Luoxiang
 * time:	            2017/12/6	11:27
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/12/6
 * upDateDesc:	        TODO
 */
public class BeltCircleSurfaceView extends GLSurfaceView {
    private SceneRenderer mRenderer;//场景渲染器
    private Context mContext ;
    public BeltCircleSurfaceView(Context context) {
        this(context , null);
    }

    public BeltCircleSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        this.setEGLContextClientVersion(2); //设置使用OPENGL ES2.0
        mRenderer = new SceneRenderer();	//创建场景渲染器
        setRenderer(mRenderer);				//设置渲染器
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//设置渲染模式为主动渲染
    }


    private class SceneRenderer implements GLSurfaceView.Renderer
    {
        Belt belt;//条状物
        Circle circle;//圆
        Circle2 circle2;

        public void onDrawFrame(GL10 gl)
        {
            //清除深度缓冲与颜色缓冲
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            //保护现场
           // MatrixState.pushMatrix();


            //绘制条状物
            MatrixState.pushMatrix();
            MatrixState.translate(-1.3f, 0, 0);//沿x方向平移


//            belt.drawSelf();
            //
            circle2.drawSelf();

            MatrixState.popMatrix();

          /*  //绘制圆
            MatrixState.pushMatrix();
            MatrixState.translate(1.3f, 0, 0);//沿x方向平移
            circle.drawSelf();
            MatrixState.popMatrix();


            //恢复现场
            MatrixState.popMatrix();*/
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //设置视窗大小及位置
            GLES20.glViewport(0, 0, width, height);
            //计算GLSurfaceView的宽高比
            Constant.ratio = (float) width / height;
            // 调用此方法计算产生透视投影矩阵
            MatrixState.setProjectFrustum(-Constant.ratio, Constant.ratio, -1, 1, 8, 100);
            // 调用此方法产生摄像机9参数位置矩阵
            MatrixState.setCamera(0, 8f, 30, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

            //初始化变换矩阵
            MatrixState.setInitStack();
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //设置屏幕背景色RGBA
            GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            //创建圆对象
            circle=new Circle(mContext);
            //创建条状物对象
            belt=new Belt(mContext);
            circle2 = new Circle2(mContext);
            //打开深度检测
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            //打开背面剪裁
            GLES20.glEnable(GLES20.GL_CULL_FACE);
        }
    }
}
