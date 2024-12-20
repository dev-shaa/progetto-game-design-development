/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class DynamicTree {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected DynamicTree(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(DynamicTree obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(DynamicTree obj) {
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
        liquidfunJNI.delete_DynamicTree(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public DynamicTree() {
    this(liquidfunJNI.new_DynamicTree(), true);
  }

  public int createProxy(AABB aabb, java.lang.Object userData) {
    return liquidfunJNI.DynamicTree_createProxy(swigCPtr, this, AABB.getCPtr(aabb), aabb, userData);
  }

  public void destroyProxy(int proxyId) {
    liquidfunJNI.DynamicTree_destroyProxy(swigCPtr, this, proxyId);
  }

  public boolean moveProxy(int proxyId, AABB aabb1, Vec2 displacement) {
    return liquidfunJNI.DynamicTree_moveProxy(swigCPtr, this, proxyId, AABB.getCPtr(aabb1), aabb1, Vec2.getCPtr(displacement), displacement);
  }

  public java.lang.Object getUserData(int proxyId) {
    return liquidfunJNI.DynamicTree_getUserData(swigCPtr, this, proxyId);
  }

  public AABB getFatAABB(int proxyId) {
    return new AABB(liquidfunJNI.DynamicTree_getFatAABB(swigCPtr, this, proxyId), false);
  }

  public void validate() {
    liquidfunJNI.DynamicTree_validate(swigCPtr, this);
  }

  public int getHeight() {
    return liquidfunJNI.DynamicTree_getHeight(swigCPtr, this);
  }

  public int getMaxBalance() {
    return liquidfunJNI.DynamicTree_getMaxBalance(swigCPtr, this);
  }

  public float getAreaRatio() {
    return liquidfunJNI.DynamicTree_getAreaRatio(swigCPtr, this);
  }

  public void rebuildBottomUp() {
    liquidfunJNI.DynamicTree_rebuildBottomUp(swigCPtr, this);
  }

  public void shiftOrigin(Vec2 newOrigin) {
    liquidfunJNI.DynamicTree_shiftOrigin(swigCPtr, this, Vec2.getCPtr(newOrigin), newOrigin);
  }

}
