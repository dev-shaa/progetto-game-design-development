/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class TrackedBlockAllocator {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected TrackedBlockAllocator(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(TrackedBlockAllocator obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(TrackedBlockAllocator obj) {
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
        liquidfunJNI.delete_TrackedBlockAllocator(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public TrackedBlockAllocator() {
    this(liquidfunJNI.new_TrackedBlockAllocator(), true);
  }

  public java.lang.Object allocate(long size) {
    return liquidfunJNI.TrackedBlockAllocator_allocate(swigCPtr, this, size);
  }

  public void free(java.lang.Object memory) {
    liquidfunJNI.TrackedBlockAllocator_free(swigCPtr, this, memory);
  }

  public void freeAll() {
    liquidfunJNI.TrackedBlockAllocator_freeAll(swigCPtr, this);
  }

  public SWIGTYPE_p_b2TypedIntrusiveListNodeT_b2TrackedBlock_t getList() {
    return new SWIGTYPE_p_b2TypedIntrusiveListNodeT_b2TrackedBlock_t(liquidfunJNI.TrackedBlockAllocator_getList(swigCPtr, this), false);
  }

}
