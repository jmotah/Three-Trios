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
    PlayerPlayerModel playerModel = new PlayerPlayerModel();
    PlayerComputerModel model = new PlayerComputerModel(playerModel);

    File cardConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/cardconfigs/card_configuration.txt");

    File gridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/src/" +
                    "cs3500/threetrios/gridconfigs/grid_configuration.txt");

//    model.startGame(cardConfig, gridConfig);
//
//    View view;
//
//    model.playToGrid(0, 0, 0);
//    model.battle(0, 0);
//
//    model.playToGrid(0, 1, 0);
//    model.battle(0, 1);
//
//    view = new GraphicalView(model);
//    view.makeVisible();
//
//    model.playToGrid(0, 2, 0);
//    model.battle(0, 2);
//    view.refresh();

    model.startGame(cardConfig, gridConfig);
    View view = new GraphicalView(model);
    view.makeVisible();

    List<HashMap<Point, Integer>> findScoreForAllCardsInAllPossibleSpaces = model.emulateBattleToFindScoreForAllCardsInAllPossibleSpaces();
    System.out.println(model.getBestScoreForAllCardsInHand(findScoreForAllCardsInAllPossibleSpaces));
    System.out.println(model.getBestScorePositionForAllCardsInHand(findScoreForAllCardsInAllPossibleSpaces));
    System.out.println(findScoreForAllCardsInAllPossibleSpaces);

  }
}