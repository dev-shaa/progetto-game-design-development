/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class AABB {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected AABB(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(AABB obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(AABB obj) {
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
        liquidfunJNI.delete_AABB(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public boolean isValid() {
    return liquidfunJNI.AABB_isValid(swigCPtr, this);
  }

  public Vec2 getCenter() {
    return new Vec2(liquidfunJNI.AABB_getCenter(swigCPtr, this), true);
  }

  public Vec2 getExtents() {
    return new Vec2(liquidfunJNI.AABB_getExtents(swigCPtr, this), true);
  }

  public float getPerimeter() {
    return liquidfunJNI.AABB_getPerimeter(swigCPtr, this);
  }

  public void combine(AABB aabb) {
    liquidfunJNI.AABB_combine__SWIG_0(swigCPtr, this, AABB.getCPtr(aabb), aabb);
  }

  public void combine(AABB aabb1, AABB aabb2) {
    liquidfunJNI.AABB_combine__SWIG_1(swigCPtr, this, AABB.getCPtr(aabb1), aabb1, AABB.getCPtr(aabb2), aabb2);
  }

  public boolean contains(AABB aabb) {
    return liquidfunJNI.AABB_contains(swigCPtr, this, AABB.getCPtr(aabb), aabb);
  }

  public boolean rayCast(RayCastOutput output, RayCastInput input) {
    return liquidfunJNI.AABB_rayCast(swigCPtr, this, RayCastOutput.getCPtr(output), output, RayCastInput.getCPtr(input), input);
  }

  public void setLowerBound(Vec2 value) {
    liquidfunJNI.AABB_lowerBound_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getLowerBound() {
    return new Vec2(liquidfunJNI.AABB_lowerBound_get(swigCPtr, this), false);
  }

  public void setUpperBound(Vec2 value) {
    liquidfunJNI.AABB_upperBound_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getUpperBound() {
    return new Vec2(liquidfunJNI.AABB_upperBound_get(swigCPtr, this), false);
  }

  public AABB() {
    this(liquidfunJNI.new_AABB(), true);
  }

}
