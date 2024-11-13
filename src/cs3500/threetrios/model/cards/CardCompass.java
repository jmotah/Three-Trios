package cs3500.threetrios.model.cards;

/**
 * An enumeration class to represent the valid number positions on each PlayingCard. These
 * positions represent cardinal direction values.
 */
public enum CardCompass {
  NORTH_VALUE,
  SOUTH_VALUE,
  EAST_VALUE,
  WEST_VALUE;

  /**
   * Returns what the opposite compass direction of whatever this object's current card compass
   * direction. This method must be public to be accessed within the
   * model to find the opposite direction of the given CardCompass enum value. (Ex: NORTH_VALUE
   * yields SOUTH_VALUE, EAST_VALUE yields WEST_VALUE, etc.)
   *
   * @return an CardCompass object which is the opposite of this CardCompass object
   */
  public CardCompass oppositeDirection() {
    switch (this) {
      case NORTH_VALUE:
        return SOUTH_VALUE;
      case SOUTH_VALUE:
        return NORTH_VALUE;
      case EAST_VALUE:
        return WEST_VALUE;
      case WEST_VALUE:
        return EAST_VALUE;
      default:
        throw new IllegalArgumentException("Invalid CardCompass object direction!");
    }
  }
}