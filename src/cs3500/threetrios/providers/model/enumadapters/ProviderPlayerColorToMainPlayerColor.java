package cs3500.threetrios.providers.model.enumadapters;

import cs3500.threetrios.model.player.PlayerColor;

/**
 * Class to help convert from the Provider's PlayerColor enum to the primary project's
 * PlayerColor enum.
 */
public class ProviderPlayerColorToMainPlayerColor implements ConvertEnums<PlayerColor> {

  private final cs3500.threetrios.providers.model.PlayerColor color;

  /**
   * ProviderPlayerColorToMainPlayerColor class constructor.
   *
   * @param color the provider's PlayerColor enum value to translate from
   */
  public ProviderPlayerColorToMainPlayerColor(cs3500.threetrios.providers.model.PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }

    this.color = color;
  }

  /**
   * Converts from the Provider's PlayerColor enum to the project's primary PlayerColor enum.
   *
   * @return the translated project's PrimaryColor enum value which we translated from the
   *     Provider's enum value
   */
  @Override
  public PlayerColor convertEnums() {
    return providerPlayerColorToMainPlayerColor();
  }

  /**
   * Helper class to perform the switch from the provider's PlayerColor enum value to the
   * primary project's PlayerColor enum value.
   *
   * @return the translated primary project's PlayerColor enum value which we translated from the
   *     provider's PlayerColor enum value
   */
  private cs3500.threetrios.model.player.PlayerColor
      providerPlayerColorToMainPlayerColor() {
    switch (color) {
      case RED:
        return cs3500.threetrios.model.player.PlayerColor.RED;
      case BLUE:
        return cs3500.threetrios.model.player.PlayerColor.BLUE;
      default:
        throw new IllegalArgumentException("Invalid player color case!");
    }
  }
}