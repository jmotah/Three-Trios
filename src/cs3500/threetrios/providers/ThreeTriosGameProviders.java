package cs3500.threetrios;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.controller.filereader.GridReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.model.strategies.Strategy1;
import cs3500.threetrios.model.strategies.Strategy1And2;
import cs3500.threetrios.model.strategies.Strategy2;
import cs3500.threetrios.providers.model.object_adapters.MainModelToProviderReadonlyThreeTrios;
import cs3500.threetrios.providers.model.object_adapters.ProviderViewToMainView;
import cs3500.threetrios.providers.view.GUIView;

/**
 * The primary class to start the ThreeTrios game.
 */
public class ThreeTriosGameProviders {
  /**
   * The main method to run a ThreeTrios game. Initializes the game model and view while reading
   * the card configuration and grid configuration file to initialize. Simulates the entire game.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    File cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/card_configuration.txt");

    File gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    if (args.length < 2) {
      throw new IllegalArgumentException("Player types must be specified!");
    }

    CardReader cardReader = new CardReader(cardConfig);
    GridReader gridReader = new GridReader(gridConfig);
    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    ThreeTriosModel model = new GameModel();
    model.startGame(grid, deck);

    MainModelToProviderReadonlyThreeTrios providerReadOnlyModel =
            new MainModelToProviderReadonlyThreeTrios(model);

    GUIView providerRedView = new GUIView(providerReadOnlyModel);
    GUIView providerBlueView = new GUIView(providerReadOnlyModel);

    Players playerRed = processPlayerType(args[0], PlayerColor.RED, model);
    Players playerBlue = processPlayerType(args[1], PlayerColor.BLUE, model);

    ThreeTriosController controllerRed = new ThreeTriosController(model,
            playerRed,
            new ProviderViewToMainView(providerRedView));
    ThreeTriosController controllerBlue = new ThreeTriosController(model,
            playerBlue,
            new ProviderViewToMainView(providerBlueView));

    providerRedView.setVisible(true);
    providerBlueView.setVisible(true);
    providerRedView.render();
    providerBlueView.render();

    model.addViewListener(controllerRed);
    model.addViewListener(controllerBlue);

    System.out.println();
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
}