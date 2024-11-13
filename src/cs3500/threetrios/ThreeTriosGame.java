package cs3500.threetrios;

import java.awt.*;
import java.io.File;
import java.util.HashMap;

import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.PlayerPlayerModel;
import cs3500.threetrios.model.strategies.Strategies;
import cs3500.threetrios.model.strategies.Strategy1And2;
import cs3500.threetrios.model.strategies.Strategy2;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.graphical.GraphicalView;

/**
 * The primary class to start the ThreeTrios game.
 */
public class ThreeTriosGame {
  /**
   * The main method to run a ThreeTrios game. Initializes the game model and view while reading
   * the card configuration and grid configuration file to initialize. Simulates the entire game.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    ThreeTriosModel model = new PlayerPlayerModel();

    File cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/cardconfigs/card_configuration.txt");

    File gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/gridconfigs/grid_configuration.txt");

    model.startGame(cardConfig, gridConfig);

    Strategies strategy = new Strategy2(model);

    playToGridAndBattleWithOptimalMove(strategy.runStrategy(), model);
    playToGridAndBattleWithOptimalMove(strategy.runStrategy(), model);
    playToGridAndBattleWithOptimalMove(strategy.runStrategy(), model);
    playToGridAndBattleWithOptimalMove(strategy.runStrategy(), model);

    ThreeTriosView view = new GraphicalView(model);
    view.makeVisible();
  }

  /**
   * Converts the HashMap of a Point object and Integer object to readable play to grid and
   * battle commands by the model. The Point object represents a grid cell position while the
   * Integer object represents a card index in the player's hand.
   *
   * @param optimalMove the HashMap object to get the grid position to play to and the card index to
   *                    play from
   * @param model       the model object to call the optimal move onto
   */
  public static void playToGridAndBattleWithOptimalMove(HashMap<Point, Integer> optimalMove,
                                                        ThreeTriosModel model) {
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    model.playToGrid(x, y, cardIdx);
    model.battle(x, y);
  }
}