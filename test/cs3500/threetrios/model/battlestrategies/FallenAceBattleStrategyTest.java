package cs3500.threetrios.model.battlestrategies;

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
import cs3500.threetrios.model.battlerules.NormalBattleRule;
import cs3500.threetrios.model.battlerules.NormalBattleRuleTest;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.player.PlayerColor;

public class FallenAceBattleStrategyTest {

  private ThreeTriosModel model;

  @Before
  public void setup() {
    File cardConfig = new File("/Users/julienmotaharian/Desktop/OOD Projects/" +
            "Group Projects/ThreeTriosBetter/test/cs3500/threetrios/cardconfigs/" +
            "card_config_fallen_ace_tests.txt");
    File gridConfig = new File("/Users/julienmotaharian/Desktop/OOD Projects/" +
            "Group Projects/ThreeTriosBetter/src/cs3500/threetrios/gridconfigs/" +
            "grid_configuration.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    List<Cards> deck = cardReader.readConfiguration();
    Grid[][] grid = gridReader.readConfiguration();

    this.model = new GameModel();

    model.startGame(grid, deck);
    model.setBattleRule(new NormalBattleRule());
    model.setBattleStrategy(new FallenAceBattleStrategy());
  }

  @Test
  public void testValidBattle() {
    model.playToGrid(1, 1, 1);
    model.battle(1, 1);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testValidBattleWithCombo() {
    model.playToGrid(1, 1, 1);
    model.battle(1, 1);

    model.playToGrid(0, 3, 0);
    model.battle(0, 3);

    model.playToGrid(2, 2, 1);
    model.battle(2, 2);

    model.playToGrid(2, 1, 2);
    model.battle(2, 1);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(expectedTileColor,
            model.getGrid()[2][2].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testInvalidBattle() {
    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(1, 2, 1);
    model.battle(1, 2);

    PlayerColor expectedTileColor = PlayerColor.RED;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testInvalidBattleSameAttackValue() {
    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    PlayerColor expectedTileColor = PlayerColor.RED;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testValidBattleAllFourCorners() {
    model.playToGrid(2, 0, 1);
    model.battle(2, 0);

    model.playToGrid(0, 0, 1);
    model.battle(0, 0);

    model.playToGrid(3, 1, 2);
    model.battle(3, 1);

    model.playToGrid(0, 2, 4);
    model.battle(0, 2);

    model.playToGrid(2, 2, 2);
    model.battle(2, 2);

    model.playToGrid(0, 3, 2);
    model.battle(0, 3);

    model.playToGrid(1, 1, 2);
    model.battle(1, 1);

    model.playToGrid(2, 1, 0);
    model.battle(2, 1);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[2][0].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testValidBattleOneSideAndInvalidOpposite() {
    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 3, 0);
    model.battle(1, 3);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    Assert.assertEquals(PlayerColor.RED,
            model.getGrid()[1][1].getWhichPlayersTile().getPlayersColor());
    Assert.assertEquals(PlayerColor.BLUE,
            model.getGrid()[1][3].getWhichPlayersTile().getPlayersColor());
  }

  @Test
  public void testValidBattleInCorner() {
    model.playToGrid(0, 1, 1);
    model.battle(0, 1);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    PlayerColor expectedTileColor = PlayerColor.BLUE;

    Assert.assertEquals(expectedTileColor,
            model.getGrid()[0][1].getWhichPlayersTile().getPlayersColor());
  }
}