package cs3500.threetrios.model.strategies;

import java.awt.*;
import java.util.HashMap;

/**
 * Represents an interface for strategies used in the ThreeTrios game.
 * This interface defines a method to execute a strategy and return the results.
 */

/**
 * Represents an interface for the AI strategies used in the game.
 */
public interface Strategies {

  HashMap<Point, Integer> runStrategy();

}
