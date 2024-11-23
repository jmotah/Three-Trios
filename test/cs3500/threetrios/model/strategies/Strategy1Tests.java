package cs3500.threetrios.model.strategies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.io.File;
import java.util.HashMap;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Represents tests for strategy 1.
 */
public class Strategy1Tests {
  private ThreeTriosModel model;
  private File cardConfig;
  private File gridConfig;

  @Before
  public void setup() {
    this.model = new GameModel();
    this.cardConfig = new File(
            "/Users/ayush/Desktop/ThreeTrios/src/cs3500/threetrios/cardconfigs/" +
                    "randomized_card_configuration.txt");
    this.gridConfig = new File("/Users/ayush/Desktop/ThreeTrios/test/cs3500/threetrios/" +
            "gridconfigs/grid_config_large.txt");
  }

  @Test
  public void testStrategyOnePlacingCardDecidingBetweenTwoPositions() {
    this.cardConfig = new File("/Users/ayush/Desktop/ThreeTrios/test/cs3500/threetrios/" +
            "cardconfigs/card_config_standard.txt");
    this.gridConfig = new File("/Users/ayush/Desktop/ThreeTrios/test/cs3500/threetrios/" +
            "gridconfigs/grid_config_getBestScorePositionForAllCardsInHand.txt");

    this.model.startGame(this.cardConfig, this.gridConfig);
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
    this.model.startGame(this.cardConfig, this.gridConfig);
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
    this.model.startGame(this.cardConfig, this.gridConfig);
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
    this.model.startGame(this.cardConfig, this.gridConfig);
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
}