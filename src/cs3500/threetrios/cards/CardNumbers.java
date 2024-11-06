package cs3500.threetrios.cards;

/**
 * An enumeration class to represent the valid numbers a card can have. The "A" key represents the
 * value 10.
 */
public enum CardNumbers {
  ONE(1),
  TWO(2),
  THREE(3),
  FOUR(4),
  FIVE(5),
  SIX(6),
  SEVEN(7),
  EIGHT(8),
  NINE(9),
  A(10);

  private final int value;

  /**
   * Creates a CardNumbers enum with a specified integer value.
   *
   * @param value the integer attack value, ranging from 1 to A (A is equivalent to 10)
   */
  CardNumbers(int value) {
    this.value = value;
  }

  /**
   * Returns the integer value of the card. This method must be public to be accessed within the
   * model to transfer a playing card's attack CardNumbers enum value to an integer attack value.
   *
   * @return the card's integer value
   */
  public int getValue() {
    return value;
  }

  /**
   * Returns a string value of the card. This method is primarily used to account for the "A" case
   * where the value is 10 but the preferred output is "A" for that value. All other values yield
   * the integer equivalent in a string form. This method must be public to be accessed within the
   * model to transfer a playing card's attack CardNumbers enum value to a String attack value.
   *
   * @return the value in string form
   */
  public String getValueString() {
    if (value == 10) {
      return "A";
    }
    return String.valueOf(value);
  }

  /**
   * Converts the given integer value to a CardNumbers object. This method must be public to be
   * accessed within the model to transfer a playing card's integer value to a CardNumbers enum
   * value.
   *
   * @param value the integer to convert
   * @return a CardNumbers object conversion from an integer
   */
  public static CardNumbers valueToCard(int value) {
    for (int i = 0; i < CardNumbers.values().length; i++) {
      CardNumbers card = CardNumbers.values()[i];
      if (card.getValue() == value) {
        return card;
      }
    }
    throw new IllegalArgumentException("No found card with given value!");
  }
}