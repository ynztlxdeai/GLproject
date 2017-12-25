package com.luoxiang.glproject;

/**
 * projectName: 	    GLproject
 * packageName:	        com.luoxiang.glproject
 * className:	        Constant
 * author:	            Luoxiang
 * time:	            2017/11/30	9:44
 * desc:	            TODO
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/11/30
 * upDateDesc:	        TODO
 */


public class Constant {
    //单位尺寸
    public static final float UNIT_SIZE=0.28f;
    //计算GLSurfaceView的宽高比
    public static float ratio;

    //绘制方式
    public static final int GL_POINTS = 0;
    public static final int GL_LINES = 1;
    public static final int GL_LINE_STRIP = 2;
    public static final int GL_LINE_LOOP = 3;

    public static int CURR_DRAW_MODE = GL_LINE_STRIP;//当前绘制方式
}
