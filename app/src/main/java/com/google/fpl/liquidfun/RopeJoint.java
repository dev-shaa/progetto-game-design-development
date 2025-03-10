/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class RopeJoint extends Joint {
  private transient long swigCPtr;

  protected RopeJoint(long cPtr, boolean cMemoryOwn) {
    super(liquidfunJNI.RopeJoint_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(RopeJoint obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(RopeJoint obj) {
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
        liquidfunJNI.delete_RopeJoint(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public Vec2 getAnchorA() {
    return new Vec2(liquidfunJNI.RopeJoint_getAnchorA(swigCPtr, this), true);
  }

  public Vec2 getAnchorB() {
    return new Vec2(liquidfunJNI.RopeJoint_getAnchorB(swigCPtr, this), true);
  }

  public Vec2 getReactionForce(float inv_dt) {
    return new Vec2(liquidfunJNI.RopeJoint_getReactionForce(swigCPtr, this, inv_dt), true);
  }

  public float getReactionTorque(float inv_dt) {
    return liquidfunJNI.RopeJoint_getReactionTorque(swigCPtr, this, inv_dt);
  }

  public Vec2 getLocalAnchorA() {
    return new Vec2(liquidfunJNI.RopeJoint_getLocalAnchorA(swigCPtr, this), false);
  }

  public Vec2 getLocalAnchorB() {
    return new Vec2(liquidfunJNI.RopeJoint_getLocalAnchorB(swigCPtr, this), false);
  }

  public void setMaxLength(float length) {
    liquidfunJNI.RopeJoint_setMaxLength(swigCPtr, this, length);
  }

  public float getMaxLength() {
    return liquidfunJNI.RopeJoint_getMaxLength(swigCPtr, this);
  }

  public int getLimitState() {
    return liquidfunJNI.RopeJoint_getLimitState(swigCPtr, this);
  }

  public void dump() {
    liquidfunJNI.RopeJoint_dump(swigCPtr, this);
  }

}
