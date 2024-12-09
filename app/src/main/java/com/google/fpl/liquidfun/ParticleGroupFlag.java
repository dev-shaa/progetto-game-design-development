/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public final class ParticleGroupFlag {
  public final static ParticleGroupFlag solidParticleGroup = new ParticleGroupFlag("solidParticleGroup", liquidfunJNI.solidParticleGroup_get());
  public final static ParticleGroupFlag rigidParticleGroup = new ParticleGroupFlag("rigidParticleGroup", liquidfunJNI.rigidParticleGroup_get());
  public final static ParticleGroupFlag particleGroupCanBeEmpty = new ParticleGroupFlag("particleGroupCanBeEmpty", liquidfunJNI.particleGroupCanBeEmpty_get());
  public final static ParticleGroupFlag particleGroupWillBeDestroyed = new ParticleGroupFlag("particleGroupWillBeDestroyed", liquidfunJNI.particleGroupWillBeDestroyed_get());
  public final static ParticleGroupFlag particleGroupNeedsUpdateDepth = new ParticleGroupFlag("particleGroupNeedsUpdateDepth", liquidfunJNI.particleGroupNeedsUpdateDepth_get());
  public final static ParticleGroupFlag particleGroupInternalMask = new ParticleGroupFlag("particleGroupInternalMask", liquidfunJNI.particleGroupInternalMask_get());

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static ParticleGroupFlag swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + ParticleGroupFlag.class + " with value " + swigValue);
  }

  private ParticleGroupFlag(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private ParticleGroupFlag(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private ParticleGroupFlag(String swigName, ParticleGroupFlag swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static ParticleGroupFlag[] swigValues = { solidParticleGroup, rigidParticleGroup, particleGroupCanBeEmpty, particleGroupWillBeDestroyed, particleGroupNeedsUpdateDepth, particleGroupInternalMask };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

