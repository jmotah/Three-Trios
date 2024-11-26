package cs3500.threetrios.providers.view;

import cs3500.threetrios.controller.ThreeTriosFeatures;

/**
 * This interface extends the ModelView interface and provides additional methods
 * for handling player interactions and displaying messages.
 */
public interface ExtendedView extends ModelView {

  /**
   * Adds a Features listener to handle player actions.
   *
   * @param features the Features listener to register.
   */
  void addFeaturesListener(ThreeTriosFeatures features);

  /**
   * Displays an error message dialog to the player.
   *
   * @param message the message to display in the dialog.
   */
  void showMessageDialog(String message);
}
