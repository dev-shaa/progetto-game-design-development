/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class ParticleGroupDef {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected ParticleGroupDef(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ParticleGroupDef obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(ParticleGroupDef obj) {
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
        liquidfunJNI.delete_ParticleGroupDef(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public ParticleGroupDef() {
    this(liquidfunJNI.new_ParticleGroupDef(), true);
  }

  public void setFlags(long value) {
    liquidfunJNI.ParticleGroupDef_flags_set(swigCPtr, this, value);
  }

  public long getFlags() {
    return liquidfunJNI.ParticleGroupDef_flags_get(swigCPtr, this);
  }

  public void setGroupFlags(long value) {
    liquidfunJNI.ParticleGroupDef_groupFlags_set(swigCPtr, this, value);
  }

  public long getGroupFlags() {
    return liquidfunJNI.ParticleGroupDef_groupFlags_get(swigCPtr, this);
  }

  public void setPosition(Vec2 value) {
    liquidfunJNI.ParticleGroupDef_position_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getPosition() {
    return new Vec2(liquidfunJNI.ParticleGroupDef_position_get(swigCPtr, this), false);
  }

  public void setAngle(float value) {
    liquidfunJNI.ParticleGroupDef_angle_set(swigCPtr, this, value);
  }

  public float getAngle() {
    return liquidfunJNI.ParticleGroupDef_angle_get(swigCPtr, this);
  }

  public void setLinearVelocity(Vec2 value) {
    liquidfunJNI.ParticleGroupDef_linearVelocity_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getLinearVelocity() {
    return new Vec2(liquidfunJNI.ParticleGroupDef_linearVelocity_get(swigCPtr, this), false);
  }

  public void setAngularVelocity(float value) {
    liquidfunJNI.ParticleGroupDef_angularVelocity_set(swigCPtr, this, value);
  }

  public float getAngularVelocity() {
    return liquidfunJNI.ParticleGroupDef_angularVelocity_get(swigCPtr, this);
  }

  public void setColor(ParticleColor value) {
    liquidfunJNI.ParticleGroupDef_color_set(swigCPtr, this, ParticleColor.getCPtr(value), value);
  }

  public ParticleColor getColor() {
    return new ParticleColor(liquidfunJNI.ParticleGroupDef_color_get(swigCPtr, this), false);
  }

  public void setStrength(float value) {
    liquidfunJNI.ParticleGroupDef_strength_set(swigCPtr, this, value);
  }

  public float getStrength() {
    return liquidfunJNI.ParticleGroupDef_strength_get(swigCPtr, this);
  }

  public void setShape(Shape value) {
    liquidfunJNI.ParticleGroupDef_shape_set(swigCPtr, this, Shape.getCPtr(value), value);
  }

  public Shape getShape() {
    long cPtr = liquidfunJNI.ParticleGroupDef_shape_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Shape(cPtr, false);
  }

  public void setShapeCount(int value) {
    liquidfunJNI.ParticleGroupDef_shapeCount_set(swigCPtr, this, value);
  }

  public int getShapeCount() {
    return liquidfunJNI.ParticleGroupDef_shapeCount_get(swigCPtr, this);
  }

  public void setStride(float value) {
    liquidfunJNI.ParticleGroupDef_stride_set(swigCPtr, this, value);
  }

  public float getStride() {
    return liquidfunJNI.ParticleGroupDef_stride_get(swigCPtr, this);
  }

  public void setParticleCount(int value) {
    liquidfunJNI.ParticleGroupDef_particleCount_set(swigCPtr, this, value);
  }

  public int getParticleCount() {
    return liquidfunJNI.ParticleGroupDef_particleCount_get(swigCPtr, this);
  }

  public void setPositionData(Vec2 value) {
    liquidfunJNI.ParticleGroupDef_positionData_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getPositionData() {
    long cPtr = liquidfunJNI.ParticleGroupDef_positionData_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Vec2(cPtr, false);
  }

  public void setLifetime(float value) {
    liquidfunJNI.ParticleGroupDef_lifetime_set(swigCPtr, this, value);
  }

  public float getLifetime() {
    return liquidfunJNI.ParticleGroupDef_lifetime_get(swigCPtr, this);
  }

  public void setGroup(ParticleGroup value) {
    liquidfunJNI.ParticleGroupDef_group_set(swigCPtr, this, ParticleGroup.getCPtr(value), value);
  }

  public ParticleGroup getGroup() {
    long cPtr = liquidfunJNI.ParticleGroupDef_group_get(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticleGroup(cPtr, false);
  }

  public void setCircleShapes(CircleShape value) {
    liquidfunJNI.ParticleGroupDef_circleShapes_set(swigCPtr, this, CircleShape.getCPtr(value), value);
  }

  public CircleShape getCircleShapes() {
    long cPtr = liquidfunJNI.ParticleGroupDef_circleShapes_get(swigCPtr, this);
    return (cPtr == 0) ? null : new CircleShape(cPtr, false);
  }

  public void setOwnShapesArray(boolean value) {
    liquidfunJNI.ParticleGroupDef_ownShapesArray_set(swigCPtr, this, value);
  }

  public boolean getOwnShapesArray() {
    return liquidfunJNI.ParticleGroupDef_ownShapesArray_get(swigCPtr, this);
  }

  public void freeShapesMemory() {
    liquidfunJNI.ParticleGroupDef_freeShapesMemory(swigCPtr, this);
  }

  public void setCircleShapesFromVertexList(java.nio.ByteBuffer inBuf, int numShapes, float radius) {
    liquidfunJNI.ParticleGroupDef_setCircleShapesFromVertexList(swigCPtr, this, inBuf, numShapes, radius);
  }

  public void setPosition(float x, float y) {
    liquidfunJNI.ParticleGroupDef_setPosition(swigCPtr, this, x, y);
  }

  public void setColor(int r, int g, int b, int a) {
    liquidfunJNI.ParticleGroupDef_setColor(swigCPtr, this, r, g, b, a);
  }

}
