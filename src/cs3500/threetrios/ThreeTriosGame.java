package cs3500.threetrios;

import java.io.File;
import java.util.ArrayList;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;
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
            "src/cs3500/threetrios/cardconfigs/tie_setup_card_config.txt");

    File gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    if (args.length < 2) {
      throw new IllegalArgumentException("Player types must be specified!");
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

  /**
   * Processes the player types based on type input. Provides a given color to the created
   * Players object.
   *
   * @param typeInput the String to analyze
   * @param color     the color to associate with the created Players object
   * @param model     the model object
   * @return a new Players object
   */
  private static Players processPlayerType(String typeInput, PlayerColor color, ReadonlyThreeTriosModel model) {
    if (typeInput == null || typeInput.isEmpty()) {
      throw new IllegalArgumentException("TypeInput cannot be null or empty!");
    } else if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    } else if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

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