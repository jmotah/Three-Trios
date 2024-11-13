package cs3500.threetrios.model.strategies;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import cs3500.threetrios.model.AbstractVariantModelTests;
import cs3500.threetrios.model.PlayerPlayerModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.player.PlayerColor;

/**
 * Represents tests for the PlayerComputerModel class. A createModel method must be instantiated
 * for the creation of the specific model we want which is the PlayerComputerModel in this case.
 * An PlayerComputerModel specific tests are beneath this.
 */
public class PlayerComputerModelTests extends AbstractVariantModelTests {

  private ThreeTriosModel model;
  private File cardConfig;
  private File gridConfig;

  @Override
  protected ThreeTriosModel createModel() { return new PlayerPlayerModel(); }

  private void init() {
    model = createModel();

    this.cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/" +
                    "src/cs3500/threetrios/cardconfigs/card_configuration.txt");
    this.gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/" +
                    "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");
  }

  @Test
  public void testPlayToGridAIInitialMove() {
    init();

    model.startGame(cardConfig, gridConfig);

    //model.playtogridAI();

    PlayingCard expected = new PlayingCard("OnesCard", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE);

    Assert.assertEquals(expected, model.getGrid()[0][0].getPlayingCard());
  }

  @Test
  public void testPlayToGridAIGoForHighestScore() {
    init();

    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(2, 2, 0);
    model.battle(2, 2);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    model.playToGrid(1, 3, 0);
    model.battle(1, 3);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

//    //model.playtogridAI();

    PlayingCard expected = new PlayingCard("SevensCard", CardNumbers.SEVEN, CardNumbers.SEVEN,
            CardNumbers.SEVEN, CardNumbers.SEVEN);

    PlayerColor expectedColor = PlayerColor.RED;

    Assert.assertEquals(expected, model.getGrid()[1][2].getPlayingCard());
    Assert.assertEquals(expectedColor, model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedColor, model.getGrid()[1][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedColor, model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedColor, model.getGrid()[1][3].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedColor, model.getGrid()[2][2].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testPlayToGridAIGoForUpmostWhenPotentialScoresAreEqual() {
    init();

    model.startGame(cardConfig, gridConfig);

    model.playToGrid(1, 1, 6);
    model.battle(1, 1);

    model.playToGrid(0, 3, 0);
    model.battle(0, 3);

    model.playToGrid(2, 1, 5);
    model.battle(2, 1);

    //model.playtogridAI();

    PlayingCard expected = new PlayingCard("FoursCard", CardNumbers.FOUR,
            CardNumbers.FOUR, CardNumbers.FOUR, CardNumbers.FOUR);
    PlayerColor expectedColor = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.getGrid()[0][1].getPlayingCard());
    Assert.assertEquals(expectedColor, model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testPlayToGridGoForLeftmostWhenPotentialScoresAreEqual() {
    init();

    gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_large.txt");

    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(5, 0, 0);
    model.battle(5, 0);

    model.playToGrid(0, 5, 0);
    model.battle(0, 5);

    //model.playtogridAI();

    PlayingCard expected = new PlayingCard("FoursCard", CardNumbers.FOUR,
            CardNumbers.FOUR, CardNumbers.FOUR, CardNumbers.FOUR);
    PlayerColor expectedColor = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.getGrid()[0][1].getPlayingCard());
    Assert.assertEquals(expectedColor, model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testPlayToGridAIGoForCornerWhenPotentialScoresAreEqual() {
    init();

    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    //model.playtogridAI();

    PlayingCard expected = new PlayingCard("FivesCard", CardNumbers.FIVE, CardNumbers.FIVE,
            CardNumbers.FIVE, CardNumbers.FIVE);

    PlayerColor expectedColor = PlayerColor.RED;

    Assert.assertEquals(expected, model.getGrid()[3][0].getPlayingCard());
    Assert.assertEquals(expectedColor, model.getGrid()[3][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedColor, model.getGrid()[3][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testPlayToGridAIGoForCornerUpperCornerPriority() {
    init();

    gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_large.txt");

    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 2, 0);
    model.battle(0, 2);

    model.playToGrid(0, 5, 0);
    model.battle(0, 5);

    model.playToGrid(0, 1, 0);
    model.battle(0, 1);

    model.playToGrid(0, 4, 0);
    model.battle(0, 4);

    model.playToGrid(5, 2, 0);
    model.battle(5, 2);

    model.playToGrid(4, 5, 0);
    model.battle(4, 5);

    model.playToGrid(5, 1, 0);
    model.battle(5, 1);

    //model.playtogridAI();

    PlayingCard expected = new PlayingCard("EightsCard", CardNumbers.EIGHT,
            CardNumbers.EIGHT, CardNumbers.EIGHT, CardNumbers.EIGHT);
    PlayerColor expectedColor = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.getGrid()[0][0].getPlayingCard());
    Assert.assertEquals(expectedColor, model.getGrid()[0][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedColor, model.getGrid()[0][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testPlayToGridAIGoForCornerUpperCornerPriority2() {
    init();

    gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_large.txt");

    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 3, 0);
    model.battle(0, 3);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(0, 4, 0);
    model.battle(0, 4);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);

    model.playToGrid(4, 1, 0);
    model.battle(4, 1);

    model.playToGrid(2, 0, 0);
    model.battle(2, 0);

    model.playToGrid(5, 1, 0);
    model.battle(5, 1);

    //model.playtogridAI();

    PlayingCard expected = new PlayingCard("EightsCard", CardNumbers.EIGHT,
            CardNumbers.EIGHT, CardNumbers.EIGHT, CardNumbers.EIGHT);
    PlayerColor expectedColor = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.getGrid()[0][5].getPlayingCard());
    Assert.assertEquals(expectedColor, model.getGrid()[0][4].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedColor, model.getGrid()[0][3].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testPlayToGridAIGoForCornerLeftmostCornerPriority() {
    init();

    this.gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_large.txt");

    model.startGame(cardConfig, gridConfig);

    model.playToGrid(2, 0, 0);
    model.battle(2, 0);

    model.playToGrid(5, 0, 0);
    model.battle(5, 0);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);

    model.playToGrid(5, 1, 0);
    model.battle(5, 1);

    model.playToGrid(2, 5, 0);
    model.battle(2, 5);

    model.playToGrid(5, 2, 0);
    model.battle(5, 2);

    model.playToGrid(1, 5, 0);
    model.battle(1, 5);

    //model.playtogridAI();

    PlayingCard expected = new PlayingCard("EightsCard", CardNumbers.EIGHT,
            CardNumbers.EIGHT, CardNumbers.EIGHT, CardNumbers.EIGHT);
    PlayerColor expectedColor = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.getGrid()[0][0].getPlayingCard());
    Assert.assertEquals(expectedColor, model.getGrid()[1][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedColor, model.getGrid()[2][0].getWhichPlayersTile().getPlayersColor());
  }
}