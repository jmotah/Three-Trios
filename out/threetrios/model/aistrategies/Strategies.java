package cs3500.threetrios.model.aistrategies;

import java.awt.Point;
import java.util.HashMap;

/**
 * Represents an interface for strategies used in the ThreeTrios game.
 * This interface defines a method to execute a strategy and return the results.
 */
public interface Strategies {

  /**
   * Runs the respective strategy. The Point object in the HashMap represents the position on the
   * grid that could result in the greatest number of cards flipped while the Integer object in
   * the hashmap represents the card index from the current player's hand that would flip the most
   * number of cards on the best grid position.
   * @return a HashMap object of a Point object and an Integer object where the Point object
   *         represents a tile on the grid to play the card at and the Integer object represents the
   *         card index to play from the current player's hand on that grid tile
   */
  HashMap<Point, Integer> runStrategy();

}
