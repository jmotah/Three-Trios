package cs3500.threetrios;

import java.awt.Point;
import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.PlayerPlayerModel;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.model.strategies.Strategy1;
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
    File cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/cardconfigs/randomized_card_configuration.txt");

    File gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/gridconfigs/grid_configuration.txt");

    if (args.length == 0) {
      throw new IllegalArgumentException("A game type must be specified!");
    }

    ThreeTriosModel model = new PlayerPlayerModel();
    model.startGame(cardConfig, gridConfig);
    ThreeTriosView viewPlayer1 = new GraphicalView(model);
    ThreeTriosView viewPlayer2 = new GraphicalView(model);
    Players playerRed = processPlayerType(args[0], PlayerColor.RED, model);
    Players playerBlue = processPlayerType(args[1], PlayerColor.BLUE, model);
    ThreeTriosController controller1 = new ThreeTriosController(model, playerRed, viewPlayer1);
    ThreeTriosController controller2 = new ThreeTriosController(model, playerBlue, viewPlayer2);
    viewPlayer1.makeVisible();
    viewPlayer2.makeVisible();
  }

  private static Players processPlayerType(String typeInput, PlayerColor color, ThreeTriosModel model) {
    switch (typeInput.toLowerCase()) {
      case "human":
        return new Player(color, new ArrayList<>());
      case "strategy1":
        return new AIPlayer(color, new ArrayList<>(), new Strategy1(model));
      case "strategy2":
        new AIPlayer(color, new ArrayList<>(), new Strategy2(model));
      case "strategy1and2":
        new AIPlayer(color, new ArrayList<>(), new Strategy1And2(model));
      default:
        throw new IllegalArgumentException("Invalid player type: " + typeInput);
    }
  }
}