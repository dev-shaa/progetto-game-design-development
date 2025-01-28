/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class ParticleDef {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected ParticleDef(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ParticleDef obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(ParticleDef obj) {
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
        liquidfunJNI.delete_ParticleDef(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public ParticleDef() {
    this(liquidfunJNI.new_ParticleDef(), true);
  }

  public void setPosition(float x, float y) {
    liquidfunJNI.ParticleDef_setPosition(swigCPtr, this, x, y);
  }

  public void setColor(int r, int g, int b, int a) {
    liquidfunJNI.ParticleDef_setColor(swigCPtr, this, r, g, b, a);
  }

  public void setFlags(long value) {
    liquidfunJNI.ParticleDef_flags_set(swigCPtr, this, value);
  }

  public long getFlags() {
    return liquidfunJNI.ParticleDef_flags_get(swigCPtr, this);
  }

  public void setPosition(Vec2 value) {
    liquidfunJNI.ParticleDef_position_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getPosition() {
    return new Vec2(liquidfunJNI.ParticleDef_position_get(swigCPtr, this), false);
  }

  public void setVelocity(Vec2 value) {
    liquidfunJNI.ParticleDef_velocity_set(swigCPtr, this, Vec2.getCPtr(value), value);
  }

  public Vec2 getVelocity() {
    return new Vec2(liquidfunJNI.ParticleDef_velocity_get(swigCPtr, this), false);
  }

  public void setColor(ParticleColor value) {
    liquidfunJNI.ParticleDef_color_set(swigCPtr, this, ParticleColor.getCPtr(value), value);
  }

  public ParticleColor getColor() {
    return new ParticleColor(liquidfunJNI.ParticleDef_color_get(swigCPtr, this), false);
  }

  public void setLifetime(float value) {
    liquidfunJNI.ParticleDef_lifetime_set(swigCPtr, this, value);
  }

  public float getLifetime() {
    return liquidfunJNI.ParticleDef_lifetime_get(swigCPtr, this);
  }

  public void setGroup(ParticleGroup value) {
    liquidfunJNI.ParticleDef_group_set(swigCPtr, this, ParticleGroup.getCPtr(value), value);
  }

  public ParticleGroup getGroup() {
    long cPtr = liquidfunJNI.ParticleDef_group_get(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticleGroup(cPtr, false);
  }

}
