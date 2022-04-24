package model.worldbuilder;

/**
 * Represents the current iteration while parsing the input file in world builder.
 */
public enum CurrentIteration {
  NONE("none"),
  ROOMS("rooms"),
  WEAPONS("weapons");

  private final String display;

  CurrentIteration(String disp) {
    this.display = disp;
  }

  @Override
  public String toString() {
    return display;
  }

}
