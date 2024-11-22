package cs3500.threetrios.model.strategies;

import java.awt.Point;
import java.util.HashMap;

/**
 * Represents an interface for strategies used in the ThreeTrios game.
 * This interface defines a method to execute a strategy and return the results.
 */
public interface Strategies {

  HashMap<Point, Integer> runStrategy();

}
