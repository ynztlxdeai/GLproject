package com.luoxiang.glproject.utils;

import android.opengl.Matrix;

/**
 * projectName: 	    GLhexagram
 * packageName:	        com.luoxiang.glhexagram
 * className:	        MatrixState
 * author:	            Luoxiang
 * time:	            2017/11/29	10:48
 * desc:	            管理系统变换矩阵的类
 *
 * svnVersion:	        $Rev
 * upDateAuthor:	    Vincent
 * upDate:	            2017/11/29
 * upDateDesc:	        TODO
 */


public class MatrixState {

    //4X4的投影矩阵
    private static float[] mProjMatrix = new float[16];
    //摄像机位置朝向的参数矩阵
    private static float[] mVMatrix    = new float[16];
    //总变换矩阵
    private static float[] mMVPMatrix = new float[16];
    //当前变换矩阵
    private static float[] currMatrix;
    //用于保存变换矩阵的堆栈
    static float[][] mStack = new float[10][16];
    //栈顶索引
    static int stackTop = -1;

    //产生无任何变化的初始化矩阵
    public static void setInitStack(){
        currMatrix = new float[16];
        Matrix.setRotateM(currMatrix , 0 , 0 ,1 ,0 ,0);
    }

    /**
     * 将当前的变化矩阵入栈
     */
    public static void pushMatrix(){
        stackTop++;
        for (int i = 0; i < 16; i++) {
            mStack[stackTop][i] = currMatrix[i];
        }
    }

    /**
     * 取出当前栈顶的矩阵
     */
    public static void popMatrix(){
        for (int i = 0; i < 16; i++) {
           currMatrix[i] = mStack[stackTop][i] ;
        }
        stackTop--;
    }

    /**
     * 平移
     * @param x
     * @param y
     * @param z
     */
    public static void translate(float x , float y , float z){
        Matrix.translateM(currMatrix , 0 , x , y , z);
    }


    /**
     * 设置正交投影
     * @param left near面的left
     * @param right near面的right
     * @param bottom near面的bottom
     * @param top   near面的top
     * @param near near面与视点的距离
     * @param far   far面与视点的距离
     */
    public static void setProjectionOrtho(float left , float right , float bottom , float top , float near , float far){
        Matrix.orthoM(mProjMatrix , 0 ,//起始偏移量
                      left , right , bottom , top , near , far);
    }

    /**
     * 设置透视投影
     * @param left near面的left
     * @param right near面的right
     * @param bottom near面的bottom
     * @param top   near面的top
     * @param near near面与视点的距离
     * @param far   far面与视点的距离
     */
    public static void setProjectFrustum(float left , float right , float bottom , float top , float near , float far){
        Matrix.frustumM(mProjMatrix, 0 ,//起始偏移量
                        left , right , bottom , top , near , far );
    }

    /**
     *设置摄像机的方法
     * @param cx 摄像机的X坐标
     * @param cy 摄像机的Y坐标
     * @param cz 摄像机的Z坐标
     * @param tx 观察目标点的X坐标
     * @param ty 观察目标点的Y坐标
     * @param tz 观察目标点的Z坐标
     * @param upx up向量在X轴上的分量
     * @param upy up向量在Y轴上的分量
     * @param upz up向量在Z轴上的分量
     */
    public static void setCamera(float cx , float cy , float cz , float tx , float ty , float tz , float upx , float upy , float upz){
        Matrix.setLookAtM(mVMatrix , 0 , cx , cy , cz , tx , ty , tz , upx , upy , upz );
    }

    /**
     * 产生最终变换矩阵的方法
     * @param spec
     * @return
     */
    public static float[] getFinalMatrix(float[] spec){
        //初始化总变换矩阵
        mMVPMatrix = new float[16];
        /**
         * Multiplies two 4x4 matrices together and stores the result in a third 4x4
         * matrix. In matrix notation: result = lhs x rhs. Due to the way
         * matrix multiplication works, the result matrix will have the same
         * effect as first multiplying by the rhs matrix, then multiplying by
         * the lhs matrix. This is the opposite of what you might expect.
         * <p>
         * The same float array may be passed for result, lhs, and/or rhs. However,
         * the result element values are undefined if the result elements overlap
         * either the lhs or rhs elements.
         *
         * @param result The float array that holds the result.
         * @param resultOffset The offset into the result array where the result is
         *        stored.
         * @param lhs The float array that holds the left-hand-side matrix.
         * @param lhsOffset The offset into the lhs array where the lhs is stored
         * @param rhs The float array that holds the right-hand-side matrix.
         * @param rhsOffset The offset into the rhs array where the rhs is stored.
         *
         * @throws IllegalArgumentException if result, lhs, or rhs are null, or if
         * resultOffset + 16 > result.length or lhsOffset + 16 > lhs.length or
         * rhsOffset + 16 > rhs.length.
         */
        Matrix.multiplyMM(mMVPMatrix , 0 , mVMatrix , 0 , spec , 0);
        Matrix.multiplyMM(mMVPMatrix , 0 , mProjMatrix , 0 , mMVPMatrix , 0);
        return mMVPMatrix;
    }

    /**
     * 计算产生总变换矩阵
     * @return 总变换矩阵
     */
    public static float[] getFinalMatrix(){
        //摄像机矩阵乘以变换矩阵
        Matrix.multiplyMM(mMVPMatrix , 0 , mVMatrix , 0 , currMatrix , 0);
        //投影矩阵乘以上一步的结果矩阵
        Matrix.multiplyMM(mMVPMatrix , 0 , mProjMatrix , 0 , mMVPMatrix , 0);
        return mMVPMatrix;
    }

    /**
     * 旋转
     * @param angle　角度
     * @param x　分量
     * @param y
     * @param z
     */
    public static void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(currMatrix , 0 , angle , x , y , z);
    }

    /**
     * 缩放
     * @param x
     * @param y
     * @param z
     */
    public static void scale(float x, float y, float z) {
        Matrix.scaleM(currMatrix  , 0 , x , y, z);
    }
}
