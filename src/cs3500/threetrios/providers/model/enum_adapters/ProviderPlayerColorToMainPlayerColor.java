package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.model.player.PlayerColor;

public class ProviderPlayerColorToMainPlayerColor implements ConvertEnums<PlayerColor> {

  private final cs3500.threetrios.providers.model.PlayerColor color;

  public ProviderPlayerColorToMainPlayerColor(cs3500.threetrios.providers.model.PlayerColor color) {
    this.color = color;
  }

  @Override
  public PlayerColor convertEnums() {
    return providerPlayerColorToMainPlayerColor(color);
  }

  private cs3500.threetrios.model.player.PlayerColor
  providerPlayerColorToMainPlayerColor(cs3500.threetrios.providers.model.PlayerColor providerColor) {
    switch (providerColor) {
      case RED:
        return cs3500.threetrios.model.player.PlayerColor.RED;
      case BLUE:
        return cs3500.threetrios.model.player.PlayerColor.BLUE;
      default:
        throw new IllegalArgumentException("Invalid player color case!");
    }
  }

}
