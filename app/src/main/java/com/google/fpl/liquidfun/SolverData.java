/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class SolverData {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected SolverData(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SolverData obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(SolverData obj) {
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
        liquidfunJNI.delete_SolverData(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setStep(TimeStep value) {
    liquidfunJNI.SolverData_step_set(swigCPtr, this, TimeStep.getCPtr(value), value);
  }

  public TimeStep getStep() {
    return new TimeStep(liquidfunJNI.SolverData_step_get(swigCPtr, this), false);
  }

  public void setPositions(Position value) {
    liquidfunJNI.SolverData_positions_set(swigCPtr, this, Position.getCPtr(value), value);
  }

  public Position getPositions() {
    long cPtr = liquidfunJNI.SolverData_positions_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Position(cPtr, false);
  }

  public void setVelocities(Velocity value) {
    liquidfunJNI.SolverData_velocities_set(swigCPtr, this, Velocity.getCPtr(value), value);
  }

  public Velocity getVelocities() {
    long cPtr = liquidfunJNI.SolverData_velocities_get(swigCPtr, this);
    return (cPtr == 0) ? null : new Velocity(cPtr, false);
  }

  public SolverData() {
    this(liquidfunJNI.new_SolverData(), true);
  }

}
