package cs3500.threetrios;

import java.awt.*;
import java.util.List;
import java.io.File;
import java.util.HashMap;

import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.playervscomputer.PlayerComputerModel;
import cs3500.threetrios.model.playervsplayer.PlayerPlayerModel;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.View;
import cs3500.threetrios.view.graphical.GraphicalView;
import cs3500.threetrios.view.textual.TextualView;

/**
 * The primary class to start the ThreeTrios game.
 */
public class ThreeTriosGame {
  /**
   * The main method to run a ThreeTrios game. Initializes the game model and view while reading
   * the card configuration and grid configuration file to initialize. Simulates the entire game.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    PlayerComputerModel model = new PlayerComputerModel();

    File cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/cardconfigs/card_configuration.txt");

    File gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/gridconfigs/grid_configuration.txt");

    model.startGame(cardConfig, gridConfig);

    View view = new GraphicalView(model);
    view.makeVisible();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    model.playToGridAI();
    view.refresh();

    System.out.println(model.emulateBattleToFindScoreForAllCardsInAllPossibleSpaces());
    System.out.println(model.getBestScorePositionForAllCardsInHand());
  }
}