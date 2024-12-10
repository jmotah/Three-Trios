package cs3500.threetrios.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import cs3500.threetrios.controller.ThreeTriosModelListener;
import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.controller.filereader.GridReader;
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
import cs3500.threetrios.model.player.AIPlayerListener;
import cs3500.threetrios.model.player.PlayerColor;

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

  private Grid[][] grid;
  private List<Cards> deck;

  @Before
  public void setup() {
    this.model = createModel();

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
  }

  private ThreeTriosModel playGameTilGameOver() {
    model.startGame(grid, deck);

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

    model.playToGrid(3, 2, 0);
    model.battle(3, 2);

    return model;
  }

  @Test(expected = IllegalStateException.class)
  public void testModelStartGameGameAlreadyStarted() {
    model.startGame(grid, deck);
    model.startGame(grid, deck);
  }

  @Test(expected = IllegalStateException.class)
  public void testModelStartGameGameAlreadyEnded() {
    model = playGameTilGameOver();

    model.startGame(grid, deck);
  }

  @Test
  public void testModelStartGameHandSizeEqualsThreeWhenLessThanThree() {
    model.startGame(grid, deck);

    int expected = 8;

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
    model.startGame(grid, deck);

    model.playToGrid(-1, -1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelPlayToGridRowOrColGreaterThanGridLength() {
    model.startGame(grid, deck);

    model.playToGrid(4, 4, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelPlayToGridCardIdxIsGreaterOrEqualToHandSize() {
    model.startGame(grid, deck);

    model.playToGrid(0, 0, model.getPlayerOfColor(PlayerColor.RED).getHand().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelPlayToGridPlayingToAlreadyPlayedToCell() {
    model.startGame(grid, deck);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);


    model.playToGrid(0, 0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testModelPlayToGridCalledWhileNotInPlacingPhase() {
    model.startGame(grid, deck);

    model.playToGrid(0, 0, 0);


    model.playToGrid(0, 1, 0);
  }

  @Test
  public void testModelPlayToGridValidForFullGame() {
    model = playGameTilGameOver();

    boolean expectedIsGameOver = true;
    PlayerColor expectedFindWinningPlayer = PlayerColor.BLUE;

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
    model.startGame(grid, deck);

    model.playToGrid(0, 0, 0);
    model.battle(-1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelBattleRowOrColGreaterOrEqualToGridLength() {
    model.startGame(grid, deck);

    model.playToGrid(0, 0, 0);
    model.battle(4, 4);
  }

  @Test(expected = IllegalStateException.class)
  public void testModelBattleNotInBattlePhase() {
    model.startGame(grid, deck);

    model.battle(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelBattleRowAndColLeadToInvalidCard() {
    model.startGame(grid, deck);

    model.playToGrid(0, 0, 0);

    model.battle(1, 1);
  }

  @Test
  public void testModelBattleSideBySide() {
    model.startGame(grid, deck);

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
    model.startGame(grid, deck);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);


    model.playToGrid(0, 0, 0);
    model.battle(0, 0);


    PlayerColor expected = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testModelBattleBattleOccursRecursively() {
    model.startGame(grid, deck);

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
    model.startGame(grid, deck);

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
    model.startGame(grid, deck);

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

    PlayerColor expected = PlayerColor.BLUE;

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
    model.startGame(grid, deck);

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
    model.startGame(grid, deck);

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
    model.startGame(grid, deck);

    model.updatePlayerTurn();

    PlayerColor expected = PlayerColor.BLUE;

    Assert.assertEquals(expected, model.getCurrentTurnPlayer().getPlayersColor());
  }

  @Test
  public void testModelUpdatePlayerTurnGivenPlayerBlue() {
    model.startGame(grid, deck);

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
    model.startGame(grid, deck);

    PlayerColor expected = PlayerColor.RED;

    Assert.assertEquals(expected, model.getCurrentTurnPlayer().getPlayersColor());
  }

  @Test
  public void testModelFullGameValid() {
    File betterCardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/cardconfigs/randomized_card_configuration.txt");

    CardReader cardReader = new CardReader(betterCardConfig);

    deck = cardReader.readConfiguration();

    model.startGame(grid, deck);

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
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/cardconfigs/card_config_three_cards.txt");

    CardReader cardReader = new CardReader(cardConfigThreeCards);

    deck = cardReader.readConfiguration();

    model.startGame(grid, deck);
  }

  @Test
  public void testModelStartGameHandSizeWhenGreaterThanThree() {
    File gridConfigSizeOfTwenty = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_size_of_twenty.txt");

    GridReader gridReader = new GridReader(gridConfigSizeOfTwenty);

    grid = gridReader.readConfiguration();

    model.startGame(grid, deck);

    int expected = 5;

    Assert.assertEquals(expected, model.getCurrentTurnPlayer().getHand().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridInvarianceIsEnsured() {
    File gridConfigEvenCardCellCount = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_card_cells_even.txt");

    GridReader gridReader = new GridReader(gridConfigEvenCardCellCount);

    grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
  }

  @Test
  public void testModelBattleCornerEdge() {
    model.startGame(grid, deck);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    Assert.assertEquals(PlayerColor.BLUE, model.findWinningPlayer().getPlayersColor());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelAddViewListenerNull() {
    model.startGame(grid, deck);

    model.addViewListener(null);
  }

  @Test
  public void testModelAddViewListener() {
    model.startGame(grid, deck);
    final boolean[] updated = {false};

    class MockModelListener implements ThreeTriosModelListener {

      @Override
      public void modelWasUpdated() {
        updated[0] = true;
      }
    }

    ThreeTriosModelListener listener = new MockModelListener();
    model.addViewListener(listener);

    Assert.assertFalse(updated[0]);
  }

  @Test
  public void testModelAddTurnListener() {
    model.startGame(grid, deck);
    final boolean[] updated = {false};

    class MockAIListener implements AIPlayerListener {
      @Override
      public void performTurn(PlayerColor color) {
        updated[0] = true;
      }
    }

    AIPlayerListener listener = new MockAIListener();
    model.addAITurnListener(listener);

    Assert.assertFalse(updated[0]);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelAddTurnListenerNull() {
    model.startGame(grid, deck);

    model.addAITurnListener(null);
  }

  @Test
  public void testModelFindWinningPlayerScore() {
    model.startGame(grid, deck);

    int expected = 8;

    Assert.assertEquals(expected, model.findWinningPlayerScore());
  }

  @Test
  public void testModelFindWinningPlayerScoreInMiddleOfGame() {
    model.startGame(grid, deck);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(0, 1, 0);
    model.battle(0, 1);

    int expected = 9;

    Assert.assertEquals(expected, model.findWinningPlayerScore());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelSetAndGetBattleRuleNull() {
    model.startGame(grid, deck);

    model.setBattleRule(null);
  }

  @Test
  public void testModelSetAndGetBattleRuleNormal() {
    model.startGame(grid, deck);

    BattleRules normalRule = new NormalBattleRule();
    model.setBattleRule(normalRule);

    Assert.assertEquals(normalRule, model.getBattleRule());
  }

  @Test
  public void testModelSetAndGetBattleRuleSame() {
    model.startGame(grid, deck);

    BattleRules sameRule = new SameBattleRule();
    model.setBattleRule(sameRule);

    Assert.assertEquals(sameRule, model.getBattleRule());
  }

  @Test
  public void testModelSetAndGetBattleRulePlus() {
    model.startGame(grid, deck);

    BattleRules plusRule = new PlusBattleRule();
    model.setBattleRule(plusRule);

    Assert.assertEquals(plusRule, model.getBattleRule());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelSetAndGetBattleStrategyNull() {
    model.startGame(grid, deck);

    model.setBattleStrategy(null);
  }

  @Test
  public void testModelSetAndGetBattleStrategyNormal() {
    model.startGame(grid, deck);

    BattleStrategies normalRule = new NormalBattleStrategy();

    model.setBattleStrategy(normalRule);

    Assert.assertEquals(normalRule, model.getBattleStrategy());
  }

  @Test
  public void testModelSetAndGetBattleStrategyReverse() {
    model.startGame(grid, deck);

    BattleStrategies reverseRule = new ReverseBattleStrategy();

    model.setBattleStrategy(reverseRule);

    Assert.assertEquals(reverseRule, model.getBattleStrategy());
  }

  @Test
  public void testModelSetAndGetBattleStrategyFallenAce() {
    model.startGame(grid, deck);

    BattleStrategies fallenAceRule = new FallenAceBattleStrategy();

    model.setBattleStrategy(fallenAceRule);

    Assert.assertEquals(fallenAceRule, model.getBattleStrategy());
  }

  @Test
  public void testModelSetAndGetBattleStrategyReverseFallenAce() {
    model.startGame(grid, deck);

    BattleStrategies reverseFallenAceRule = new ReverseFallenAceBattleStrategy();

    model.setBattleStrategy(reverseFallenAceRule);

    Assert.assertEquals(reverseFallenAceRule, model.getBattleStrategy());
  }

  @Test
  public void testModelGetBattleStrategyOnStart() {
    model.startGame(grid, deck);

    Assert.assertTrue(model.getBattleStrategy() instanceof NormalBattleStrategy);
  }

  @Test
  public void testModelGetBattleRuleOnStart() {
    model.startGame(grid, deck);

    Assert.assertTrue(model.getBattleRule() instanceof NormalBattleRule);
  }
}