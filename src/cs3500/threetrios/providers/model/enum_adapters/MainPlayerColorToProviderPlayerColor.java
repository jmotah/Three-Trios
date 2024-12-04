package cs3500.threetrios.providers.model.enum_adapters;

import cs3500.threetrios.providers.model.PlayerColor;

/**
 * Class to help convert from the primary project's PlayerColor enum to the Provider's PlayerColor
 * enum.
 */
public class MainPlayerColorToProviderPlayerColor implements ConvertEnums<PlayerColor> {

  private final cs3500.threetrios.model.player.PlayerColor color;

  /**
   * MainPlayerColorToProviderPlayerColor class constructor.
   *
   * @param color the primary project's PlayerColor enum value to translate from
   */
  public MainPlayerColorToProviderPlayerColor(cs3500.threetrios.model.player.PlayerColor color) {
    this.color = color;
  }

  /**
   * Converts from the primary project's PlayerColor enum value to the Provider's PlayerColor enum.
   *
   * @return the translated Provider's PlayerColor enum value which we translated from the project's
   * primary PlayerColor enum value.
   */
  @Override
  public PlayerColor convertEnums() {
    return mainColorToProvidedPlayerColor();
  }

  /**
   * Helper class to perform the switch from the primary project's PlayerColor enum value to the
   * Provider's PlayerColor enum value.
   *
   * @return the translated Provider's PlayerColor enum value which we translated from the primary
   * project's PlayerColor enum value
   */
  private PlayerColor mainColorToProvidedPlayerColor() {
    switch (color) {
      case RED:
        return PlayerColor.RED;
      case BLUE:
        return PlayerColor.BLUE;
      default:
        throw new IllegalArgumentException("Invalid player color case!");
    }
  }
}