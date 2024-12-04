package cs3500.threetrios.providers.model.object_adapters;

import java.util.List;

import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.providers.view.ExtendedView;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.graphical.GridLayoutPanel;
import cs3500.threetrios.view.graphical.PlayerCardsLayoutPanel;

public class ProviderViewToMainView implements ThreeTriosView {

  private final ExtendedView providerView;

  public ProviderViewToMainView(ExtendedView providerView) {
    if (providerView == null) {
      throw new IllegalArgumentException("mainView cannot be null");
    }

    this.providerView = providerView;
  }

  @Override
  public void makeVisible() {
    providerView.render();
  }

  @Override
  public void refresh() {
    providerView.render();
  }

  @Override
  public PlayerCardsLayoutPanel getRedCardPanel() {
    return new PlayerCardsLayoutPanel(
            new Player(PlayerColor.RED, List.of()));
  }

  @Override
  public PlayerCardsLayoutPanel getBlueCardPanel() {
    return new PlayerCardsLayoutPanel(
            new Player(PlayerColor.BLUE, List.of()));
  }

  @Override
  public GridLayoutPanel getGridPanel() {
    return null;
  }

  @Override
  public void showMessage(String title, String message) {
    providerView.showMessageDialog(message);
  }

  @Override
  public void showErrorMessage(String error) {
    providerView.showMessageDialog(error);
  }
}