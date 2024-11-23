package cs3500.threetrios.controller.filereader;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.model.PlayerPlayerModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.graphical.GraphicalView;

public class ControllerTests {

  private ThreeTriosModel model;
  private File cardConfig;
  private File gridConfig;

  @Before
  public void setup() {
    model = new PlayerPlayerModel();
    this.cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/" +
                    "src/cs3500/threetrios/cardconfigs/card_configuration.txt");
    this.gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/" +
                    "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");
    model.startGame(cardConfig, gridConfig);
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



}