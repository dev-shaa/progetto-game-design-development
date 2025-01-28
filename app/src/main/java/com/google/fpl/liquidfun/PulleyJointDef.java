/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class PulleyJointDef extends JointDef {
  private transient long swigCPtr;

  protected PulleyJointDef(long cPtr, boolean cMemoryOwn) {
    super(liquidfunJNI.PulleyJointDef_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(PulleyJointDef obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(PulleyJointDef obj) {
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
        liquidfunJNI.delete_PulleyJointDef(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public PulleyJointDef() {
    this(liquidfunJNI.new_PulleyJointDef(), true);
  }

  public void initialize(Body bodyA, Body bodyB, Vec2 groundAnchorA, Vec2 groundAnchorB, Vec2 anchorA, Vec2 anchorB, float ratio) {
    liquidfunJNI.PulleyJointDef_initialize(swigCPtr, this, Body.getCPtr(bodyA), bodyA, Body.getCPtr(bodyB), bodyB, Vec2.getCPtr(groundAnchorA), groundAnchorA, Vec2.getCPtr(groundAnchorB), groundAnchorB, Vec2.getCPtr(anchorA), anchorA, Vec2.getCPtr(anchorB), anchorB, ratio);
  }

  public void setGroundAnchorA(Vec2 value) {
    liquidfunJNI.PulleyJointDef_groundAnchorA_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getGroundAnchorA() {
    return new Vec2(liquidfunJNI.PulleyJointDef_groundAnchorA_get(swigCPtr, this), false);
  }

  public void setGroundAnchorB(Vec2 value) {
    liquidfunJNI.PulleyJointDef_groundAnchorB_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getGroundAnchorB() {
    return new Vec2(liquidfunJNI.PulleyJointDef_groundAnchorB_get(swigCPtr, this), false);
  }

  public void setLocalAnchorA(Vec2 value) {
    liquidfunJNI.PulleyJointDef_localAnchorA_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getLocalAnchorA() {
    return new Vec2(liquidfunJNI.PulleyJointDef_localAnchorA_get(swigCPtr, this), false);
  }

  public void setLocalAnchorB(Vec2 value) {
    liquidfunJNI.PulleyJointDef_localAnchorB_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getLocalAnchorB() {
    return new Vec2(liquidfunJNI.PulleyJointDef_localAnchorB_get(swigCPtr, this), false);
  }

  public void setLengthA(float value) {
    liquidfunJNI.PulleyJointDef_lengthA_set(swigCPtr, this, value);
  }

  public float getLengthA() {
    return liquidfunJNI.PulleyJointDef_lengthA_get(swigCPtr, this);
  }

  public void setLengthB(float value) {
    liquidfunJNI.PulleyJointDef_lengthB_set(swigCPtr, this, value);
  }

  public float getLengthB() {
    return liquidfunJNI.PulleyJointDef_lengthB_get(swigCPtr, this);
  }

  public void setRatio(float value) {
    liquidfunJNI.PulleyJointDef_ratio_set(swigCPtr, this, value);
  }

  public float getRatio() {
    return liquidfunJNI.PulleyJointDef_ratio_get(swigCPtr, this);
  }

}
