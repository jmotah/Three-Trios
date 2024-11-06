package cs3500.threetrios.controller.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.cards.CardNumbers;
import cs3500.threetrios.cards.PlayingCard;

/**
 * Responsible for reading and translating a card configuration file into a list of PlayingCard
 * objects. Each line represents a single playing card for the game. Two cards can have the same
 * value, but they must not have the same name and same values.
 * A card configuration file must be made in a specific way. Each line of the file represents a new
 * playing card. The first parameter is the name of the card stored as a string. The second, third,
 * fourth, and fifth parameter are the integer attack values of the cards for north, south, east,
 * and west, respectively. For the 'A' card number, the letter "A" regardless of case as well as
 * the integer 10 can be typed into the card configuration file.
 * SAMPLE CARD CONFIGURATION FILE:
 * CardOne 1 2 3 4
 * CardTwo A a 3 10
 * CardThree A 4 2 2
 * CardFour 4 3 2 1
 * CardFive 1 1 1 1
 * CardSix 1 1 1 1
 */
public class CardReader implements ConfigurationReader<List<PlayingCard>> {
  private final BufferedReader cardConfigReader;

  /**
   * A constructor for the CardReader class.
   *
   * @param cardConfig the file to read from
   */
  public CardReader(File cardConfig) {

    if (cardConfig == null) {
      throw new IllegalArgumentException("Card config file cannot be null.");
    }

    try {
      this.cardConfigReader = new BufferedReader(new FileReader(cardConfig));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("The given file is not found!");
    }
  }

  /**
   * Reads the configuration class and gathers a list of playing cards.
   *
   * @return list of playing cards retrieved from the configuration file
   */
  @Override
  public List<PlayingCard> readConfiguration() {
    List<PlayingCard> cards = readCardsFromCardConfig();
    checkForDuplicates(cards);

    return cards;
  }


  /**
   * Reads all the cards in the card configuration file and converts them to a list of
   * PlayingCard objects.
   *
   * @return a list of PlayingCard objects of the cards read from the card configuration file
   */
  private List<PlayingCard> readCardsFromCardConfig() {
    try {
      String readLine = this.cardConfigReader.readLine();
      List<PlayingCard> deck = new ArrayList<>(List.of());

      if (readLine.isEmpty()) {
        throw new IllegalArgumentException("Card config file must contain cards!");
      }

      while (readLine != null) {
        deck.add(configLineToPlayingCard(readLine));
        readLine = this.cardConfigReader.readLine();
      }
      return deck;
    } catch (NullPointerException | IOException | StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("The card configuration is unreadable!");
    }
  }

  /**
   * Converts each individual line within the card configuration file to an individual
   * PlayingCard object.
   *
   * @param readLine the line to analyze from the file reader
   * @return a PlayingCard object for the read playing card from the card configuration
   */
  private static PlayingCard configLineToPlayingCard(String readLine) {
    String name;
    CardNumbers north;
    CardNumbers south;
    CardNumbers east;
    CardNumbers west;
    int nextSpaceIndex = readLine.indexOf(" ");
    name = readLine.substring(0, nextSpaceIndex);

    readLine = readLine.substring(nextSpaceIndex + 1);
    nextSpaceIndex = readLine.indexOf(" ");
    north = parseLine(readLine.substring(0, nextSpaceIndex));

    readLine = readLine.substring(nextSpaceIndex + 1);
    nextSpaceIndex = readLine.indexOf(" ");
    south = parseLine(readLine.substring(0, nextSpaceIndex));

    readLine = readLine.substring(nextSpaceIndex + 1);
    nextSpaceIndex = readLine.indexOf(" ");
    east = parseLine(readLine.substring(0, nextSpaceIndex));

    readLine = readLine.substring(nextSpaceIndex + 1);
    west = parseLine(readLine);

    return new PlayingCard(name, north, south, east, west);
  }

  /**
   * Parses the given line to read. Checks if it is equivalent to "A" or not. If it is, returns the
   * CardNumbers rather than the card's value. Else wise, it returns the card's integer value.
   * This method allows the player to use either the integer 10, or the string "A" to represent a
   * CardNumbers. An object within the card configuration.
   *
   * @param readLine the given line to read
   * @return a CardNumbers object of the
   */
  private static CardNumbers parseLine(String readLine) {
    if (readLine.equalsIgnoreCase("A")) {
      return CardNumbers.A;
    } else {
      return CardNumbers.valueToCard(Integer.parseInt(readLine));
    }
  }

  private void checkForDuplicates(List<PlayingCard> cards) {
    List<String> seenCards = new ArrayList<>();

    for (PlayingCard card : cards) {
      if (!seenCards.contains(card.getName())) {
        seenCards.add(card.getName());
      } else {
        throw new IllegalStateException("The deck contains duplicate cards!");
      }
    }
  }

}