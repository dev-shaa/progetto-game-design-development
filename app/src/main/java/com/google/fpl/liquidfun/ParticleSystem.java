/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class ParticleSystem {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected ParticleSystem(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(ParticleSystem obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(ParticleSystem obj) {
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

  public int createParticle(ParticleDef def) {
    return liquidfunJNI.ParticleSystem_createParticle(swigCPtr, this, ParticleDef.getCPtr(def), def);
  }

  public ParticleHandle getParticleHandleFromIndex(int index) {
    long cPtr = liquidfunJNI.ParticleSystem_getParticleHandleFromIndex(swigCPtr, this, index);
    return (cPtr == 0) ? null : new ParticleHandle(cPtr, false);
  }

  public void destroyParticle(int index) {
    liquidfunJNI.ParticleSystem_destroyParticle__SWIG_0(swigCPtr, this, index);
  }

  public void destroyParticle(int index, boolean callDestructionListener) {
    liquidfunJNI.ParticleSystem_destroyParticle__SWIG_1(swigCPtr, this, index, callDestructionListener);
  }

  public void destroyOldestParticle(int index, boolean callDestructionListener) {
    liquidfunJNI.ParticleSystem_destroyOldestParticle(swigCPtr, this, index, callDestructionListener);
  }

  public int destroyParticlesInShape(Shape shape, Transform xf) {
    return liquidfunJNI.ParticleSystem_destroyParticlesInShape__SWIG_0(swigCPtr, this, Shape.getCPtr(shape), shape, Transform.getCPtr(xf), xf);
  }

  public int destroyParticlesInShape(Shape shape, Transform xf, boolean callDestructionListener) {
    return liquidfunJNI.ParticleSystem_destroyParticlesInShape__SWIG_1(swigCPtr, this, Shape.getCPtr(shape), shape, Transform.getCPtr(xf), xf, callDestructionListener);
  }

  public ParticleGroup createParticleGroup(ParticleGroupDef def) {
    long cPtr = liquidfunJNI.ParticleSystem_createParticleGroup(swigCPtr, this, ParticleGroupDef.getCPtr(def), def);
    return (cPtr == 0) ? null : new ParticleGroup(cPtr, false);
  }

  public void joinParticleGroups(ParticleGroup groupA, ParticleGroup groupB) {
    liquidfunJNI.ParticleSystem_joinParticleGroups(swigCPtr, this, ParticleGroup.getCPtr(groupA), groupA, ParticleGroup.getCPtr(groupB), groupB);
  }

  public void splitParticleGroup(ParticleGroup group) {
    liquidfunJNI.ParticleSystem_splitParticleGroup(swigCPtr, this, ParticleGroup.getCPtr(group), group);
  }

  public ParticleGroup getParticleGroupList() {
    long cPtr = liquidfunJNI.ParticleSystem_getParticleGroupList__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticleGroup(cPtr, false);
  }

  public int getParticleGroupCount() {
    return liquidfunJNI.ParticleSystem_getParticleGroupCount(swigCPtr, this);
  }

  public int getParticleCount() {
    return liquidfunJNI.ParticleSystem_getParticleCount(swigCPtr, this);
  }

  public int getMaxParticleCount() {
    return liquidfunJNI.ParticleSystem_getMaxParticleCount(swigCPtr, this);
  }

  public void setMaxParticleCount(int count) {
    liquidfunJNI.ParticleSystem_setMaxParticleCount(swigCPtr, this, count);
  }

  public long getAllParticleFlags() {
    return liquidfunJNI.ParticleSystem_getAllParticleFlags(swigCPtr, this);
  }

  public long getAllGroupFlags() {
    return liquidfunJNI.ParticleSystem_getAllGroupFlags(swigCPtr, this);
  }

  public void setPaused(boolean paused) {
    liquidfunJNI.ParticleSystem_setPaused(swigCPtr, this, paused);
  }

  public boolean getPaused() {
    return liquidfunJNI.ParticleSystem_getPaused(swigCPtr, this);
  }

  public void setDensity(float density) {
    liquidfunJNI.ParticleSystem_setDensity(swigCPtr, this, density);
  }

  public float getDensity() {
    return liquidfunJNI.ParticleSystem_getDensity(swigCPtr, this);
  }

  public void setGravityScale(float gravityScale) {
    liquidfunJNI.ParticleSystem_setGravityScale(swigCPtr, this, gravityScale);
  }

  public float getGravityScale() {
    return liquidfunJNI.ParticleSystem_getGravityScale(swigCPtr, this);
  }

  public void setDamping(float damping) {
    liquidfunJNI.ParticleSystem_setDamping(swigCPtr, this, damping);
  }

  public float getDamping() {
    return liquidfunJNI.ParticleSystem_getDamping(swigCPtr, this);
  }

  public void setStaticPressureIterations(int iterations) {
    liquidfunJNI.ParticleSystem_setStaticPressureIterations(swigCPtr, this, iterations);
  }

  public int getStaticPressureIterations() {
    return liquidfunJNI.ParticleSystem_getStaticPressureIterations(swigCPtr, this);
  }

  public void setRadius(float radius) {
    liquidfunJNI.ParticleSystem_setRadius(swigCPtr, this, radius);
  }

  public float getRadius() {
    return liquidfunJNI.ParticleSystem_getRadius(swigCPtr, this);
  }

  public Vec2 getPositionBuffer() {
    long cPtr = liquidfunJNI.ParticleSystem_getPositionBuffer__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new Vec2(cPtr, false);
  }

  public Vec2 getVelocityBuffer() {
    long cPtr = liquidfunJNI.ParticleSystem_getVelocityBuffer__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new Vec2(cPtr, false);
  }

  public ParticleColor getColorBuffer() {
    long cPtr = liquidfunJNI.ParticleSystem_getColorBuffer__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticleColor(cPtr, false);
  }

  public SWIGTYPE_p_p_b2ParticleGroup getGroupBuffer() {
    long cPtr = liquidfunJNI.ParticleSystem_getGroupBuffer__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_b2ParticleGroup(cPtr, false);
  }

  public SWIGTYPE_p_float getWeightBuffer() {
    long cPtr = liquidfunJNI.ParticleSystem_getWeightBuffer__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_float(cPtr, false);
  }

  public SWIGTYPE_p_p_void getUserDataBuffer() {
    long cPtr = liquidfunJNI.ParticleSystem_getUserDataBuffer__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_p_void(cPtr, false);
  }

  public SWIGTYPE_p_unsigned_int getFlagsBuffer() {
    long cPtr = liquidfunJNI.ParticleSystem_getFlagsBuffer(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_unsigned_int(cPtr, false);
  }

  public void setParticleFlags(int index, long flags) {
    liquidfunJNI.ParticleSystem_setParticleFlags(swigCPtr, this, index, flags);
  }

  public long getParticleFlags(int index) {
    return liquidfunJNI.ParticleSystem_getParticleFlags(swigCPtr, this, index);
  }

  public void setFlagsBuffer(SWIGTYPE_p_unsigned_int buffer, int capacity) {
    liquidfunJNI.ParticleSystem_setFlagsBuffer(swigCPtr, this, SWIGTYPE_p_unsigned_int.getCPtr(buffer), capacity);
  }

  public void setPositionBuffer(Vec2 buffer, int capacity) {
    liquidfunJNI.ParticleSystem_setPositionBuffer(swigCPtr, this, Vec2.getCPtr(buffer), buffer, capacity);
  }

  public void setVelocityBuffer(Vec2 buffer, int capacity) {
    liquidfunJNI.ParticleSystem_setVelocityBuffer(swigCPtr, this, Vec2.getCPtr(buffer), buffer, capacity);
  }

  public void setColorBuffer(ParticleColor buffer, int capacity) {
    liquidfunJNI.ParticleSystem_setColorBuffer(swigCPtr, this, ParticleColor.getCPtr(buffer), buffer, capacity);
  }

  public void setUserDataBuffer(SWIGTYPE_p_p_void buffer, int capacity) {
    liquidfunJNI.ParticleSystem_setUserDataBuffer(swigCPtr, this, SWIGTYPE_p_p_void.getCPtr(buffer), capacity);
  }

  public ParticleContact getContacts() {
    long cPtr = liquidfunJNI.ParticleSystem_getContacts(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticleContact(cPtr, false);
  }

  public int getContactCount() {
    return liquidfunJNI.ParticleSystem_getContactCount(swigCPtr, this);
  }

  public ParticleBodyContact getBodyContacts() {
    long cPtr = liquidfunJNI.ParticleSystem_getBodyContacts(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticleBodyContact(cPtr, false);
  }

  public int getBodyContactCount() {
    return liquidfunJNI.ParticleSystem_getBodyContactCount(swigCPtr, this);
  }

  public ParticlePair getPairs() {
    long cPtr = liquidfunJNI.ParticleSystem_getPairs(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticlePair(cPtr, false);
  }

  public int getPairCount() {
    return liquidfunJNI.ParticleSystem_getPairCount(swigCPtr, this);
  }

  public ParticleTriad getTriads() {
    long cPtr = liquidfunJNI.ParticleSystem_getTriads(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticleTriad(cPtr, false);
  }

  public int getTriadCount() {
    return liquidfunJNI.ParticleSystem_getTriadCount(swigCPtr, this);
  }

  public void setStuckThreshold(int iterations) {
    liquidfunJNI.ParticleSystem_setStuckThreshold(swigCPtr, this, iterations);
  }

  public SWIGTYPE_p_int getStuckCandidates() {
    long cPtr = liquidfunJNI.ParticleSystem_getStuckCandidates(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_int(cPtr, false);
  }

  public int getStuckCandidateCount() {
    return liquidfunJNI.ParticleSystem_getStuckCandidateCount(swigCPtr, this);
  }

  public float computeCollisionEnergy() {
    return liquidfunJNI.ParticleSystem_computeCollisionEnergy(swigCPtr, this);
  }

  public void setStrictContactCheck(boolean enabled) {
    liquidfunJNI.ParticleSystem_setStrictContactCheck(swigCPtr, this, enabled);
  }

  public boolean getStrictContactCheck() {
    return liquidfunJNI.ParticleSystem_getStrictContactCheck(swigCPtr, this);
  }

  public void setParticleLifetime(int index, float lifetime) {
    liquidfunJNI.ParticleSystem_setParticleLifetime(swigCPtr, this, index, lifetime);
  }

  public float getParticleLifetime(int index) {
    return liquidfunJNI.ParticleSystem_getParticleLifetime(swigCPtr, this, index);
  }

  public void setDestructionByAge(boolean enable) {
    liquidfunJNI.ParticleSystem_setDestructionByAge(swigCPtr, this, enable);
  }

  public boolean getDestructionByAge() {
    return liquidfunJNI.ParticleSystem_getDestructionByAge(swigCPtr, this);
  }

  public SWIGTYPE_p_int getExpirationTimeBuffer() {
    long cPtr = liquidfunJNI.ParticleSystem_getExpirationTimeBuffer(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_int(cPtr, false);
  }

  public float expirationTimeToLifetime(int expirationTime) {
    return liquidfunJNI.ParticleSystem_expirationTimeToLifetime(swigCPtr, this, expirationTime);
  }

  public SWIGTYPE_p_int getIndexByExpirationTimeBuffer() {
    long cPtr = liquidfunJNI.ParticleSystem_getIndexByExpirationTimeBuffer(swigCPtr, this);
    return (cPtr == 0) ? null : new SWIGTYPE_p_int(cPtr, false);
  }

  public void particleApplyLinearImpulse(int index, Vec2 impulse) {
    liquidfunJNI.ParticleSystem_particleApplyLinearImpulse(swigCPtr, this, index, Vec2.getCPtr(impulse), impulse);
  }

  public void applyLinearImpulse(int firstIndex, int lastIndex, Vec2 impulse) {
    liquidfunJNI.ParticleSystem_applyLinearImpulse(swigCPtr, this, firstIndex, lastIndex, Vec2.getCPtr(impulse), impulse);
  }

  public void particleApplyForce(int index, Vec2 force) {
    liquidfunJNI.ParticleSystem_particleApplyForce(swigCPtr, this, index, Vec2.getCPtr(force), force);
  }

  public void applyForce(int firstIndex, int lastIndex, Vec2 force) {
    liquidfunJNI.ParticleSystem_applyForce(swigCPtr, this, firstIndex, lastIndex, Vec2.getCPtr(force), force);
  }

  public ParticleSystem getNext() {
    long cPtr = liquidfunJNI.ParticleSystem_getNext__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new ParticleSystem(cPtr, false);
  }

  public void queryAABB(QueryCallback callback, AABB aabb) {
    liquidfunJNI.ParticleSystem_queryAABB(swigCPtr, this, QueryCallback.getCPtr(callback), callback, AABB.getCPtr(aabb), aabb);
  }

  public void queryShapeAABB(QueryCallback callback, Shape shape, Transform xf) {
    liquidfunJNI.ParticleSystem_queryShapeAABB(swigCPtr, this, QueryCallback.getCPtr(callback), callback, Shape.getCPtr(shape), shape, Transform.getCPtr(xf), xf);
  }

  public void rayCast(RayCastCallback callback, Vec2 point1, Vec2 point2) {
    liquidfunJNI.ParticleSystem_rayCast(swigCPtr, this, RayCastCallback.getCPtr(callback), callback, Vec2.getCPtr(point1), point1, Vec2.getCPtr(point2), point2);
  }

  public void computeAABB(AABB aabb) {
    liquidfunJNI.ParticleSystem_computeAABB(swigCPtr, this, AABB.getCPtr(aabb), aabb);
  }

  public void setParticleVelocity(int index, float vx, float vy) {
    liquidfunJNI.ParticleSystem_setParticleVelocity(swigCPtr, this, index, vx, vy);
  }

  public float getParticlePositionX(int index) {
    return liquidfunJNI.ParticleSystem_getParticlePositionX(swigCPtr, this, index);
  }

  public float getParticlePositionY(int index) {
    return liquidfunJNI.ParticleSystem_getParticlePositionY(swigCPtr, this, index);
  }

  public int copyPositionBuffer(int startIndex, int numParticles, java.lang.Object outBuf, int size) {
    return liquidfunJNI.ParticleSystem_copyPositionBuffer(swigCPtr, this, startIndex, numParticles, outBuf, size);
  }

  public int copyColorBuffer(int startIndex, int numParticles, java.lang.Object outBuf, int size) {
    return liquidfunJNI.ParticleSystem_copyColorBuffer(swigCPtr, this, startIndex, numParticles, outBuf, size);
  }

  public int copyWeightBuffer(int startIndex, int numParticles, java.lang.Object outBuf, int size) {
    return liquidfunJNI.ParticleSystem_copyWeightBuffer(swigCPtr, this, startIndex, numParticles, outBuf, size);
  }

  public final static class ExceptionType {
    public final static ParticleSystem.ExceptionType bufferTooSmall = new ParticleSystem.ExceptionType("bufferTooSmall");
    public final static ParticleSystem.ExceptionType particleIndexOutOfBounds = new ParticleSystem.ExceptionType("particleIndexOutOfBounds");
    public final static ParticleSystem.ExceptionType numErrors = new ParticleSystem.ExceptionType("numErrors");
    public final static ParticleSystem.ExceptionType noExceptions = new ParticleSystem.ExceptionType("noExceptions");

    public final int swigValue() {
      return swigValue;
    }

    public String toString() {
      return swigName;
    }

    public static ExceptionType swigToEnum(int swigValue) {
      if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
        return swigValues[swigValue];
      for (int i = 0; i < swigValues.length; i++)
        if (swigValues[i].swigValue == swigValue)
          return swigValues[i];
      throw new IllegalArgumentException("No enum " + ExceptionType.class + " with value " + swigValue);
    }

    private ExceptionType(String swigName) {
      this.swigName = swigName;
      this.swigValue = swigNext++;
    }

    private ExceptionType(String swigName, int swigValue) {
      this.swigName = swigName;
      this.swigValue = swigValue;
      swigNext = swigValue+1;
    }

    private ExceptionType(String swigName, ExceptionType swigEnum) {
      this.swigName = swigName;
      this.swigValue = swigEnum.swigValue;
      swigNext = this.swigValue+1;
    }

    private static ExceptionType[] swigValues = { bufferTooSmall, particleIndexOutOfBounds, numErrors, noExceptions };
    private static int swigNext = 0;
    private final int swigValue;
    private final String swigName;
  }

}
