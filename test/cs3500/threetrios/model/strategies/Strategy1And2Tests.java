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
 * Tests to verify the chained version of strategies 1 and 2 works as intended.
 */
public class Strategy1And2Tests {
  ThreeTriosModel model;

  Strategies strategy;

  File cardConfig;
  File gridConfig;

  private Grid[][] grid;
  private List<Cards> deck;

  @Before
  public void setup() {
    model = new GameModel();
    strategy = new Strategy1And2(model);

    this.cardConfig = new File(
            "src/cs3500/threetrios/cardconfigs/randomized_card_configuration.txt");
    this.gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/" +
                    "src/cs3500/threetrios/gridconfigs/grid_configuration.txt");

    GridReader gridReader = new GridReader(this.gridConfig);
    CardReader cardReader = new CardReader(this.cardConfig);

    grid = gridReader.readConfiguration();
    deck = cardReader.readConfiguration();
  }

  @Test
  public void testPlayToGridAIInitialMove() {
    model.startGame(grid, deck);

    HashMap<Point, Integer> expected = new HashMap<Point, Integer>();
    expected.put(new Point(0, 0), 0);

    Assert.assertEquals(expected, strategy.runStrategy());
  }

  @Test
  public void testPlayToGridAIGoForHighestScore() {
    model.startGame(grid, deck);

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

    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(1, 2), 0);

    Assert.assertEquals(expected, strategy.runStrategy());
  }

  @Test
  public void testPlayToGridAIGoForUpmostWhenPotentialScoresAreEqual() {
    model.startGame(grid, deck);

    model.playToGrid(1, 1, 6);
    model.battle(1, 1);

    model.playToGrid(0, 3, 0);
    model.battle(0, 3);

    model.playToGrid(2, 1, 5);
    model.battle(2, 1);

    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(0, 1), 0);

    Assert.assertEquals(expected, strategy.runStrategy());
  }

  @Test
  public void testPlayToGridGoForLeftmostWhenPotentialScoresAreEqual() {
    gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_large.txt");

    GridReader gridReader = new GridReader(gridConfig);

    grid = gridReader.readConfiguration();

    model.startGame(grid, deck);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(5, 0, 0);
    model.battle(5, 0);

    model.playToGrid(0, 5, 0);
    model.battle(0, 5);

    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(0, 1), 1);

    Assert.assertEquals(expected, strategy.runStrategy());
  }

  @Test
  public void testPlayToGridAIGoForCornerWhenPotentialScoresAreEqual() {
    model.startGame(grid, deck);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(3, 1, 0);
    model.battle(3, 1);

    model.playToGrid(2, 3, 0);
    model.battle(2, 3);

    model.playToGrid(1, 2, 0);
    model.battle(1, 2);

    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(3, 0), 0);

    Assert.assertEquals(expected, strategy.runStrategy());
  }

  @Test
  public void testPlayToGridAIGoForCornerUpperCornerPriority() {
    gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_large.txt");

    GridReader gridReader = new GridReader(gridConfig);

    grid = gridReader.readConfiguration();

    model.startGame(grid, deck);

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

    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(0, 0), 0);

    Assert.assertEquals(expected, strategy.runStrategy());
  }

  @Test
  public void testPlayToGridAIGoForCornerUpperCornerPriority2() {
    gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_large.txt");

    GridReader gridReader = new GridReader(gridConfig);

    grid = gridReader.readConfiguration();

    model.startGame(grid, deck);

    model.playToGrid(0, 3, 0);
    model.battle(0, 3);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(0, 4, 4);
    model.battle(0, 4);

    model.playToGrid(1, 0, 0);
    model.battle(1, 0);

    model.playToGrid(4, 1, 0);
    model.battle(4, 1);

    model.playToGrid(2, 0, 0);
    model.battle(2, 0);

    model.playToGrid(5, 1, 0);
    model.battle(5, 1);

    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(0, 5), 10);

    Assert.assertEquals(expected, strategy.runStrategy());
  }

  @Test
  public void testPlayToGridAIGoForCornerLeftmostCornerPriority() {
    gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_large.txt");

    GridReader gridReader = new GridReader(gridConfig);

    grid = gridReader.readConfiguration();

    model.startGame(grid, deck);

    model.playToGrid(2, 0, 5);
    model.battle(2, 0);

    model.playToGrid(5, 0, 0);
    model.battle(5, 0);

    model.playToGrid(1, 0, 2);
    model.battle(1, 0);

    model.playToGrid(5, 1, 0);
    model.battle(5, 1);

    model.playToGrid(2, 5, 4);
    model.battle(2, 5);

    model.playToGrid(5, 2, 0);
    model.battle(5, 2);

    model.playToGrid(1, 5, 0);
    model.battle(1, 5);

    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(0, 0), 1);

    Assert.assertEquals(expected, strategy.runStrategy());
  }
}