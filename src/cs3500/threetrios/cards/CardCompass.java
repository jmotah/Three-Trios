package cs3500.threetrios.cards;

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
   * direction.
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