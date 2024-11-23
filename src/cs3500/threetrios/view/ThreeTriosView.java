package cs3500.threetrios.view;

import cs3500.threetrios.view.graphical.GridLayoutPanel;
import cs3500.threetrios.view.graphical.PlayerCardsLayoutPanel;

/**
 * A generic view for the Three Trios game which is in charge of displaying the game grid, player
 * hand(s), and current player's turn.
 **/
public interface ThreeTriosView {
  void makeVisible();

  void refresh();

  PlayerCardsLayoutPanel getRedCardPanel();

  PlayerCardsLayoutPanel getBlueCardPanel();

  GridLayoutPanel getGridPanel();

  void showMessage(String title, String message);

  void showErrorMessage(String error);
}