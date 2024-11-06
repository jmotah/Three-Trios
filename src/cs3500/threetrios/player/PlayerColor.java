package cs3500.threetrios.player;

import java.awt.*;

/**
 * An enumeration class to represent the two legal players in the game.
 */
public enum PlayerColor {
  RED(Color.RED),
  BLUE(Color.BLUE);

  private final Color color;

  PlayerColor(Color color) { this.color = color;}

  public Color getColor() { return color; }
}
