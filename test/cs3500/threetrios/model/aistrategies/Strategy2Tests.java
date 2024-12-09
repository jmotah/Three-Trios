package cs3500.threetrios.model.aistrategies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.io.File;
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
 * Represents tests for strategy 2.
 */
public class Strategy2Tests {
  private ThreeTriosModel model;

  private Grid[][] grid;
  private List<Cards> deck;

  private File cardConfig;
  private File gridConfig;

  @Before
  public void setup() {
    this.model = new GameModel();
    this.cardConfig = new File("src/cs3500/threetrios/" +
            "cardconfigs/randomized_card_configuration.txt");
    this.gridConfig = new File("test/cs3500/threetrios/" +
            "gridconfigs/grid_config_large.txt");

    GridReader gridReader = new GridReader(gridConfig);
    CardReader cardReader = new CardReader(cardConfig);

    grid = gridReader.readConfiguration();
    deck = cardReader.readConfiguration();
  }

  @Test
  public void testStrategyTwoCornerAndBigCard() {
    this.model.startGame(grid, deck);
    Strategies strategyTwo = new Strategy2(this.model);

    this.model.playToGrid(1, 0, 0);
    this.model.battle(1, 0);

    this.model.playToGrid(0, 1, 0);
    this.model.battle(0, 1);

    this.model.playToGrid(0, 2, 0);
    this.model.battle(0, 2);

    HashMap<Point, Integer> optimalMove = strategyTwo.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(0L, x);
    Assert.assertEquals(0L, y);
    Assert.assertEquals(12L, cardIdx);
  }

  @Test
  public void testStrategyTwoOpenGrid() {
    this.model.startGame(grid, deck);
    Strategies strategyTwo = new Strategy2(this.model);

    this.model.playToGrid(1, 1, 0);
    this.model.battle(1, 1);

    this.model.playToGrid(1, 5, 0);
    this.model.battle(1, 5);

    this.model.playToGrid(3, 1, 0);
    this.model.battle(3, 1);

    HashMap<Point, Integer> optimalMove = strategyTwo.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
    Assert.assertEquals(12, cardIdx);
  }

  @Test
  public void testStrategyTwoTopLeftOccupied() {
    this.model.startGame(grid, deck);
    Strategies strategyTwo = new Strategy2(this.model);

    this.model.playToGrid(0, 0, 0);
    this.model.battle(0, 0);

    this.model.playToGrid(0, 1, 0);
    this.model.battle(0, 1);

    this.model.playToGrid(1, 0, 0);
    this.model.battle(1, 0);

    this.model.playToGrid(1, 1, 0);
    this.model.battle(1, 1);

    this.model.playToGrid(4, 5, 0);
    this.model.battle(4, 5);

    HashMap<Point, Integer> optimalMove = strategyTwo.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(0, x);
    Assert.assertEquals(5, y);
    Assert.assertEquals(8, cardIdx);
  }

  @Test
  public void testStrategyTwoTopCornersOccupied() {
    this.model.startGame(grid, deck);
    Strategies strategyTwo = new Strategy2(this.model);

    this.model.playToGrid(0, 0, 0);
    this.model.battle(0, 0);

    this.model.playToGrid(0, 5, 0);
    this.model.battle(0, 5);

    this.model.playToGrid(3, 3, 0);
    this.model.battle(3, 3);

    HashMap<Point, Integer> optimalMove = strategyTwo.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(5, x);
    Assert.assertEquals(0, y);
    Assert.assertEquals(10, cardIdx);
  }

  @Test
  public void testStrategyTwoTopRightCornerOccupied() {
    this.model.startGame(grid, deck);
    Strategies strategyTwo = new Strategy2(this.model);

    this.model.playToGrid(0, 5, 0);
    this.model.battle(0, 5);

    HashMap<Point, Integer> optimalMove = strategyTwo.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
    Assert.assertEquals(13, cardIdx);
  }

  @Test
  public void testStrategyTwoAllCornersOccupied() {
    this.model.startGame(grid, deck);
    Strategies strategyTwo = new Strategy2(this.model);

    this.model.playToGrid(0, 0, 0);
    this.model.battle(0, 0);

    this.model.playToGrid(0, 5, 0);
    this.model.battle(0, 5);

    this.model.playToGrid(5, 0, 0);
    this.model.battle(5, 0);

    HashMap<Point, Integer> optimalMove = strategyTwo.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(0, x);
    Assert.assertEquals(1, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testStrategyTwoAllCornersClusterOccupied() {
    this.model.startGame(grid, deck);
    Strategies strategyTwo = new Strategy2(this.model);

    this.model.playToGrid(0, 0, 0);
    this.model.battle(0, 0);

    this.model.playToGrid(0, 5, 0);
    this.model.battle(0, 5);

    this.model.playToGrid(5, 0, 0);
    this.model.battle(5, 0);

    this.model.playToGrid(0, 1, 0);
    this.model.battle(0, 1);

    this.model.playToGrid(1, 1, 0);
    this.model.battle(1, 1);

    this.model.playToGrid(1, 0, 0);
    this.model.battle(1, 0);

    this.model.playToGrid(0, 4, 0);
    this.model.battle(0, 4);

    this.model.playToGrid(1, 4, 0);
    this.model.battle(1, 4);

    this.model.playToGrid(1, 5, 0);
    this.model.battle(1, 5);

    this.model.playToGrid(4, 0, 0);
    this.model.battle(4, 0);

    this.model.playToGrid(4, 1, 0);
    this.model.battle(4, 1);

    this.model.playToGrid(5, 1, 0);
    this.model.battle(5, 1);

    this.model.playToGrid(4, 5, 0);
    this.model.battle(4, 5);

    this.model.playToGrid(4, 4, 0);
    this.model.battle(4, 4);

    this.model.playToGrid(5, 4, 0);
    this.model.battle(5, 4);

    HashMap<Point, Integer> optimalMove = strategyTwo.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(0, x);
    Assert.assertEquals(2, y);
    Assert.assertEquals(0, cardIdx);
  }

  @Test
  public void testStrategyTwoAllCornersClusterOccupiedAndExtraInTopLeft() {
    this.model.startGame(grid, deck);
    Strategies strategyTwo = new Strategy2(this.model);

    this.model.playToGrid(0, 0, 0);
    this.model.battle(0, 0);

    this.model.playToGrid(0, 5, 0);
    this.model.battle(0, 5);

    this.model.playToGrid(5, 0, 0);
    this.model.battle(5, 0);

    this.model.playToGrid(0, 1, 0);
    this.model.battle(0, 1);

    this.model.playToGrid(1, 1, 0);
    this.model.battle(1, 1);

    this.model.playToGrid(1, 0, 0);
    this.model.battle(1, 0);

    this.model.playToGrid(0, 4, 0);
    this.model.battle(0, 4);

    this.model.playToGrid(1, 4, 0);
    this.model.battle(1, 4);

    this.model.playToGrid(1, 5, 0);
    this.model.battle(1, 5);

    this.model.playToGrid(4, 0, 0);
    this.model.battle(4, 0);

    this.model.playToGrid(4, 1, 0);
    this.model.battle(4, 1);

    this.model.playToGrid(5, 1, 0);
    this.model.battle(5, 1);

    this.model.playToGrid(4, 5, 0);
    this.model.battle(4, 5);

    this.model.playToGrid(4, 4, 0);
    this.model.battle(4, 4);

    this.model.playToGrid(5, 4, 0);
    this.model.battle(5, 4);

    this.model.playToGrid(0, 2, 0);
    this.model.battle(0, 2);

    this.model.playToGrid(5, 3, 0);
    this.model.battle(5, 3);

    HashMap<Point, Integer> optimalMove = strategyTwo.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    Assert.assertEquals(0, x);
    Assert.assertEquals(3, y);
    Assert.assertEquals(0, cardIdx);
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

    Strategies strategy2 = new Strategy2(this.model);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    model.playToGrid(2, 2, 0);
    model.battle(2, 2);

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
    Assert.assertEquals(3, cardIdx);
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

    Strategies strategy2 = new Strategy2(this.model);

    model.playToGrid(2, 1, 2);
    model.battle(2, 1);

    model.playToGrid(0, 0, 1);
    model.battle(0, 0);

    model.playToGrid(3, 1, 1);
    model.battle(3, 1);

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(3, y);
    Assert.assertEquals(3, cardIdx);
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

    Strategies strategy2 = new Strategy2(this.model);

    model.playToGrid(2, 1, 1);
    model.battle(2, 1);

    model.playToGrid(2, 2, 4);
    model.battle(2, 2);

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
    Assert.assertEquals(3, cardIdx);
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

    Strategies strategy2 = new Strategy2(this.model);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(2, 1, 0);
    model.battle(2, 1);

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(3, y);
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

    Strategies strategy2 = new Strategy2(this.model);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
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

    Strategies strategy2 = new Strategy2(this.model);

    model.playToGrid(1, 1, 0);
    model.battle(1, 1);

    model.playToGrid(3, 0, 1);
    model.battle(3, 0);

    model.playToGrid(1, 3, 1);
    model.battle(1, 3);

    model.playToGrid(3, 1, 1);
    model.battle(3, 1);

    model.playToGrid(2, 3, 2);
    model.battle(2, 3);

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
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

    Strategies strategy2 = new Strategy2(this.model);

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

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
    Assert.assertEquals(13, cardIdx);
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

    Strategies strategy2 = new Strategy2(this.model);

    model.playToGrid(4, 0, 2);
    model.battle(4, 0);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(5, 1, 1);
    model.battle(5, 1);

    model.playToGrid(0, 1, 0);
    model.battle(0, 1);

    model.playToGrid(5, 2, 0);
    model.battle(5, 2);

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(5, y);
    Assert.assertEquals(0, cardIdx);
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

    Strategies strategy2 = new Strategy2(this.model);

    model.playToGrid(2, 0, 3);
    model.battle(2, 0);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(3, 1, 2);
    model.battle(3, 1);

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(3, y);
    Assert.assertEquals(1, cardIdx);
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

    Strategies strategy2 = new Strategy2(this.model);

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

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
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

    Strategies strategy2 = new Strategy2(this.model);

    model.playToGrid(1, 3, 0);
    model.battle(1, 3);

    model.playToGrid(3, 0, 0);
    model.battle(3, 0);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    model.playToGrid(0, 2, 5);
    model.battle(0, 2);

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
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

    Strategies strategy2 = new Strategy2(this.model);

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

    HashMap<Point, Integer> optimalMove = strategy2.runStrategy();
    Point position = optimalMove.keySet().iterator().next();
    int x = (int) position.getX();
    int y = (int) position.getY();
    int cardIdx = optimalMove.get(position);

    System.out.println("X:" + x + " Y:" + y + " cardIdx:" + cardIdx);

    Assert.assertEquals(0, x);
    Assert.assertEquals(0, y);
    Assert.assertEquals(2, cardIdx);
  }
}