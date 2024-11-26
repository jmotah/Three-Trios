import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.view.textual.TextualView;
import cs3500.threetrios.view.ThreeTriosView;

/**
 * An Examples class filled with quick code to references of how to play a ThreeTrios game. Examples
 * include a basic initialization of the game, how to play to the grid and battle, playing to the
 * end game, and displaying the textual view.
 */
public class Examples {
  private ThreeTriosModel model;
  private ThreeTriosView view;

  @Before
  public void setup() {
    model = new GameModel();
    view = new TextualView(model);

    File cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/cardconfigs/card_configuration.txt");
    File gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/gridconfigs/board_2x3.txt");

    model.startGame(cardConfig, gridConfig);
  }

  /**
   * Code to initialize the game while displaying a view.
   */
  @Test
  public void startGameWithView() {
    view.refresh();

    /* AssertEquals is needed for the handins grader */
    Assert.assertFalse(model.isGameOver());
  }

  /**
   * Code to start the game and play to the grid and battle while displaying a view.
   */
  @Test
  public void startGameAndPlayToGridAndBattleWithView() {
    model.playToGrid(0, 0, 0);
    model.battle(0, 0);

    view.refresh();

    /* AssertEquals is needed for the handins grader */
    Assert.assertFalse(model.isGameOver());
  }

  /**
   * Code to start the game and playing to end game while displaying a view.
   */
  @Test
  public void startGameAndPlayToEndGameWithView() {
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

    /* AssertEquals is needed for the handins grader */
    Assert.assertTrue(model.isGameOver());
  }
}