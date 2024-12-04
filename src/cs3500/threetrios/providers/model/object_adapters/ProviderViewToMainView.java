package cs3500.threetrios.providers.model.object_adapters;

import java.util.List;

import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.providers.view.ExtendedView;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.graphical.GridLayoutPanel;
import cs3500.threetrios.view.graphical.PlayerCardsLayoutPanel;

/**
 * Adapts the provider's ExtendedView interface and class implementation to the project's main
 * ThreeTriosView interface. Adapts the provider's implementation to the primary interface.
 */
public class ProviderViewToMainView implements ThreeTriosView {

  private final ExtendedView providerView;

  /**
   * ProviderViewToMainView class constructor.
   *
   * @param providerView the provider ExtendedView interface implementation class to delegate to
   */
  public ProviderViewToMainView(ExtendedView providerView) {
    if (providerView == null) {
      throw new IllegalArgumentException("mainView cannot be null");
    }

    this.providerView = providerView;
  }

  /**
   * Displays the graphical view for a graphical view. Calls the refresh method to display the game
   * state for a textual view.
   */
  @Override
  public void makeVisible() {
    providerView.render();
  }

  /**
   * Refreshes the graphical view component, updating all the components for a graphical view.
   * Displays the refreshed game state in a textual manner for a textual view.
   */
  @Override
  public void refresh() {
    providerView.render();
  }

  /**
   * Gets the panel holding all the red player's cards for a graphical view. Returns null for a
   * textual view.
   *
   * @return a PlayerCardsLayoutPanel object
   */
  @Override
  public PlayerCardsLayoutPanel getRedCardPanel() {
    return new PlayerCardsLayoutPanel(
            new Player(PlayerColor.RED, List.of()));
  }

  /**
   * Gets the panel holding all the blue player's cards for a graphical view. Returns null for a
   * textual view.
   *
   * @return a PlayerCardsLayoutPanel object
   */
  @Override
  public PlayerCardsLayoutPanel getBlueCardPanel() {
    return new PlayerCardsLayoutPanel(
            new Player(PlayerColor.BLUE, List.of()));
  }

  /**
   * Gets the panel holding all the grid tile panels for a graphical view. Returns null for a
   * textual view.
   *
   * @return a GridLayoutPanel object
   */

  @Override
  public GridLayoutPanel getGridPanel() {
    return null;
  }

  /**
   * Transmit a message to the views for a graphical view. Returns for a textual view.
   *
   * @param title   the title of the display box
   * @param message the message to display to the player
   */
  @Override
  public void showMessage(String title, String message) {
    providerView.showMessageDialog(message);
  }

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly for
   * a graphical view. Returns for a textual view.
   *
   * @param error the error to display to the player
   */
  @Override
  public void showErrorMessage(String error) {
    providerView.showMessageDialog(error);
  }
}