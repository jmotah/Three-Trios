package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.controller.Features;
import cs3500.threetrios.providers.controller.ThreeTriosFeatures;

public class MainFeaturesToProviderFeatures implements ThreeTriosFeatures {

  Features mainFeatures;

  public MainFeaturesToProviderFeatures(Features mainFeatures) {
    if (mainFeatures == null) {
      throw new IllegalArgumentException("mainFeatures cannot be null");
    }

    this.mainFeatures = mainFeatures;
  }

  @Override
  public void selectCard(int cardIndex) {
    mainFeatures.selectCard(cardIndex);
  }

  @Override
  public void selectCell(int row, int col) {
    mainFeatures.selectGridCell(row, col);
  }
}