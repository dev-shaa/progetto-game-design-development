/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class DistanceJoint {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected DistanceJoint(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(DistanceJoint obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(DistanceJoint obj) {
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
        liquidfunJNI.delete_DistanceJoint(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Vec2 getAnchorA() {
    return new Vec2(liquidfunJNI.DistanceJoint_getAnchorA(swigCPtr, this), true);
  }

  public Vec2 getAnchorB() {
    return new Vec2(liquidfunJNI.DistanceJoint_getAnchorB(swigCPtr, this), true);
  }

  public Vec2 getReactionForce(float inv_dt) {
    return new Vec2(liquidfunJNI.DistanceJoint_getReactionForce(swigCPtr, this, inv_dt), true);
  }

  public float getReactionTorque(float inv_dt) {
    return liquidfunJNI.DistanceJoint_getReactionTorque(swigCPtr, this, inv_dt);
  }

  public Vec2 getLocalAnchorA() {
    return new Vec2(liquidfunJNI.DistanceJoint_getLocalAnchorA(swigCPtr, this), false);
  }

  public Vec2 getLocalAnchorB() {
    return new Vec2(liquidfunJNI.DistanceJoint_getLocalAnchorB(swigCPtr, this), false);
  }

  public void setLength(float length) {
    liquidfunJNI.DistanceJoint_setLength(swigCPtr, this, length);
  }

  public float getLength() {
    return liquidfunJNI.DistanceJoint_getLength(swigCPtr, this);
  }

  public void setFrequency(float hz) {
    liquidfunJNI.DistanceJoint_setFrequency(swigCPtr, this, hz);
  }

  public float getFrequency() {
    return liquidfunJNI.DistanceJoint_getFrequency(swigCPtr, this);
  }

  public void setDampingRatio(float ratio) {
    liquidfunJNI.DistanceJoint_setDampingRatio(swigCPtr, this, ratio);
  }

  public float getDampingRatio() {
    return liquidfunJNI.DistanceJoint_getDampingRatio(swigCPtr, this);
  }

  public void dump() {
    liquidfunJNI.DistanceJoint_dump(swigCPtr, this);
  }

}
