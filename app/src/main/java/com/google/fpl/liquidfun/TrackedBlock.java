/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class TrackedBlock {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected TrackedBlock(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(TrackedBlock obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(TrackedBlock obj) {
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

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        throw new UnsupportedOperationException("C++ destructor does not have public access");
      }
      swigCPtr = 0;
    }
  }

  public java.lang.Object getMemory() {
    return liquidfunJNI.TrackedBlock_getMemory(swigCPtr, this);
  }

  public static java.lang.Object allocate(long size) {
    return liquidfunJNI.TrackedBlock_allocate(size);
  }

  public static TrackedBlock getFromMemory(java.lang.Object memory) {
    long cPtr = liquidfunJNI.TrackedBlock_getFromMemory(memory);
    return (cPtr == 0) ? null : new TrackedBlock(cPtr, false);
  }

  public static void free(java.lang.Object memory) {
    liquidfunJNI.TrackedBlock_free__SWIG_0(memory);
  }

  public static void free(TrackedBlock block) {
    liquidfunJNI.TrackedBlock_free__SWIG_1(TrackedBlock.getCPtr(block), block);
  }

}
