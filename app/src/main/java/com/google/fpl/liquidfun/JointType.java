/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (https://www.swig.org).
 * Version 4.2.0
 *
 * Do not make changes to this file unless you know what you are doing - modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.google.fpl.liquidfun;

public final class JointType {
  public final static JointType e_unknownJoint = new JointType("e_unknownJoint");
  public final static JointType e_revoluteJoint = new JointType("e_revoluteJoint");
  public final static JointType e_prismaticJoint = new JointType("e_prismaticJoint");
  public final static JointType e_distanceJoint = new JointType("e_distanceJoint");
  public final static JointType e_pulleyJoint = new JointType("e_pulleyJoint");
  public final static JointType e_mouseJoint = new JointType("e_mouseJoint");
  public final static JointType e_gearJoint = new JointType("e_gearJoint");
  public final static JointType e_wheelJoint = new JointType("e_wheelJoint");
  public final static JointType e_weldJoint = new JointType("e_weldJoint");
  public final static JointType e_frictionJoint = new JointType("e_frictionJoint");
  public final static JointType e_ropeJoint = new JointType("e_ropeJoint");
  public final static JointType e_motorJoint = new JointType("e_motorJoint");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static JointType swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + JointType.class + " with value " + swigValue);
  }

  private JointType(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private JointType(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private JointType(String swigName, JointType swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static JointType[] swigValues = { e_unknownJoint, e_revoluteJoint, e_prismaticJoint, e_distanceJoint, e_pulleyJoint, e_mouseJoint, e_gearJoint, e_wheelJoint, e_weldJoint, e_frictionJoint, e_ropeJoint, e_motorJoint };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

