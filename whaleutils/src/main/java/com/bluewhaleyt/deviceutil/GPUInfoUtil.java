package com.bluewhaleyt.deviceutil;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GPUInfoUtil implements GLSurfaceView.Renderer {

    static String glRenderer;
    static String glVendor;
    static String glVersion;
    static String glExtensions;

    public static final String GPU_VIEW = "GLSurfaceView";

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glRenderer = gl.glGetString(GL10.GL_RENDERER);
        glVendor = gl.glGetString(GL10.GL_VENDOR);
        glVersion = gl.glGetString(GL10.GL_VERSION);
        glExtensions = gl.glGetString(GL10.GL_EXTENSIONS);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }

    public static void set(GLSurfaceView glSurfaceView) {
        glSurfaceView.setEGLContextClientVersion(1);
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        glSurfaceView.setRenderer(new GPUInfoUtil());
    }

}
