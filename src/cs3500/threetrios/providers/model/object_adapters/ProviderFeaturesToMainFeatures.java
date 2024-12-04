package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.providers.controller.ThreeTriosFeatures;

public class ProviderFeaturesToMainFeatures implements Features {

  private final ThreeTriosFeatures providerFeatures;

  public ProviderFeaturesToMainFeatures(ThreeTriosFeatures providerFeatures) {
    if (providerFeatures == null) {
      throw new IllegalArgumentException("providerFeatures cannot be null!");
    }
    this.providerFeatures = providerFeatures;
  }

  @Override
  public void selectCard(int cardIndex) {
    this.providerFeatures.selectCard(cardIndex);
  }

  @Override
  public void selectGridCell(int row, int column) {
    this.selectGridCell(row, column);
  }
}
