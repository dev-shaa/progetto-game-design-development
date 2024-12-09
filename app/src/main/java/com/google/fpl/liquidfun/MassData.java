/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class MassData {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected MassData(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(MassData obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(MassData obj) {
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
        liquidfunJNI.delete_MassData(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setMass(float value) {
    liquidfunJNI.MassData_mass_set(swigCPtr, this, value);
  }

  public float getMass() {
    return liquidfunJNI.MassData_mass_get(swigCPtr, this);
  }

  public void setCenter(Vec2 value) {
    liquidfunJNI.MassData_center_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getCenter() {
    return new Vec2(liquidfunJNI.MassData_center_get(swigCPtr, this), false);
  }

  public void setI(float value) {
    liquidfunJNI.MassData_I_set(swigCPtr, this, value);
  }

  public float getI() {
    return liquidfunJNI.MassData_I_get(swigCPtr, this);
  }

  public MassData() {
    this(liquidfunJNI.new_MassData(), true);
  }

}
