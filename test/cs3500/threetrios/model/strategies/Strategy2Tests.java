package cs3500.threetrios.model.strategies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.controller.filereader.GridReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.GridTile;

/**
 * Represents tests for strategy 2.
 */
public class Strategy2Tests {
  private ThreeTriosModel model;
  private File cardConfig;
  private File gridConfig;

  private Grid[][] grid;
  private List<Cards> deck;

  @Before
  public void setup() {
    this.model = new GameModel();
    this.cardConfig = new File("/Users/ayush/Desktop/ThreeTrios/src/cs3500/threetrios/" +
            "cardconfigs/randomized_card_configuration.txt");
    this.gridConfig = new File("/Users/ayush/Desktop/ThreeTrios/test/cs3500/threetrios/" +
            "gridconfigs/grid_config_large.txt");

    GridReader gridReader = new GridReader(this.gridConfig);
    CardReader cardReader = new CardReader(this.cardConfig);

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

}
