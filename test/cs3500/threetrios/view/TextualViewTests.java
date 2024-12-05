package cs3500.threetrios.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.List;

import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.controller.filereader.GridReader;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.view.textual.TextualView;

/**
 * Represents tests for the TextualView class. Verifies all methods work as intended and the
 * textual view that is outputted is correct in all scenarios.
 */
public class TextualViewTests {
  private ThreeTriosModel model;

  private Grid[][] grid;
  private List<Cards> deck;

  //This is used to test the void render() method. Captures the output sent through
  //System.out
  private final ByteArrayOutputStream output = new ByteArrayOutputStream();

  @Before
  public void setup() {
    this.model = new GameModel();
    File cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/" +
                    "ThreeTriosBetter/src/cs3500/threetrios/cardconfigs/card_configuration.txt");
    File gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/" +
                    "ThreeTriosBetter/src/cs3500/threetrios/gridconfigs/board_2x3.txt");

    GridReader gridReader = new GridReader(gridConfig);
    CardReader cardReader = new CardReader(cardConfig);

    grid = gridReader.readConfiguration();
    deck = cardReader.readConfiguration();

    //This is used to test the void render() method. Captures the output sent through
    //System.out
    System.setOut(new PrintStream(output));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTextualViewConstructorNullModel() {
    ThreeTriosView view = new TextualView(null);
  }

  @Test
  public void testTextualViewConstructorValid() {
    ThreeTriosView view = new TextualView(model);

    Assert.assertNotEquals(null, view);
  }

  @Test
  public void testTextualViewToStringAfterStartGame() {
    ThreeTriosView view = new TextualView(model);
    model.startGame(grid, deck);

    String expected = "Player: RED\n" +
            "___\n" +
            "__X\n" +
            "Hand:\n" +
            "OnesCard 1 1 1 1\n" +
            "ThreesCard 3 3 3 3\n" +
            "FivesCard 5 5 5 5\n\n";

    Assert.assertEquals(expected, view.toString());
  }

  @Test
  public void testTextualViewToStringAfterAMove() {
    ThreeTriosView view = new TextualView(model);
    model.startGame(grid, deck);

    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    String expected = "Player: BLUE\n" +
            "R__\n" +
            "__X\n" +
            "Hand:\n" +
            "TwosCard 2 2 2 2\n" +
            "FoursCard 4 4 4 4\n" +
            "SixesCard 6 6 6 6\n" +
            "\n";

    Assert.assertEquals(expected, view.toString());
  }

  @Test
  public void testTextualViewToStringAfterBattle() {
    ThreeTriosView view = new TextualView(model);
    model.startGame(grid, deck);
    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    model.playToGrid(0, 1, 0);
    model.battle(0, 1);

    String expected = "Player: RED\n" +
            "BB_\n" +
            "__X\n" +
            "Hand:\n" +
            "ThreesCard 3 3 3 3\n" +
            "FivesCard 5 5 5 5\n" +
            "\n";

    Assert.assertEquals(expected, view.toString());
  }

  @Test
  public void testTextualViewRenderBeforeGameOver() {
    ThreeTriosView view = new TextualView(model);
    model.startGame(grid, deck);

    view.refresh();

    String expected = "Player: RED\n" +
            "___\n" +
            "__X\n" +
            "Hand:\n" +
            "OnesCard 1 1 1 1\n" +
            "ThreesCard 3 3 3 3\n" +
            "FivesCard 5 5 5 5\n" +
            "\n";

    Assert.assertEquals(expected, output.toString());
  }

  @Test
  public void testTextualViewRenderAfterGameOver() {
    ThreeTriosView view = new TextualView(model);
    model.startGame(grid, deck);

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

    view.refresh();

    String expected = "Winning Player: RED\n" +
            "RRR\n" +
            "RRX\n" +
            "GAME OVER!\n";

    Assert.assertEquals(expected, output.toString());
  }
}