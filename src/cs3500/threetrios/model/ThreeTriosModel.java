package cs3500.threetrios.model;

import java.io.File;

import cs3500.threetrios.controller.ThreeTriosModelListener;
import cs3500.threetrios.model.player.AIPlayerListener;

/**
 * Represents a generic model for the ThreeTrios game. The model is in charge of managing the
 * initialization of the game, managing actions done within the game, and keeping track of various
 * game states and variables.
 */
public interface ThreeTriosModel extends ReadonlyThreeTriosModel {

  void startGame(File cardConfig, File gridConfig);

  void playToGrid(int row, int column, int cardIdxInHand);

  void battle(int row, int column);

  void updatePlayerTurn();

  void addViewListener(ThreeTriosModelListener listener);

  void addAITurnListener(AIPlayerListener listener);
}