package cs3500.threetrios.model.cards;

/**
 * Represents functionality for a playing card within the ThreeTrios game.
 */
public interface Cards {
  String getName();

  int getValue(CardCompass direction);

  String toString();
}
