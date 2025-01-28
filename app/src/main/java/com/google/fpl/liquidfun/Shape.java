/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class Shape {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected Shape(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(Shape obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(Shape obj) {
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
        liquidfunJNI.delete_Shape(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public int getChildCount() {
    return liquidfunJNI.Shape_getChildCount(swigCPtr, this);
  }

  public boolean testPoint(Transform xf, Vec2 p) {
    return liquidfunJNI.Shape_testPoint(swigCPtr, this, Transform.getCPtr(xf), xf, Vec2.getCPtr(p), p);
  }

  public void computeMass(MassData massData, float density) {
    liquidfunJNI.Shape_computeMass(swigCPtr, this, MassData.getCPtr(massData), massData, density);
  }

  public void setType(Shape.Type value) {
    liquidfunJNI.Shape_type_set(swigCPtr, this, value.swigValue());
  }

  public Shape.Type getType() {
    return Shape.Type.swigToEnum(liquidfunJNI.Shape_type_get(swigCPtr, this));
  }

  public void setRadius(float value) {
    liquidfunJNI.Shape_radius_set(swigCPtr, this, value);
  }

  public float getRadius() {
    return liquidfunJNI.Shape_radius_get(swigCPtr, this);
  }

  public enum Type {
    CIRCLE(0),
    EDGE(1),
    POLYGON(2),
    CHAIN(3),
    e_typeCount(4);

    public final int swigValue() {
      return swigValue;
    }

    public static Type swigToEnum(int swigValue) {
      Type[] swigValues = Type.class.getEnumConstants();
      if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
        return swigValues[swigValue];
      for (Type swigEnum : swigValues)
        if (swigEnum.swigValue == swigValue)
          return swigEnum;
      throw new IllegalArgumentException("No enum " + Type.class + " with value " + swigValue);
    }

    @SuppressWarnings("unused")
    private Type() {
      this.swigValue = SwigNext.next++;
    }

    @SuppressWarnings("unused")
    private Type(int swigValue) {
      this.swigValue = swigValue;
      SwigNext.next = swigValue+1;
    }

    @SuppressWarnings("unused")
    private Type(Type swigEnum) {
      this.swigValue = swigEnum.swigValue;
      SwigNext.next = this.swigValue+1;
    }

    private final int swigValue;

    private static class SwigNext {
      private static int next = 0;
    }
  }

}
