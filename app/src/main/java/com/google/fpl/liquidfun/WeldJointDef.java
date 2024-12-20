/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class WeldJointDef extends JointDef {
  private transient long swigCPtr;

  protected WeldJointDef(long cPtr, boolean cMemoryOwn) {
    super(liquidfunJNI.WeldJointDef_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(WeldJointDef obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(WeldJointDef obj) {
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
        liquidfunJNI.delete_WeldJointDef(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public WeldJointDef() {
    this(liquidfunJNI.new_WeldJointDef(), true);
  }

  public void initialize(Body bodyA, Body bodyB, Vec2 anchor) {
    liquidfunJNI.WeldJointDef_initialize(swigCPtr, this, Body.getCPtr(bodyA), bodyA, Body.getCPtr(bodyB), bodyB, Vec2.getCPtr(anchor), anchor);
  }

  public void setLocalAnchorA(Vec2 value) {
    liquidfunJNI.WeldJointDef_localAnchorA_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getLocalAnchorA() {
    return new Vec2(liquidfunJNI.WeldJointDef_localAnchorA_get(swigCPtr, this), false);
  }

  public void setLocalAnchorB(Vec2 value) {
    liquidfunJNI.WeldJointDef_localAnchorB_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getLocalAnchorB() {
    return new Vec2(liquidfunJNI.WeldJointDef_localAnchorB_get(swigCPtr, this), false);
  }

  public void setReferenceAngle(float value) {
    liquidfunJNI.WeldJointDef_referenceAngle_set(swigCPtr, this, value);
  }

  public float getReferenceAngle() {
    return liquidfunJNI.WeldJointDef_referenceAngle_get(swigCPtr, this);
  }

  public void setFrequencyHz(float value) {
    liquidfunJNI.WeldJointDef_frequencyHz_set(swigCPtr, this, value);
  }

  public float getFrequencyHz() {
    return liquidfunJNI.WeldJointDef_frequencyHz_get(swigCPtr, this);
  }

  public void setDampingRatio(float value) {
    liquidfunJNI.WeldJointDef_dampingRatio_set(swigCPtr, this, value);
  }

  public float getDampingRatio() {
    return liquidfunJNI.WeldJointDef_dampingRatio_get(swigCPtr, this);
  }

}
