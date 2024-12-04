package cs3500.threetrios.model.cards;

/**
 * Represents functionality for a playing card within the ThreeTrios game.
 */
public interface Cards {
  /**
   * Gets the name of the PlayingCard object.
   *
   * @return the name of the playing card
   */
  String getName();

  /**
   * The value of the PlayingCard object at a specific direction.
   *
   * @param direction the specific direction to grab the value from
   * @return the integer value of the PlayingCard object at the specific direction
   */
  int getValue(CardCompass direction);

  /**
   * Gets the value as a string of the PlayingCard object at a specific direction. This method is
   * primarily used to consider the "A" case.
   *
   * @param direction the specific direction to grab the value from
   * @return the String value of the PlayingCard object at the specific direction, accounting for A
   */
  String getValueAsString(CardCompass direction);

  /**
   * The translation of a PlayingCard object to a String text.
   *
   * @return String text of a PlayingCard object translated
   */
  String toString();
}
