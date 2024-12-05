package cs3500.threetrios.model;

import java.util.List;

import cs3500.threetrios.model.battlestrategies.BattleStrategies;
import cs3500.threetrios.controller.ThreeTriosModelListener;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.player.AIPlayerListener;

/**
 * Represents a generic model for the ThreeTrios game. The model is in charge of managing the
 * initialization of the game, managing actions done within the game, and keeping track of various
 * game states and variables.
 */
public interface ThreeTriosModel extends ReadonlyThreeTriosModel {

  /**
   * Starts the game with a given set of configurations from a card and grid configuration file.
   *
   * @param grid the desired grid configuration to use; this usually is delivered from a grid
   *             configuration reader class
   * @param deck the desired deck to use; this usually is delivered from a card configuration
   *             reader class
   */
  void startGame(Grid[][] grid, List<Cards> deck);

  /**
   * Plays a desired card from the current player's hand to a desired cell in the grid.
   *
   * @param row           the desired row to play to; number is 0-index based
   * @param column        the desired column to play to; number is 0-index based
   * @param cardIdxInHand the desired card index to play from the player's hand; number is 0-index
   *                      based
   */
  void playToGrid(int row, int column, int cardIdxInHand);

  /**
   * Battles the nearby cards at the provided row and column index. If the provided card wins the
   * battle, updates the card battled against to become the current player's card.
   *
   * @param row    the desired row index to initiate the battle with; number is 0-index based
   * @param column the desired column index to initiate the battle with; number is 0-index based
   */
  void battle(int row, int column);

  /**
   * Sets the variant battle rule to take priority in the model.
   *
   * @param strategy the rule to set as the understanding for how battling takes place
   */
  void setBattleRule(BattleStrategies strategy);

  /**
   * Updates the current player's turn from the given player to the next player. As an example,
   * if the given player is RED, the current player's turn will update to BLUE, and vice versa.
   */
  void updatePlayerTurn();

  /**
   * Adds a listener to prepare the view to be updated when the model notifies it to.
   *
   * @param listener the listener to add to the model
   */
  void addViewListener(ThreeTriosModelListener listener);

  /**
   * Adds a listener to prepare the AI to commence its turn when the model notifies it to.
   *
   * @param listener the listener to add to the model
   */
  void addAITurnListener(AIPlayerListener listener);
}