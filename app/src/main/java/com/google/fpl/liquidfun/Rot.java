/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class Rot {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Rot(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Rot obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Rot obj) {
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
        liquidfunJNI.delete_Rot(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Rot() {
    this(liquidfunJNI.new_Rot__SWIG_0(), true);
  }

  public Rot(float angle) {
    this(liquidfunJNI.new_Rot__SWIG_1(angle), true);
  }

  public void set(float angle) {
    liquidfunJNI.Rot_set(swigCPtr, this, angle);
  }

  public void setIdentity() {
    liquidfunJNI.Rot_setIdentity(swigCPtr, this);
  }

  public float getAngle() {
    return liquidfunJNI.Rot_getAngle(swigCPtr, this);
  }

  public Vec2 getXAxis() {
    return new Vec2(liquidfunJNI.Rot_getXAxis(swigCPtr, this), true);
  }

  public Vec2 getYAxis() {
    return new Vec2(liquidfunJNI.Rot_getYAxis(swigCPtr, this), true);
  }

  public void setS(float value) {
    liquidfunJNI.Rot_s_set(swigCPtr, this, value);
  }

  public float getS() {
    return liquidfunJNI.Rot_s_get(swigCPtr, this);
  }

  public void setC(float value) {
    liquidfunJNI.Rot_c_set(swigCPtr, this, value);
  }

  public float getC() {
    return liquidfunJNI.Rot_c_get(swigCPtr, this);
  }

}