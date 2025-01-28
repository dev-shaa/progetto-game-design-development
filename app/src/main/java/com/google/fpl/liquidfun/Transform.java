/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class Transform {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Transform(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Transform obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Transform obj) {
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
        liquidfunJNI.delete_Transform(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Transform() {
    this(liquidfunJNI.new_Transform(), true);
  }

  public void setIdentity() {
    liquidfunJNI.Transform_setIdentity(swigCPtr, this);
  }

  public float getPositionX() {
    return liquidfunJNI.Transform_getPositionX(swigCPtr, this);
  }

  public float getPositionY() {
    return liquidfunJNI.Transform_getPositionY(swigCPtr, this);
  }

  public float getRotationSin() {
    return liquidfunJNI.Transform_getRotationSin(swigCPtr, this);
  }

  public float getRotationCos() {
    return liquidfunJNI.Transform_getRotationCos(swigCPtr, this);
  }

}
