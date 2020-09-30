package com.sensiblemetrics.api.ws.webdocs.commons.constraint;

import lombok.experimental.UtilityClass;

/** Constraint group validation declaration */
@UtilityClass
public class ConstraintGroup {
  /** Default on create constraint group validation */
  public interface OnCreate {}

  /** Default on update constraint group validation */
  public interface OnUpdate {}

  /** Default on delete constraint group validation */
  public interface OnDelete {}

  /** Default on fetch constraint group validation */
  public interface OnSelect {}
}
