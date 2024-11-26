package cs3500.threetrios.model.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.PlayingCard;

/**
 * Represents tests for the HumanPlayer class. Verifies the constructor works properly as well as
 * any of its implemented methods.
 */
public class PlayerTests {

  private Players human;

  @Before
  public void setup() {
    human = new Player(PlayerColor.RED, List.of(
            new PlayingCard("Card1", CardNumbers.ONE, CardNumbers.ONE,
                    CardNumbers.ONE, CardNumbers.ONE),
            new PlayingCard("Card1", CardNumbers.TWO, CardNumbers.TWO,
                    CardNumbers.TWO, CardNumbers.TWO)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHumanPlayerConstructorNullColor() {
    human = new Player(null, List.of());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHumanPlayerConstructorNullHand() {
    human = new Player(PlayerColor.RED, null);
  }

  @Test
  public void testHumanPlayerGetPlayersColor() {
    PlayerColor expected = PlayerColor.RED;

    Assert.assertEquals(expected, human.getPlayersColor());
  }

  @Test
  public void testHumanPlayerGetHand() {
    List<PlayingCard> expected = List.of(
            new PlayingCard("Card1", CardNumbers.ONE, CardNumbers.ONE,
                    CardNumbers.ONE, CardNumbers.ONE),
            new PlayingCard("Card1", CardNumbers.TWO, CardNumbers.TWO,
                    CardNumbers.TWO, CardNumbers.TWO));

    Assert.assertEquals(expected, human.getHand());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerRemoveCardAtIndexLessThanZero() {
    human.removeCardAtIndex(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerRemoveCardAtIndexGreaterThanHandSize() {
    human.removeCardAtIndex(5);
  }
}
