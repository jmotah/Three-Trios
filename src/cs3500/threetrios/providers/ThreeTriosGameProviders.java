package cs3500.threetrios.providers;

import java.io.File;
import java.security.Provider;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.controller.filereader.GridReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.model.strategies.Strategy1;
import cs3500.threetrios.model.strategies.Strategy1And2;
import cs3500.threetrios.model.strategies.Strategy2;
import cs3500.threetrios.providers.controller.ProviderControllerAdapter;
import cs3500.threetrios.providers.model.AttackValue;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Grid;
import cs3500.threetrios.providers.model.Player;
import cs3500.threetrios.providers.model.PlayerColor;
import cs3500.threetrios.providers.model.ProviderAIPlayerAdapter;
import cs3500.threetrios.providers.model.ProviderCardAdapter;
import cs3500.threetrios.providers.model.ProviderGridAdapter;
import cs3500.threetrios.providers.model.ProviderGridCellAdapter;
import cs3500.threetrios.providers.model.ProviderHumanAdapter;
import cs3500.threetrios.providers.model.ProviderModelAdapter;
import cs3500.threetrios.providers.model.ProviderStrategy1Adapter;
import cs3500.threetrios.providers.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.providers.view.ExtendedView;
import cs3500.threetrios.providers.view.GUIView;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.graphical.GraphicalView;

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

    Utilities utilities = new Utilities();

    GridReader gridReader = new GridReader(gridConfig);
    GridTile[][] grid = gridReader.readConfiguration();
    CardReader cardReader = new CardReader(cardConfig);
    List<PlayingCard> deck = cardReader.readConfiguration();

    Grid providerGrid = utilities.mainGridToProviderGrid(grid);
    List<Card> providerDeck = utilities.mainPlayingCardListToProviderCardList(deck);

    cs3500.threetrios.providers.model.ThreeTriosModel model =
            new ProviderModelAdapter(providerGrid, providerDeck);

    model.startGame();
    GUIView redView = new GUIView(model);
    GUIView blueView = new GUIView(model);

    Player playerRed = processPlayerType(args[0], PlayerColor.RED, model);
    Player playerBlue = processPlayerType(args[1], PlayerColor.BLUE, model);

    ProviderControllerAdapter controllerRed = new ProviderControllerAdapter(
            model,
            playerRed,
            redView);
    ProviderControllerAdapter controllerBlue = new ProviderControllerAdapter(
            model,
            playerBlue,
            blueView);

    redView.setVisible(true);
    redView.render();
    blueView.setVisible(true);
    blueView.render();
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
  private static Player processPlayerType(String typeInput,
                                          cs3500.threetrios.providers.model.PlayerColor color,
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
      return new ProviderHumanAdapter(color, new ArrayList<>());
    } else if (typeInput.equals("strategy1")) {
      return new ProviderHumanAdapter(color, new ArrayList<>());
      //would return AI Player
      //return new AIPlayer(color, new ArrayList<>(), new Strategy1(model));
      //return new ProviderAIPlayerAdapter(color, new ArrayList<>(),
      //new ProviderStrategy1Adapter(model),
      //model);
    } else if (typeInput.equals("strategy2")) {
      return new ProviderHumanAdapter(color, new ArrayList<>());
      //return new AIPlayer(color, new ArrayList<>(), new Strategy2(model));
    } else if (typeInput.equals("strategy1and2")) {
      return new ProviderHumanAdapter(color, new ArrayList<>());
      //return new AIPlayer(color, new ArrayList<>(), new Strategy1And2(model));
    } else {
      throw new IllegalArgumentException("Invalid player type: " + typeInput);
    }
  }
}