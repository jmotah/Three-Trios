package cs3500.threetrios.model.battlerules;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.controller.filereader.ConfigurationReader;
import cs3500.threetrios.controller.filereader.GridReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.battlestrategies.FallenAceBattleStrategy;
import cs3500.threetrios.model.battlestrategies.NormalBattleStrategy;
import cs3500.threetrios.model.battlestrategies.ReverseBattleStrategy;
import cs3500.threetrios.model.battlestrategies.ReverseFallenAceBattleStrategy;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.player.PlayerColor;

/**
 * Represents tests for the verifying the Same battle rule works as expected. Some tests are
 * performed alongside additional strategies to the rules.
 */
public class SameBattleRuleTest {

  private ThreeTriosModel model;

  private File cardConfig;
  private File gridConfig;

  private ConfigurationReader<List<Cards>> cardReader;
  private ConfigurationReader<Grid[][]> gridReader;

  @Before
  public void setup() {
    this.cardConfig = new File("/Users/julienmotaharian/Desktop/OOD Projects/" +
            "Group Projects/ThreeTriosBetter/src/cs3500/threetrios/cardconfigs/" +
            "randomized_card_configuration.txt");
    this.gridConfig = new File("/Users/julienmotaharian/Desktop/OOD Projects/" +
            "Group Projects/ThreeTriosBetter/src/cs3500/threetrios/gridconfigs/" +
            "grid_configuration.txt");

    this.cardReader = new CardReader(cardConfig);
    this.gridReader = new GridReader(gridConfig);

    this.model = new GameModel();
  }

  @Test
  public void testValidBattleWith2Sames() {
    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new NormalBattleStrategy());

    model.playToGrid(1, 1, 2);
    model.battle(1, 1);

    model.playToGrid(3, 0, 0);
    model.battle(3, 0);

    model.playToGrid(1, 3, 2);
    model.battle(1, 3);

    model.playToGrid(1, 2, 1);
    model.battle(1, 2);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][3].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testValidBattleWith1Same() {
    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new NormalBattleStrategy());

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    PlayerColor expectedTileColor = PlayerColor.RED;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testValidBattleWith4Same() {
    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new NormalBattleStrategy());

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    model.playToGrid(2, 2, 0);
    model.battle(2, 2);

    model.playToGrid(0, 0, 1);
    model.battle(0, 0);

    model.playToGrid(2, 0, 2);
    model.battle(2, 0);

    model.playToGrid(0, 1, 1);
    model.battle(0, 1);

    model.playToGrid(3, 1, 2);
    model.battle(3, 1);

    model.playToGrid(0, 2, 1);
    model.battle(0, 2);

    model.playToGrid(1, 1, 2);
    model.battle(1, 1);

    model.playToGrid(2, 1, 0);
    model.battle(2, 1);

    PlayerColor expectedTileColor = PlayerColor.RED;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[2][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[3][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[2][2].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testStrategyOverrideForInvalidBattle() {
    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new NormalBattleStrategy());

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(1, 2, 2);
    model.battle(1, 2);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testInvalidStrategyOverrideForInvalidBattle() {
    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new NormalBattleStrategy());

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(1, 2, 1);
    model.battle(1, 2);

    Assert.assertEquals(PlayerColor.RED,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testStrategyOverrideForComboStepAfterValidBattle() {
    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new NormalBattleStrategy());

    model.playToGrid(1, 0, 2);
    model.battle(1, 0);

    model.playToGrid(3, 0, 0);
    model.battle(3, 0);

    model.playToGrid(1, 2, 2);
    model.battle(1, 2);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    model.playToGrid(0, 0, 2);
    model.battle(0, 0);

    model.playToGrid(3, 2, 1);
    model.battle(3, 2);

    model.playToGrid(2, 2, 2);
    model.battle(2, 2);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(PlayerColor.RED,
            model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][2].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[2][2].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testReverseStrategyOverrideForComboStepAfterValidBattle() {
    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new ReverseBattleStrategy());

    model.playToGrid(1, 0, 2);
    model.battle(1, 0);

    model.playToGrid(3, 0, 0);
    model.battle(3, 0);

    model.playToGrid(1, 2, 2);
    model.battle(1, 2);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    model.playToGrid(0, 0, 2);
    model.battle(0, 0);

    model.playToGrid(3, 2, 1);
    model.battle(3, 2);

    model.playToGrid(2, 2, 1);
    model.battle(2, 2);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(PlayerColor.RED,
            model.getGrid()[0][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][0].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][2].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[2][2].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testFallenAceStrategyOverrideForComboStepAfterValidBattle() {
    this.cardConfig = new File("/Users/julienmotaharian/Desktop/OOD Projects/" +
            "Group Projects/ThreeTriosBetter/test/cs3500/threetrios/cardconfigs/" +
            "card_config_fallen_ace_tests.txt");
    this.gridConfig = new File("/Users/julienmotaharian/Desktop/OOD Projects/" +
            "Group Projects/ThreeTriosBetter/test/cs3500/threetrios/gridconfigs/" +
            "grid_config_large.txt");

    this.cardReader = new CardReader(cardConfig);
    this.gridReader = new GridReader(gridConfig);

    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new FallenAceBattleStrategy());

    model.playToGrid(1, 1, 5);
    model.battle(1, 1);

    model.playToGrid(5, 0, 0);
    model.battle(5, 0);

    model.playToGrid(1, 3, 0);
    model.battle(1, 3);

    model.playToGrid(5, 1, 1);
    model.battle(5, 1);

    model.playToGrid(0, 1, 5);
    model.battle(0, 1);

    model.playToGrid(5, 2, 1);
    model.battle(5, 2);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    model.playToGrid(1, 2, 14);
    model.battle(1, 2);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][3].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[2][3].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testReverseFallenAceStrategyOverrideForComboStepAfterValidBattle() {
    this.cardConfig = new File("/Users/julienmotaharian/Desktop/OOD Projects/" +
            "Group Projects/ThreeTriosBetter/test/cs3500/threetrios/cardconfigs/" +
            "card_config_fallen_ace_tests.txt");
    this.gridConfig = new File("/Users/julienmotaharian/Desktop/OOD Projects/" +
            "Group Projects/ThreeTriosBetter/test/cs3500/threetrios/gridconfigs/" +
            "grid_config_large.txt");

    this.cardReader = new CardReader(cardConfig);
    this.gridReader = new GridReader(gridConfig);

    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new ReverseFallenAceBattleStrategy());

    model.playToGrid(1, 1, 5);
    model.battle(1, 1);

    model.playToGrid(5, 0, 0);
    model.battle(5, 0);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    model.playToGrid(5, 1, 1);
    model.battle(5, 1);

    model.playToGrid(0, 1, 5);
    model.battle(0, 1);

    model.playToGrid(5, 2, 1);
    model.battle(5, 2);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][3].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[2][3].getWhichPlayersTile().getPlayersColor());
  }
}