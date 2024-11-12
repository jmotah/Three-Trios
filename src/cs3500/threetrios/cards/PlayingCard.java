package cs3500.threetrios.cards;

import java.util.Objects;

/**
 * Represents a valid playing card within the ThreeTrios game.
 */
public class PlayingCard implements Cards {
  private final String name;
  private final CardNumbers north;
  private final CardNumbers south;
  private final CardNumbers east;
  private final CardNumbers west;

  /**
   * The PlayingCard class constructor.
   *
   * @param name  the name of the card
   * @param north the value of the north number of the card
   * @param south the value of the south number of the card
   * @param east  the value of the east number of the card
   * @param west  the value of the west number of the card
   */
  public PlayingCard(String name, CardNumbers north, CardNumbers south,
                     CardNumbers east, CardNumbers west) {
    if (name == null || north == null || south == null || east == null || west == null) {
      throw new IllegalArgumentException("Name and/or one of the CardNumbers object respective to "
              + "its direction is null!");
    }
    this.name = name;
    this.north = north;
    this.south = south;
    this.east = east;
    this.west = west;
  }

  /**
   * The translation of a PlayingCard object to a String text.
   *
   * @return String text of a PlayingCard object translated
   */
  @Override
  public String toString() {
    return this.name + " " + this.north.getValueString() + " " + this.south.getValueString() +
            " " + this.east.getValueString() + " " + this.west.getValueString();
  }

  /**
   * Gets the name of the PlayingCard object.
   *
   * @return the name of the playing card
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * The value of the PlayingCard object at a specific direction.
   *
   * @param direction the specific direction to grab the value from
   * @return the integer value of the PlayingCard object at the specific direction
   */
  @Override
  public int getValue(CardCompass direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Cannot grab from a null direction!");
    }

    if (direction == CardCompass.NORTH_VALUE) {
      return north.getValue();
    } else if (direction == CardCompass.SOUTH_VALUE) {
      return south.getValue();
    } else if (direction == CardCompass.EAST_VALUE) {
      return east.getValue();
    }
    return west.getValue();
  }

  /**
   * Gets the value as a string of the PlayingCard object at a specific direction. This method is
   * primarily used to consider the "A" case.
   *
   * @param direction the specific direction to grab the value from
   * @return the String value of the PlayingCard object at the specific direction, accounting for
   * "A"
   */
  public String getValueAsString(CardCompass direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Cannot grab from a null direction!");
    }

    if (direction == CardCompass.NORTH_VALUE) {
      return checkForACase(north);
    } else if (direction == CardCompass.SOUTH_VALUE) {
      return checkForACase(south);
    } else if (direction == CardCompass.EAST_VALUE) {
      return checkForACase(east);
    }
    return checkForACase(west);
  }

  /**
   * Checks to see if the number value given is 10. If it is, returns "A", if not, returns the
   * normal integer value as a String.
   *
   * @param number the CardNumbers object value of the number to check
   * @return the String value of the card number
   */
  public String checkForACase(CardNumbers number) {
    if (number == CardNumbers.A) {
      return "A";
    } else {
      return "" + number.getValue();
    }
  }

  /**
   * Checks if this PlayingCard object is equal to the given Object object.
   *
   * @param object the Object object to check if this PlayingCard object is equal to or not
   * @return true if the objects are equal, false if otherwise
   */
  @Override
  public boolean equals(Object object) {
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    return this == object ||
            (this.north == ((PlayingCard) object).north &&
                    this.south == ((PlayingCard) object).south &&
                    this.east == ((PlayingCard) object).east &&
                    this.west == ((PlayingCard) object).west);
  }

  /**
   * Returns a hash code for this object.
   *
   * @return an integer representing the hash code for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.north, this.south, this.east, this.west);
  }
}