/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class Fixture {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Fixture(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Fixture obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Fixture obj) {
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
        liquidfunJNI.delete_Fixture(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public Shape.Type getType() {
    return Shape.Type.swigToEnum(liquidfunJNI.Fixture_getType(swigCPtr, this));
  }

  public Shape getShape() {
    long cPtr = liquidfunJNI.Fixture_getShape__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new Shape(cPtr, false);
  }

  public void setSensor(boolean sensor) {
    liquidfunJNI.Fixture_setSensor(swigCPtr, this, sensor);
  }

  public boolean isSensor() {
    return liquidfunJNI.Fixture_isSensor(swigCPtr, this);
  }

  public void setFilterData(Filter filter) {
    liquidfunJNI.Fixture_setFilterData(swigCPtr, this, Filter.getCPtr(filter), filter);
  }

  public Filter getFilterData() {
    return new Filter(liquidfunJNI.Fixture_getFilterData(swigCPtr, this), false);
  }

  public void refilter() {
    liquidfunJNI.Fixture_refilter(swigCPtr, this);
  }

  public Body getBody() {
    long cPtr = liquidfunJNI.Fixture_getBody__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new Body(cPtr, false);
  }

  public Fixture getNext() {
    long cPtr = liquidfunJNI.Fixture_getNext__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new Fixture(cPtr, false);
  }

  public java.lang.Object getUserData() {
    return liquidfunJNI.Fixture_getUserData(swigCPtr, this);
  }

  public void setUserData(java.lang.Object data) {
    liquidfunJNI.Fixture_setUserData(swigCPtr, this, data);
  }

  public boolean testPoint(Vec2 p) {
    return liquidfunJNI.Fixture_testPoint(swigCPtr, this, Vec2.getCPtr(p), p);
  }

  public void computeDistance(Vec2 p, SWIGTYPE_p_float distance, Vec2 normal, int childIndex) {
    liquidfunJNI.Fixture_computeDistance(swigCPtr, this, Vec2.getCPtr(p), p, SWIGTYPE_p_float.getCPtr(distance), Vec2.getCPtr(normal), normal, childIndex);
  }

  public boolean rayCast(RayCastOutput output, RayCastInput input, int childIndex) {
    return liquidfunJNI.Fixture_rayCast(swigCPtr, this, RayCastOutput.getCPtr(output), output, RayCastInput.getCPtr(input), input, childIndex);
  }

  public void getMassData(MassData massData) {
    liquidfunJNI.Fixture_getMassData(swigCPtr, this, MassData.getCPtr(massData), massData);
  }

  public void setDensity(float density) {
    liquidfunJNI.Fixture_setDensity(swigCPtr, this, density);
  }

  public float getDensity() {
    return liquidfunJNI.Fixture_getDensity(swigCPtr, this);
  }

  public float getFriction() {
    return liquidfunJNI.Fixture_getFriction(swigCPtr, this);
  }

  public void setFriction(float friction) {
    liquidfunJNI.Fixture_setFriction(swigCPtr, this, friction);
  }

  public float getRestitution() {
    return liquidfunJNI.Fixture_getRestitution(swigCPtr, this);
  }

  public void setRestitution(float restitution) {
    liquidfunJNI.Fixture_setRestitution(swigCPtr, this, restitution);
  }

  public AABB getAABB(int childIndex) {
    return new AABB(liquidfunJNI.Fixture_getAABB(swigCPtr, this, childIndex), false);
  }

  public void dump(int bodyIndex) {
    liquidfunJNI.Fixture_dump(swigCPtr, this, bodyIndex);
  }

}
