package cs3500.threetrios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.controller.filereader.ConfigurationReader;
import cs3500.threetrios.controller.filereader.GridReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.battlerules.BattleRules;
import cs3500.threetrios.model.battlerules.NormalBattleRule;
import cs3500.threetrios.model.battlerules.PlusBattleRule;
import cs3500.threetrios.model.battlerules.SameBattleRule;
import cs3500.threetrios.model.battlestrategies.BattleStrategies;
import cs3500.threetrios.model.battlestrategies.FallenAceBattleStrategy;
import cs3500.threetrios.model.battlestrategies.NormalBattleStrategy;
import cs3500.threetrios.model.battlestrategies.ReverseBattleStrategy;
import cs3500.threetrios.model.battlestrategies.ReverseFallenAceBattleStrategy;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.model.aistrategies.Strategy1;
import cs3500.threetrios.model.aistrategies.Strategy1And2;
import cs3500.threetrios.model.aistrategies.Strategy2;
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
            "src/cs3500/threetrios/cardconfigs/plus_rule_card_config.txt");

    File gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    if (args.length < 2) {
      throw new IllegalArgumentException("Player types must be specified!");
    }

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    ThreeTriosModel model = new GameModel();
    model.startGame(grid, deck);
    model.setBattleStrategy(new NormalBattleStrategy());

    for (int i = 2; i < args.length; i++) {
      String typeInput = args[i].toLowerCase().trim();

      if (typeInput.charAt(0) == 'r') {
        model.setBattleRule(processBattleRule(typeInput));
      } else if (typeInput.charAt(0) == 's') {
        model.setBattleStrategy(processBattleStrategy(typeInput));
      } else {
        throw new IllegalArgumentException("Unknown type input!");
      }
    }

    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosView blueView = new GraphicalView(model);

    Players playerRed = processPlayerType(args[0], PlayerColor.RED, model);
    Players playerBlue = processPlayerType(args[1], PlayerColor.BLUE, model);

    ThreeTriosController controllerRed = new ThreeTriosController(model, playerRed, redView);
    ThreeTriosController controllerBlue = new ThreeTriosController(model, playerBlue, blueView);

    redView.makeVisible();
    blueView.makeVisible();
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
  private static Players processPlayerType(String typeInput, PlayerColor color,
                                           ReadonlyThreeTriosModel model) {
    if (typeInput == null || typeInput.isEmpty()) {
      throw new IllegalArgumentException("TypeInput cannot be null or empty!");
    } else if (color == null) {
      throw new IllegalArgumentException("Color cannot be null!");
    } else if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    typeInput = typeInput.toLowerCase().trim();

    if (typeInput.equals("human")) {
      return new Player(color, new ArrayList<>());
    } else if (typeInput.equals("strategy1")) {
      return new AIPlayer(color, new ArrayList<>(), new Strategy1(model));
    } else if (typeInput.equals("strategy2")) {
      return new AIPlayer(color, new ArrayList<>(), new Strategy2(model));
    } else if (typeInput.equals("strategy1and2")) {
      return new AIPlayer(color, new ArrayList<>(), new Strategy1And2(model));
    } else {
      throw new IllegalArgumentException("Invalid player type: " + typeInput);
    }
  }

  /**
   * Processes the battle strategy based on type input.
   *
   * @param typeInput the String to analyze. The provided string must contain "strat:" before the
   *                  specified type input
   * @return the respective battle strategies object depending on the type input
   */
  private static BattleStrategies processBattleStrategy(String typeInput) {
    if (typeInput == null || typeInput.isEmpty()) {
      throw new IllegalArgumentException("TypeInput cannot be null or empty!");
    }

    typeInput = typeInput.toLowerCase().trim();

    if (typeInput.equals("strat:normal")) {
      return new NormalBattleStrategy();
    } else if (typeInput.equals("strat:reverse")) {
      return new ReverseBattleStrategy();
    } else if (typeInput.equals("strat:fallenace")) {
      return new FallenAceBattleStrategy();
    } else if (typeInput.equals("strat:reverseandfallenace")) {
      return new ReverseFallenAceBattleStrategy();
    } else {
      throw new IllegalArgumentException("Invalid type input given!");
    }
  }

  /**
   * Processes the battle rule based on type input.
   *
   * @param typeInput the String to analyze. The provided string must contain "rule:" before the
   *                  specified type input
   * @return the respective battle rule object depending on the type input
   */
  private static BattleRules processBattleRule(String typeInput) {
    if (typeInput == null || typeInput.isEmpty()) {
      throw new IllegalArgumentException("TypeInput cannot be null or empty!");
    }

    typeInput = typeInput.toLowerCase().trim();

    if (typeInput.equals("rule:normal")) {
      return new NormalBattleRule();
    } else if (typeInput.equals("rule:same")) {
      return new SameBattleRule();
    } else if (typeInput.equals("rule:plus")) {
      return new PlusBattleRule();
    } else {
      throw new IllegalArgumentException("Invalid type input given!");
    }
  }
}