package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.providers.model.PlayerColor;

public class MainPlayerColorToProviderPlayerColor implements ConvertEnums<PlayerColor> {

  private final cs3500.threetrios.model.player.PlayerColor color;

  public MainPlayerColorToProviderPlayerColor(cs3500.threetrios.model.player.PlayerColor color) {
    this.color = color;
  }

  @Override
  public PlayerColor convertEnums() {
    return mainColorToProvidedPlayerColor(color);
  }

  private PlayerColor mainColorToProvidedPlayerColor(
          cs3500.threetrios.model.player.PlayerColor mainColor) {
    switch (mainColor) {
      case RED:
        return PlayerColor.RED;
      case BLUE:
        return PlayerColor.BLUE;
      default:
        throw new IllegalArgumentException("Invalid player color case!");
    }
  }

}
