/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class PulleyJoint extends Joint {
  private transient long swigCPtr;

  protected PulleyJoint(long cPtr, boolean cMemoryOwn) {
    super(liquidfunJNI.PulleyJoint_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(PulleyJoint obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(PulleyJoint obj) {
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
        liquidfunJNI.delete_PulleyJoint(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public Vec2 getAnchorA() {
    return new Vec2(liquidfunJNI.PulleyJoint_getAnchorA(swigCPtr, this), true);
  }

  public Vec2 getAnchorB() {
    return new Vec2(liquidfunJNI.PulleyJoint_getAnchorB(swigCPtr, this), true);
  }

  public Vec2 getReactionForce(float inv_dt) {
    return new Vec2(liquidfunJNI.PulleyJoint_getReactionForce(swigCPtr, this, inv_dt), true);
  }

  public float getReactionTorque(float inv_dt) {
    return liquidfunJNI.PulleyJoint_getReactionTorque(swigCPtr, this, inv_dt);
  }

  public Vec2 getGroundAnchorA() {
    return new Vec2(liquidfunJNI.PulleyJoint_getGroundAnchorA(swigCPtr, this), true);
  }

  public Vec2 getGroundAnchorB() {
    return new Vec2(liquidfunJNI.PulleyJoint_getGroundAnchorB(swigCPtr, this), true);
  }

  public float getLengthA() {
    return liquidfunJNI.PulleyJoint_getLengthA(swigCPtr, this);
  }

  public float getLengthB() {
    return liquidfunJNI.PulleyJoint_getLengthB(swigCPtr, this);
  }

  public float getRatio() {
    return liquidfunJNI.PulleyJoint_getRatio(swigCPtr, this);
  }

  public float getCurrentLengthA() {
    return liquidfunJNI.PulleyJoint_getCurrentLengthA(swigCPtr, this);
  }

  public float getCurrentLengthB() {
    return liquidfunJNI.PulleyJoint_getCurrentLengthB(swigCPtr, this);
  }

  public void dump() {
    liquidfunJNI.PulleyJoint_dump(swigCPtr, this);
  }

  public void shiftOrigin(Vec2 newOrigin) {
    liquidfunJNI.PulleyJoint_shiftOrigin(swigCPtr, this, Vec2.getCPtr(newOrigin), newOrigin);
  }

}
