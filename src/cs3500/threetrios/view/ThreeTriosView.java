package cs3500.threetrios.view;

import cs3500.threetrios.controller.filereader.HintsToggleListener;
import cs3500.threetrios.view.graphical.GridLayoutPanel;
import cs3500.threetrios.view.graphical.PlayerCardsLayoutPanel;

/**
 * A generic view for the Three Trios game which is in charge of displaying the game grid, player
 * hand(s), and current player's turn.
 **/
public interface ThreeTriosView {
  /**
   * Displays the graphical view for a graphical view. Calls the refresh method to display the game
   * state for a textual view.
   */
  void makeVisible();

  /**
   * Refreshes the graphical view component, updating all the components for a graphical view.
   * Displays the refreshed game state in a textual manner for a textual view.
   */
  void refresh();

  /**
   * Gets the panel holding all the red player's cards for a graphical view. Returns null for a
   * textual view.
   *
   * @return a PlayerCardsLayoutPanel object
   */
  PlayerCardsLayoutPanel getRedCardPanel();

  /**
   * Gets the panel holding all the blue player's cards for a graphical view. Returns null for a
   * textual view.
   *
   * @return a PlayerCardsLayoutPanel object
   */
  PlayerCardsLayoutPanel getBlueCardPanel();

  /**
   * Gets the panel holding all the grid tile panels for a graphical view. Returns null for a
   * textual view.
   *
   * @return a GridLayoutPanel object
   */
  GridLayoutPanel getGridPanel();

  /**
   * Transmit a message to the views for a graphical view. Returns for a textual view.
   *
   * @param title   the title of the display box
   * @param message the message to display to the player
   */
  void showMessage(String title, String message);

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly for
   * a graphical view. Returns for a textual view.
   *
   * @param error the error to display to the player
   */
  void showErrorMessage(String error);

  /**
   * Sets a listener to prepare to be called whenever the toggle hints button is clicked.
   *
   * @param listener the listener to set
   */
  void setHintsToggleListener(HintsToggleListener listener);

  /**
   * Sets the currently clicked card index.
   *
   * @param currentlyClickedCardIndex the card index to set the currently clicked card index to
   */
  void setCurrentlyClickedCardIndex(int currentlyClickedCardIndex);
}