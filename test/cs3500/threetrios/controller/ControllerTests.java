package cs3500.threetrios.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.controller.filereader.GridReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.GridTile;
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
 * Represents tests for the ThreeTrios game controller which manages instructions
 * between the controller, model, and view.
 */
public class ControllerTests {

  private ThreeTriosModel model;

  private Grid[][] grid;
  private List<Cards> deck;

  @Before
  public void setup() {
    model = new GameModel();
    File cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/" +
                    "src/cs3500/threetrios/cardconfigs/card_configuration.txt");
    File gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/" +
                    "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    GridReader gridReader = new GridReader(gridConfig);
    CardReader cardReader = new CardReader(cardConfig);

    grid = gridReader.readConfiguration();
    deck = cardReader.readConfiguration();

    model.startGame(grid, deck);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullModel() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(null, redPlayer, redView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullPlayer() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, null, redView);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testControllerNullView() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, null);
  }

  @Test
  public void testControllerSelectCardSelectGridCellRedPlayer() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView view = new GraphicalView(model);
    ThreeTriosController controller = new ThreeTriosController(model, redPlayer, view);

    controller.selectCard(1);
    controller.selectGridCell(2, 2);

    PlayingCard expected = new PlayingCard("ThreesCard", CardNumbers.THREE, CardNumbers.THREE,
            CardNumbers.THREE, CardNumbers.THREE);

    Assert.assertEquals(expected, model.getGrid()[2][2].getPlayingCard());
  }

  @Test
  public void testControllerSelectCardSelectGridCellBluePlayer() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, redView);

    Players bluePlayer = model.getPlayerOfColor(PlayerColor.BLUE);
    ThreeTriosView blueView = new GraphicalView(model);
    ThreeTriosController blueController = new ThreeTriosController(model, bluePlayer, blueView);

    redController.selectCard(1);
    redController.selectGridCell(2, 2);

    blueController.selectCard(3);
    blueController.selectGridCell(0, 1);

    PlayingCard expected1 = new PlayingCard("ThreesCard", CardNumbers.THREE,
            CardNumbers.THREE, CardNumbers.THREE, CardNumbers.THREE);

    PlayingCard expected2 = new PlayingCard("EightsCard", CardNumbers.EIGHT,
            CardNumbers.EIGHT, CardNumbers.EIGHT, CardNumbers.EIGHT);

    Assert.assertEquals(expected1, model.getGrid()[2][2].getPlayingCard());
    Assert.assertEquals(expected2, model.getGrid()[0][1].getPlayingCard());
  }

  @Test
  public void testControllerSelectCardEdgeCase0SelectGridCell() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView view = new GraphicalView(model);
    ThreeTriosController controller = new ThreeTriosController(model, redPlayer, view);

    controller.selectCard(0);
    controller.selectGridCell(2, 2);

    PlayingCard expected = new PlayingCard("OnesCard", CardNumbers.ONE, CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE);

    Assert.assertEquals(expected, model.getGrid()[2][2].getPlayingCard());
  }

  @Test
  public void testControllerSelectCardEdgeCaseLastSelectGridCell() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView view = new GraphicalView(model);
    ThreeTriosController controller = new ThreeTriosController(model, redPlayer, view);

    controller.selectCard(model.getCurrentTurnPlayer().getHand().size() - 1);
    controller.selectGridCell(2, 2);

    PlayingCard expected = new PlayingCard("15thCard", CardNumbers.THREE, CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE);

    Assert.assertEquals(expected, model.getGrid()[2][2].getPlayingCard());
  }

  @Test
  public void testControllerSelectCardSelectGridCellEdgeCase00() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView view = new GraphicalView(model);
    ThreeTriosController controller = new ThreeTriosController(model, redPlayer, view);

    controller.selectCard(1);
    controller.selectGridCell(0, 0);

    PlayingCard expected = new PlayingCard("ThreesCard", CardNumbers.THREE, CardNumbers.THREE,
            CardNumbers.THREE, CardNumbers.THREE);

    Assert.assertEquals(expected, model.getGrid()[0][0].getPlayingCard());
  }

  @Test
  public void testControllerSelectCardSelectGridCellEdgeCase0Last() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView view = new GraphicalView(model);
    ThreeTriosController controller = new ThreeTriosController(model, redPlayer, view);

    controller.selectCard(1);
    controller.selectGridCell(0, 3);

    PlayingCard expected = new PlayingCard("ThreesCard", CardNumbers.THREE, CardNumbers.THREE,
            CardNumbers.THREE, CardNumbers.THREE);

    Assert.assertEquals(expected, model.getGrid()[0][3].getPlayingCard());
  }

  @Test
  public void testControllerSelectCardSelectGridCellEdgeCaseLast0() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView view = new GraphicalView(model);
    ThreeTriosController controller = new ThreeTriosController(model, redPlayer, view);

    controller.selectCard(1);
    controller.selectGridCell(3, 0);

    PlayingCard expected = new PlayingCard("ThreesCard", CardNumbers.THREE, CardNumbers.THREE,
            CardNumbers.THREE, CardNumbers.THREE);

    Assert.assertEquals(expected, model.getGrid()[3][0].getPlayingCard());
  }

  @Test(expected = IllegalStateException.class)
  public void testControllerSelectCardSelectGridCellEdgeCaseLastLast() {
    Players redPlayer = model.getPlayerOfColor(PlayerColor.RED);
    ThreeTriosView view = new GraphicalView(model);
    ThreeTriosController controller = new ThreeTriosController(model, redPlayer, view);

    controller.selectCard(1);
    controller.selectGridCell(3, 3);
  }

  @Test
  public void testControllerAIRedPlayerStrategy1() {
    Players redPlayer = new AIPlayer(PlayerColor.RED, List.of(),
            new Strategy1(model));
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, redView);

    PlayingCard expected = new PlayingCard("OnesCard", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE);

    Assert.assertEquals(expected, model.getGrid()[0][0].getPlayingCard());
  }

  @Test
  public void testControllerAIRedPlayerStrategy2() {
    Players redPlayer = new AIPlayer(PlayerColor.RED, List.of(),
            new Strategy2(model));
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, redView);

    PlayingCard expected = new PlayingCard("NinesCard", CardNumbers.NINE,
            CardNumbers.NINE, CardNumbers.NINE, CardNumbers.NINE);

    Assert.assertEquals(expected, model.getGrid()[0][0].getPlayingCard());
  }

  @Test
  public void testControllerAIRedPlayerStrategy1And2() {
    Players redPlayer = new AIPlayer(PlayerColor.RED, List.of(),
            new Strategy1And2(model));
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, redView);

    PlayingCard expected = new PlayingCard("NinesCard", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE);

    Assert.assertEquals(expected, model.getGrid()[0][0].getPlayingCard());
  }

  @Test
  public void testControllerHumanAIBluePlayerStrategy1() {
    Players redPlayer = new Player(PlayerColor.RED, List.of());
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, redView);

    Players bluePlayer = new AIPlayer(PlayerColor.BLUE, List.of(),
            new Strategy1(model));
    ThreeTriosView blueView = new GraphicalView(model);
    ThreeTriosController blueController = new ThreeTriosController(model, bluePlayer, blueView);

    redController.selectCard(0);
    redController.selectGridCell(0, 0);

    PlayingCard expected = new PlayingCard("TwosCard", CardNumbers.TWO,
            CardNumbers.TWO, CardNumbers.TWO, CardNumbers.TWO);

    Assert.assertEquals(expected, model.getGrid()[0][1].getPlayingCard());
  }

  @Test
  public void testControllerHumanAIBluePlayerStrategy2() {
    Players redPlayer = new Player(PlayerColor.RED, List.of());
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, redView);

    Players bluePlayer = new AIPlayer(PlayerColor.BLUE, List.of(),
            new Strategy2(model));
    ThreeTriosView blueView = new GraphicalView(model);
    ThreeTriosController blueController = new ThreeTriosController(model, bluePlayer, blueView);

    redController.selectCard(0);
    redController.selectGridCell(0, 0);

    PlayingCard expected = new PlayingCard("AsCard", CardNumbers.A,
            CardNumbers.A, CardNumbers.A, CardNumbers.A);

    Assert.assertEquals(expected, model.getGrid()[0][3].getPlayingCard());
  }

  @Test
  public void testControllerHumanAIBluePlayerStrategy1And2() {
    Players redPlayer = new Player(PlayerColor.RED, List.of());
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, redView);

    Players bluePlayer = new AIPlayer(PlayerColor.BLUE, List.of(),
            new Strategy1And2(model));
    ThreeTriosView blueView = new GraphicalView(model);
    ThreeTriosController blueController = new ThreeTriosController(model, bluePlayer, blueView);

    redController.selectCard(0);
    redController.selectGridCell(0, 0);

    PlayingCard expected = new PlayingCard("TwosCard", CardNumbers.TWO,
            CardNumbers.TWO, CardNumbers.TWO, CardNumbers.TWO);

    Assert.assertEquals(expected, model.getGrid()[0][1].getPlayingCard());
  }

  @Test
  public void testControllerAIRedPlayerStrategy1AIBluePlayerStrategy1() {
    Players redPlayer = new AIPlayer(PlayerColor.RED, List.of(),
            new Strategy1(model));
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, redView);

    Players bluePlayer = new AIPlayer(PlayerColor.BLUE, List.of(),
            new Strategy1(model));
    ThreeTriosView blueView = new GraphicalView(model);
    ThreeTriosController blueController = new ThreeTriosController(model, bluePlayer, blueView);

    PlayingCard expected1 = new PlayingCard("OnesCard", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE);
    PlayingCard expected2 = new PlayingCard("TwosCard", CardNumbers.TWO,
            CardNumbers.TWO, CardNumbers.TWO, CardNumbers.TWO);

    Assert.assertEquals(expected1, model.getGrid()[0][0].getPlayingCard());
    Assert.assertEquals(expected2, model.getGrid()[0][1].getPlayingCard());
  }

  @Test
  public void testControllerAIRedPlayerStrategy1AIBluePlayerStrategy2() {
    Players redPlayer = new AIPlayer(PlayerColor.RED, List.of(),
            new Strategy1(model));
    ThreeTriosView redView = new GraphicalView(model);
    ThreeTriosController redController = new ThreeTriosController(model, redPlayer, redView);

    Players bluePlayer = new AIPlayer(PlayerColor.BLUE, List.of(),
            new Strategy2(model));
    ThreeTriosView blueView = new GraphicalView(model);
    ThreeTriosController blueController = new ThreeTriosController(model, bluePlayer, blueView);

    PlayingCard expected1 = new PlayingCard("OnesCard", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE);
    PlayingCard expected2 = new PlayingCard("AsCard", CardNumbers.A,
            CardNumbers.A, CardNumbers.A, CardNumbers.A);

    Assert.assertEquals(expected1, model.getGrid()[0][0].getPlayingCard());
    Assert.assertEquals(expected2, model.getGrid()[0][3].getPlayingCard());
  }

  @Test
  public void testPlayerAddActionListener() {
    Players redPlayer = model.getCurrentTurnPlayer();

    final boolean[] cardSelected = {false};
    Features mock = new Features() {
      @Override
      public void selectCard(int cardIndex) {
        cardSelected[0] = true;
      }

      @Override
      public void selectGridCell(int row, int column) {
        cardSelected[0] = true;
      }
    };

    redPlayer.addActionListener(mock);
    Assert.assertFalse(cardSelected[0]);
  }
}