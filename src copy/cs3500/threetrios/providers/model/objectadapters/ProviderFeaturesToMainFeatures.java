package cs3500.threetrios.providers.model.objectadapters;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.providers.controller.ThreeTriosFeatures;

/**
 * Adapts the provider's ThreeTriosFeatures interface and class implementation to the project's
 * main Features interface. Adapts the provider's implementation to the primary interface.
 */
public class ProviderFeaturesToMainFeatures implements Features {

  private final ThreeTriosFeatures providerFeatures;

  /**
   * ProviderFeaturesToMainFeatures class constructor.
   *
   * @param providerFeatures the provider ThreeTriosFeatures interface implementation class to
   *                         delegate to
   */
  public ProviderFeaturesToMainFeatures(ThreeTriosFeatures providerFeatures) {
    if (providerFeatures == null) {
      throw new IllegalArgumentException("providerFeatures cannot be null!");
    }
    this.providerFeatures = providerFeatures;
  }

  /**
   * Given a card index, selects the respective card in the player's hand by delegating.
   *
   * @param cardIndex the card index of the card to select
   */
  @Override
  public void selectCard(int cardIndex) {
    this.providerFeatures.selectCard(cardIndex);
  }

  /**
   * Given a row and column index, selects the respective grid tile in the game grid and places the
   * selected card index there. The requirement to use this method is that there must be a selected
   * card index already.
   *
   * @param row    the row index to play to
   * @param column the column index to play to
   */
  @Override
  public void selectGridCell(int row, int column) {
    providerFeatures.selectCell(row, column);
  }
}
