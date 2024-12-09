/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public class TOIOutput {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected TOIOutput(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(TOIOutput obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected static long swigRelease(TOIOutput obj) {
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
        liquidfunJNI.delete_TOIOutput(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setState(TOIOutput.State value) {
    liquidfunJNI.TOIOutput_state_set(swigCPtr, this, value.swigValue());
  }

  public TOIOutput.State getState() {
    return TOIOutput.State.swigToEnum(liquidfunJNI.TOIOutput_state_get(swigCPtr, this));
  }

  public void setT(float value) {
    liquidfunJNI.TOIOutput_t_set(swigCPtr, this, value);
  }

  public float getT() {
    return liquidfunJNI.TOIOutput_t_get(swigCPtr, this);
  }

  public TOIOutput() {
    this(liquidfunJNI.new_TOIOutput(), true);
  }

  public final static class State {
    public final static TOIOutput.State e_unknown = new TOIOutput.State("e_unknown");
    public final static TOIOutput.State e_failed = new TOIOutput.State("e_failed");
    public final static TOIOutput.State e_overlapped = new TOIOutput.State("e_overlapped");
    public final static TOIOutput.State e_touching = new TOIOutput.State("e_touching");
    public final static TOIOutput.State e_separated = new TOIOutput.State("e_separated");

    public final int swigValue() {
      return swigValue;
    }

    public String toString() {
      return swigName;
    }

    public static State swigToEnum(int swigValue) {
      if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
        return swigValues[swigValue];
      for (int i = 0; i < swigValues.length; i++)
        if (swigValues[i].swigValue == swigValue)
          return swigValues[i];
      throw new IllegalArgumentException("No enum " + State.class + " with value " + swigValue);
    }

    private State(String swigName) {
      this.swigName = swigName;
      this.swigValue = swigNext++;
    }

    private State(String swigName, int swigValue) {
      this.swigName = swigName;
      this.swigValue = swigValue;
      swigNext = swigValue+1;
    }

    private State(String swigName, State swigEnum) {
      this.swigName = swigName;
      this.swigValue = swigEnum.swigValue;
      swigNext = this.swigValue+1;
    }

    private static State[] swigValues = { e_unknown, e_failed, e_overlapped, e_touching, e_separated };
    private static int swigNext = 0;
    private final int swigValue;
    private final String swigName;
  }

}