package cs3500.threetrios.model.player;

import cs3500.threetrios.model.strategies.Strategies;

public interface AIPlayers extends Players {
  public Strategies getStrategies();
}
