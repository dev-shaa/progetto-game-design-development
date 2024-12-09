/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class Mat33 {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Mat33(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Mat33 obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Mat33 obj) {
    long ptr = 0;
    if (obj != null) {
      if (!obj.swigCMemOwn)
        throw new RuntimeException("Cannot release ownership as memory is not owned");
      ptr = obj.swigCPtr;
      obj.swigCMemOwn = false;
      obj.delete();
    }
    return ptr;
  }

  @SuppressWarnings({"deprecation", "removal"})
  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        liquidfunJNI.delete_Mat33(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Mat33() {
    this(liquidfunJNI.new_Mat33__SWIG_0(), true);
  }

  public Mat33(Vec3 c1, Vec3 c2, Vec3 c3) {
    this(liquidfunJNI.new_Mat33__SWIG_1(Vec3.getCPtr(c1), c1, Vec3.getCPtr(c2), c2, Vec3.getCPtr(c3), c3), true);
  }

  public void setZero() {
    liquidfunJNI.Mat33_setZero(swigCPtr, this);
  }

  public Vec3 solve33(Vec3 b) {
    return new Vec3(liquidfunJNI.Mat33_solve33(swigCPtr, this, Vec3.getCPtr(b), b), true);
  }

  public Vec2 solve22(Vec2 b) {
    return new Vec2(liquidfunJNI.Mat33_solve22(swigCPtr, this, Vec2.getCPtr(b), b), true);
  }

  public void getInverse22(Mat33 M) {
    liquidfunJNI.Mat33_getInverse22(swigCPtr, this, Mat33.getCPtr(M), M);
  }

  public void getSymInverse33(Mat33 M) {
    liquidfunJNI.Mat33_getSymInverse33(swigCPtr, this, Mat33.getCPtr(M), M);
  }

  public void setEx(Vec3 value) {
    liquidfunJNI.Mat33_ex_set(swigCPtr, this, Vec3.getCPtr(value), value);
  }

  public Vec3 getEx() {
    return new Vec3(liquidfunJNI.Mat33_ex_get(swigCPtr, this), false);
  }

  public void setEy(Vec3 value) {
    liquidfunJNI.Mat33_ey_set(swigCPtr, this, Vec3.getCPtr(value), value);
  }

  public Vec3 getEy() {
    return new Vec3(liquidfunJNI.Mat33_ey_get(swigCPtr, this), false);
  }

  public void setEz(Vec3 value) {
    liquidfunJNI.Mat33_ez_set(swigCPtr, this, Vec3.getCPtr(value), value);
  }

  public Vec3 getEz() {
    return new Vec3(liquidfunJNI.Mat33_ez_get(swigCPtr, this), false);
  }

}
