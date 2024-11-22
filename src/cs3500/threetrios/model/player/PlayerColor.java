package cs3500.threetrios.model.player;

import java.awt.Color;

/**
 * An enumeration class to represent the two legal players in the game.
 */
public enum PlayerColor {
  RED(Color.RED),
  BLUE(Color.BLUE);

  private final Color color;

  /**
   * Constructor for a PlayerColor enum which possesses a specific Color value associated to each
   * enum value.
   *
   * @param color the Color object to associate with the PlayerColor enum value
   */
  PlayerColor(Color color) {
    this.color = color;
  }

  /**
   * Gets the Color object associated with the enum value.
   *
   * @return the Color object associated with the enum value
   */
  public Color getColor() {
    return color;
  }
}
