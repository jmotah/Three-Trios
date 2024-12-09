package cs3500.threetrios.providers.model;

/**
 * This class encapsulates the strategies used by the AI or automated player in the Three Trios
 * game. The strategies determine the best possible move based on various criteria such as
 * maximizing flips or selecting strategic positions like corners.
*/
public class ThreeTriosStrategies {

  /**
   * * Inner class to represent a Move (position + card index).
   * This class is used to encapsulate a specific move with the position on the grid
   * and the index of the card being placed.
   */
  public static class Move {
    public final int row;
    public final int col;
    public final int cardIndex;

    /**
     * Constructs a new Move object with the given position and card index.
     *
     * @param row       the row index of the move.
     * @param col       the column index of the move.
     * @param cardIndex the card index of the move.
     */
    public Move(int row, int col, int cardIndex) {
      this.row = row;
      this.col = col;
      this.cardIndex = cardIndex;
    }

    /**
     * Returns a string representation of the Move object.
     *
     * @return a string representing the Move in the format:
     */
    @Override
    public String toString() {
      return "Move: Card Index " + cardIndex + " at (" + row + ", " + col + ")";
    }
  }
}