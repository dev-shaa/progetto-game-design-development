/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class FreeList {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected FreeList(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(FreeList obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(FreeList obj) {
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
        liquidfunJNI.delete_FreeList(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public FreeList() {
    this(liquidfunJNI.new_FreeList(), true);
  }

  public IntrusiveListNode allocate() {
    long cPtr = liquidfunJNI.FreeList_allocate(swigCPtr, this);
    return (cPtr == 0) ? null : new IntrusiveListNode(cPtr, false);
  }

  public void free(IntrusiveListNode node) {
    liquidfunJNI.FreeList_free(swigCPtr, this, IntrusiveListNode.getCPtr(node), node);
  }

  public void addToFreeList(IntrusiveListNode node) {
    liquidfunJNI.FreeList_addToFreeList(swigCPtr, this, IntrusiveListNode.getCPtr(node), node);
  }

  public void removeAll() {
    liquidfunJNI.FreeList_removeAll(swigCPtr, this);
  }

  public IntrusiveListNode getAllocatedList() {
    return new IntrusiveListNode(liquidfunJNI.FreeList_getAllocatedList(swigCPtr, this), false);
  }

  public IntrusiveListNode getFreeList() {
    return new IntrusiveListNode(liquidfunJNI.FreeList_getFreeList(swigCPtr, this), false);
  }

}
