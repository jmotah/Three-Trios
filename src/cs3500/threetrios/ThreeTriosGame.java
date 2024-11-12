package cs3500.threetrios;

import java.io.File;

import cs3500.threetrios.model.playervscomputer.PlayerComputerModel;
import cs3500.threetrios.view.View;
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
    PlayerComputerModel model = new PlayerComputerModel();

    File cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/cardconfigs/card_configuration.txt");

    File gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/gridconfigs/grid_configuration.txt");

    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(0, 1, 0);
    model.battle(0, 1);

    model.playToGrid(0, 2, 0);
    model.battle(0, 2);

    model.playToGrid(0, 3, 0);
    model.battle(0, 3);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    model.playToGrid(1, 3, 0);
    model.battle(1, 3);

    model.playToGrid(2, 0, 0);
    model.battle(2, 0);

    model.playToGrid(2, 1, 0);
    model.battle(2, 1);

    model.playToGrid(2, 2, 0);
    model.battle(2, 2);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    model.playToGrid(3, 0, 0);
    model.battle(3, 0);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    View view = new GraphicalView(model);
    view.makeVisible();

    model.playToGrid(3, 2, 0);
    model.battle(3, 2);
  }
}