package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import cs3500.threetrios.model.playervsplayer.PlayerPlayerModel;
import cs3500.threetrios.player.PlayerColor;

/**
 * Abstract test class for the models of different variants of a ThreeTrios game. Requires a
 * createModel method in all classes that extend this to create the differing variant models for
 * tests.
 */
public abstract class AbstractVariantModelTests {

  /**
   * Creates the respective model object for either the BasicModelTest or AdvancedModelTest.
   *
   * @return a new, respective model object for the game type
   */
  protected abstract ThreeTriosModel createModel();

  private ThreeTriosModel model;
  private File cardConfig;
  private File gridConfig;

  @Before
  public void setup() {
    this.model = new PlayerPlayerModel();
    this.cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/src/" +
                    "cs3500/threetrios/cardconfigs/card_configuration.txt");
    this.gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/src/" +
                    "cs3500/threetrios/gridconfigs/grid_configuration.txt");
  }

  private ThreeTriosModel playGameTilGameOver() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(0, 1, 0);
    model.battle(0, 1);

    model.playToGrid(0, 2, 0);
    model.battle(0, 2);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    return model;
  }

  @Test(expected = IllegalStateException.class)
  public void testModelStartGameGameAlreadyStarted() {
    model.startGame(cardConfig, gridConfig);
    model.startGame(cardConfig, gridConfig);
  }

  @Test(expected = IllegalStateException.class)
  public void testModelStartGameGameAlreadyEnded() {
    model = playGameTilGameOver();

    model.startGame(cardConfig, gridConfig);
  }

  @Test
  public void testModelStartGameHandSizeEqualsThreeWhenLessThanThree() {
    model.startGame(cardConfig, gridConfig);

    int expected = 3;

    Assert.assertEquals(expected, model.getPlayerOfColor(PlayerColor.RED).getHand().size());
  }

  @Test(expected = IllegalStateException.class)
  public void testModelPlayToGridGameHasNotStarted() {
    model.playToGrid(0, 0,
            0);
  }

  @Test(expected = IllegalStateException.class)
  public void testModelPlayToGridGameIsOver() {
    model = playGameTilGameOver();

    model.playToGrid(0, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelPlayToGridNegativeRowOrColOrCardIdx() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(-1, -1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelPlayToGridRowOrColGreaterThanGridLength() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(4, 4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelPlayToGridCardIdxIsGreaterOrEqualToHandSize() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, model.getPlayerOfColor(PlayerColor.RED).getHand().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelPlayToGridPlayingToAlreadyPlayedToCell() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);


    model.playToGrid(0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testModelPlayToGridCalledWhileNotInPlacingPhase() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);


    model.playToGrid(0, 1, 0);
  }

  @Test
  public void testModelPlayToGridValidForFullGame() {
    model = playGameTilGameOver();

    boolean expectedIsGameOver = true;
    PlayerColor expectedFindWinningPlayer = PlayerColor.RED;

    Assert.assertEquals(expectedIsGameOver, model.isGameOver());
    Assert.assertEquals(expectedFindWinningPlayer, model.findWinningPlayer().getPlayersColor());
  }

  @Test(expected = IllegalStateException.class)
  public void testModelBattleGameNotStarted() {
    model.battle(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testModelBattleGameOver() {
    model = playGameTilGameOver();

    model.battle(1, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelBattleRowOrColLessThanZero() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(-1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelBattleRowOrColGreaterOrEqualToGridLength() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(4, 4);
  }

  @Test(expected = IllegalStateException.class)
  public void testModelBattleNotInBattlePhase() {
    model.startGame(cardConfig, gridConfig);

    model.battle(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelBattleRowAndColLeadToInvalidCard() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);

    model.battle(1, 1);
  }

  @Test
  public void testModelBattleSideBySide() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(0, 2, 0);
    model.battle(0, 2);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(0, 1, 1);
    model.battle(0, 1);

    Assert.assertEquals(PlayerColor.BLUE,
            model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(PlayerColor.BLUE,
            model.getGrid()[0][2].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(PlayerColor.BLUE,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(PlayerColor.BLUE,
            model.getGrid()[0][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testModelBattleBattleAgainstEdgeZero() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);


    model.playToGrid(0, 0, 0);
    model.battle(0, 0);


    PlayerColor expected = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testModelBattleBattleOccursRecursively() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);


    model.playToGrid(0, 1, 0);
    model.battle(0, 1);


    model.playToGrid(0, 2, 0);
    model.battle(0, 2);


    PlayerColor expected = PlayerColor.RED;

    Assert.assertEquals(expected, model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expected, model.getGrid()[0][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expected, model.getGrid()[0][2].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testModelBattleDoesNotCombatSameCard() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(0, 2, 0);
    model.battle(0, 2);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);

    Assert.assertEquals(PlayerColor.RED,
            model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(PlayerColor.BLUE,
            model.getGrid()[0][2].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(PlayerColor.RED,
            model.getGrid()[1][0].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testModelBattleEnsureCardsNotConnectingArentAffected() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);


    model.playToGrid(0, 2, 0);
    model.battle(0, 2);


    model.playToGrid(1, 1, 0);
    model.battle(1, 1);


    PlayerColor testOneAndThreeExpected = PlayerColor.RED;

    PlayerColor testTwoExpected = PlayerColor.BLUE;

    Assert.assertEquals(testOneAndThreeExpected,
            model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(testTwoExpected,
            model.getGrid()[0][2].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(testOneAndThreeExpected,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testModelBattleValidForFullGame() {
    model = playGameTilGameOver();

    PlayerColor expected = PlayerColor.RED;

    Assert.assertEquals(expected, model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expected, model.getGrid()[0][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expected, model.getGrid()[0][2].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expected, model.getGrid()[1][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expected, model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test(expected = IllegalStateException.class)
  public void testModelIsGameOverGameNotStarted() {
    model.isGameOver();
  }

  @Test
  public void testModelIsGameOverWhenNotActuallyOver() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);


    boolean expected = false;

    Assert.assertEquals(expected, model.isGameOver());
  }

  @Test
  public void testModelIsGameOverWhenActuallyOver() {
    model = playGameTilGameOver();

    boolean expected = true;

    Assert.assertEquals(expected, model.isGameOver());
  }

  @Test(expected = IllegalStateException.class)
  public void testModelFindWinningPlayerGameNotStarted() {
    model.findWinningPlayer();
  }

  @Test
  public void testModelFindWinningPlayerValid() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);


    model.playToGrid(0, 1, 0);
    model.battle(0, 1);


    PlayerColor expected = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.findWinningPlayer().getPlayersColor());
  }

  @Test(expected = IllegalStateException.class)
  public void testModelUpdatePlayerTurnGameNotStarted() {
    model.updatePlayerTurn();
  }

  @Test(expected = IllegalStateException.class)
  public void testModelUpdatePlayerTurnGameOver() {
    model = playGameTilGameOver();

    model.updatePlayerTurn();
  }

  @Test
  public void testModelUpdatePlayerTurnGivenPlayerRed() {
    model.startGame(cardConfig, gridConfig);

    model.updatePlayerTurn();

    PlayerColor expected = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.getCurrentTurnPlayer().getPlayersColor());
  }

  @Test
  public void testModelUpdatePlayerTurnGivenPlayerBlue() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.updatePlayerTurn();

    PlayerColor expected = PlayerColor.RED;

    Assert.assertEquals(expected, model.getCurrentTurnPlayer().getPlayersColor());
  }

  @Test(expected = IllegalStateException.class)
  public void testModelGetCurrentTurnPlayerGameNotStarted() {
    model.getCurrentTurnPlayer();
  }

  @Test(expected = IllegalStateException.class)
  public void testModelGetCurrentTurnPlayerGameOver() {
    model = playGameTilGameOver();

    model.getCurrentTurnPlayer();
  }

  @Test
  public void testModelGetCurrentTurnPlayerValid() {
    model.startGame(cardConfig, gridConfig);

    PlayerColor expected = PlayerColor.RED;

    Assert.assertEquals(expected, model.getCurrentTurnPlayer().getPlayersColor());
  }

  @Test
  public void testModelFullGameValid() {
    File betterCardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/src/" +
                    "cs3500/threetrios/cardconfigs/randomized_card_configuration.txt");

    model.startGame(betterCardConfig, gridConfig);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);


    model.playToGrid(0, 1, 0);
    model.battle(0, 1);


    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    Assert.assertEquals(PlayerColor.RED, model.findWinningPlayer().getPlayersColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameDeckSizeNotEnoughToStartTheGame() {
    File cardConfigThreeCards = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/cardconfigs/card_config_three_cards.txt");

    model.startGame(cardConfigThreeCards, gridConfig);
  }

  @Test
  public void testModelStartGameHandSizeWhenGreaterThanThree() {
    File gridConfigSizeOfTwenty = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_size_of_twenty.txt");

    model.startGame(cardConfig, gridConfigSizeOfTwenty);

    int expected = 5;

    Assert.assertEquals(expected, model.getCurrentTurnPlayer().getHand().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridInvarianceIsEnsured() {
    File gridConfigEvenCardCellCount = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_card_cells_even.txt");

    model.startGame(cardConfig, gridConfigEvenCardCellCount);
  }

  @Test
  public void testModelBattleCornerEdge() {
    model.startGame(cardConfig, gridConfig);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    Assert.assertEquals(PlayerColor.BLUE, model.findWinningPlayer().getPlayersColor());
  }
}