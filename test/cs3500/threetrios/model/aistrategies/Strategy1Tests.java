package cs3500.threetrios.model.aistrategies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.io.File;
import java.lang.module.Configuration;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.controller.filereader.ConfigurationReader;
import cs3500.threetrios.controller.filereader.GridReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.battlerules.NormalBattleRule;
import cs3500.threetrios.model.battlerules.PlusBattleRule;
import cs3500.threetrios.model.battlerules.SameBattleRule;
import cs3500.threetrios.model.battlestrategies.FallenAceBattleStrategy;
import cs3500.threetrios.model.battlestrategies.NormalBattleStrategy;
import cs3500.threetrios.model.battlestrategies.ReverseBattleStrategy;
import cs3500.threetrios.model.battlestrategies.ReverseFallenAceBattleStrategy;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.Grid;

/**
 * Represents tests for strategy 1.
 */
public class Strategy1Tests {
  private ThreeTriosModel model;
  private File cardConfig;
  private File gridConfig;

  private Grid[][] grid;
  private List<Cards> deck;

  @Before
  public void setup() {
    this.model = new GameModel();
    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/" +
                    "randomized_card_configuration.txt");
    this.gridConfig = new File("test/cs3500/threetrios/" +
            "gridconfigs/grid_config_large.txt");

    GridReader gridReader = new GridReader(this.gridConfig);
    CardReader cardReader = new CardReader(this.cardConfig);

    grid = gridReader.readConfiguration();
    deck = cardReader.readConfiguration();
  }

  @Test
  public void testStrategyOnePlacingCardDecidingBetweenTwoPositions() {
    this.cardConfig = new File("/Users/ayush/Desktop/ThreeTrios/test/cs3500/threetrios/" +
            "cardconfigs/card_config_standard.txt");
    this.gridConfig = new File("/Users/ayush/Desktop/ThreeTrios/test/cs3500/threetrios/" +
            "gridconfigs/grid_config_getBestScorePositionForAllCardsInHand.txt");

    GridReader gridReader = new GridReader(gridConfig);
    CardReader cardReader = new CardReader(cardConfig);

    grid = gridReader.readConfiguration();
    deck = cardReader.readConfiguration();

    this.model.startGame(grid, deck);
    Strategies strategyOne = new Strategy1(this.model);

    this.model.playToGrid(0, 0, 0);
    this.model.battle(0, 0);

    this.model.playToGrid(0, 4, 0);
    this.model.battle(0, 4);

    this.model.playToGrid(1, 0, 0);
    this.model.battle(1, 0);

    this.model.playToGrid(1, 4, 0);
    this.model.battle(1, 4);

    this.model.playToGrid(0, 2, 0);
    this.model.battle(0, 2);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(0, x);
    Assert.assertEquals(1, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testStrategyOneOpenGridPicksCorrectPosition() {
    this.model.startGame(grid, deck);
    Strategies strategyOne = new Strategy1(this.model);

    this.model.playToGrid(1, 1, 0);
    this.model.battle(1, 1);

    this.model.playToGrid(1, 5, 0);
    this.model.battle(1, 5);

    this.model.playToGrid(3, 1, 0);
    this.model.battle(3, 1);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = (Integer) optimalMove.get(position);

    Assert.assertEquals(0, x);
    Assert.assertEquals(1, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testStrategyOneOpenGridCornerAndNotFirstHandIndex() {
    this.model.startGame(grid, deck);
    Strategies strategyOne = new Strategy1(this.model);

    this.model.playToGrid(0, 4, 0);
    this.model.battle(0, 4);

    this.model.playToGrid(4, 3, 0);
    this.model.battle(4, 3);

    this.model.playToGrid(0, 1, 0);
    this.model.battle(0, 1);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
    Assert.assertEquals(1, cardIdx);
  }

  @Test
  public void testStrategyOneComboStep() {
    this.model.startGame(grid, deck);
    Strategies strategyOne = new Strategy1(this.model);

    this.model.playToGrid(0, 0, 0);
    this.model.battle(0, 0);

    this.model.playToGrid(0, 1, 0);
    this.model.battle(0, 1);

    this.model.playToGrid(0, 2, 0);
    this.model.battle(0, 2);

    this.model.playToGrid(0, 3, 1);
    this.model.battle(0, 3);

    this.model.playToGrid(0, 4, 0);
    this.model.battle(0, 4);

    this.model.playToGrid(0, 5, 0);
    this.model.battle(0, 5);

    this.model.playToGrid(1, 3, 1);
    this.model.battle(1, 3);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);
    Assert.assertEquals(1, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(1, cardIdx);
  }

  @Test
  public void testNormalStrategyPriority() {
    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/card_configuration.txt");
    this.gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new NormalBattleRule());
    model.setBattleStrategy(new NormalBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    model.playToGrid(2, 2, 0);
    model.battle(2, 2);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(2, x);
    Assert.assertEquals(1, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testReverseStrategyPriority() {
    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/card_configuration.txt");
    this.gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new NormalBattleRule());
    model.setBattleStrategy(new ReverseBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 4);
    model.battle(1, 1);

    model.playToGrid(1, 2, 3);
    model.battle(1, 2);

    model.playToGrid(1, 3, 2);
    model.battle(1, 3);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(3, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testFallenAceStrategyPriority() {
    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/card_configuration.txt");
    this.gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new NormalBattleRule());
    model.setBattleStrategy(new FallenAceBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 1);
    model.battle(1, 1);

    model.playToGrid(1, 2, 4);
    model.battle(1, 2);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testReverseFallenAceStrategyPriority() {
    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/card_configuration.txt");
    this.gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new NormalBattleRule());
    model.setBattleStrategy(new ReverseFallenAceBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 1);
    model.battle(1, 1);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(3, cardIdx);
  }

  @Test
  public void testSameBattleRule() {
    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/randomized_card_configuration.txt");
    this.gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new NormalBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(1, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testSameBattleRuleConsultsReverseStrategies() {
    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/randomized_card_configuration.txt");
    this.gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new ReverseBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    model.playToGrid(3, 1, 1);
    model.battle(3, 1);

    model.playToGrid(0, 2, 0);
    model.battle(0, 2);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(1, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testSameBattleRuleConsultsFallenAceStrategy() {
    this.cardConfig = new File(
            "test/cs3500/threetrios/cardconfigs/card_config_fallen_ace_tests.txt");
    this.gridConfig = new File(
            "test/cs3500/threetrios/gridconfigs/grid_config_large.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new FallenAceBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(3, 0, 0);
    model.battle(3, 0);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    model.playToGrid(3, 2, 0);
    model.battle(3, 2);

    model.playToGrid(5, 4, 9);
    model.battle(5, 4);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(1, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testSameBattleRuleConsultsReverseFallenAceStrategy() {
    this.cardConfig = new File(
            "test/cs3500/threetrios/cardconfigs/card_config_fallen_ace_tests.txt");
    this.gridConfig = new File(
            "test/cs3500/threetrios/gridconfigs/grid_config_large.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new SameBattleRule());
    model.setBattleStrategy(new ReverseFallenAceBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 1, 1);
    model.battle(1, 1);

    model.playToGrid(3, 1, 2);
    model.battle(3, 1);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    model.playToGrid(3, 2, 2);
    model.battle(3, 2);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(1, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(3, cardIdx);
  }

  @Test
  public void testPlusBattleRule() {
    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/randomized_card_configuration.txt");
    this.gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new PlusBattleRule());
    model.setBattleStrategy(new NormalBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 3);
    model.battle(1, 1);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 3, 4);
    model.battle(1, 3);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(1, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testPlusBattleRuleConsultsReverseStrategy() {
    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/randomized_card_configuration.txt");
    this.gridConfig = new File(
            "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new PlusBattleRule());
    model.setBattleStrategy(new ReverseBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    model.playToGrid(3, 1, 1);
    model.battle(3, 1);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(1, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testPlusBattleRuleConsultsFallenAceStrategy() {
    this.cardConfig = new File(
            "test/cs3500/threetrios/cardconfigs/card_config_fallen_ace_tests.txt");
    this.gridConfig = new File(
            "test/cs3500/threetrios/gridconfigs/grid_config_large.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new PlusBattleRule());
    model.setBattleStrategy(new FallenAceBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(3, 0, 0);
    model.battle(3, 0);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    model.playToGrid(3, 2, 0);
    model.battle(3, 2);

    model.playToGrid(5, 3, 9);
    model.battle(5, 3);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(1, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testPlusBattleRuleConsultsReverseFallenAceStrategy() {
    this.cardConfig = new File(
            "test/cs3500/threetrios/cardconfigs/card_config_fallen_ace_tests.txt");
    this.gridConfig = new File(
            "test/cs3500/threetrios/gridconfigs/grid_config_large.txt");

    ConfigurationReader<List<Cards>> cardReader = new CardReader(cardConfig);
    ConfigurationReader<Grid[][]> gridReader = new GridReader(gridConfig);

    this.deck = cardReader.readConfiguration();
    this.grid = gridReader.readConfiguration();

    model.startGame(grid, deck);
    model.setBattleRule(new PlusBattleRule());
    model.setBattleStrategy(new ReverseFallenAceBattleStrategy());

    Strategies strategyOne = new Strategy1(this.model);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 1, 1);
    model.battle(1, 1);

    model.playToGrid(3, 1, 2);
    model.battle(3, 1);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    model.playToGrid(3, 2, 0);
    model.battle(3, 2);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    HashMap<Point, Integer> optimalMove = strategyOne.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(1, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(0, cardIdx);
  }
}