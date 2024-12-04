package cs3500.threetrios.controller.filereader;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.cards.PlayingCard;

/**
 * A test class for the CardReader class. Verifies reading card configuration files.
 */
public class CardReaderTests {

  private CardReader createCardReader(File cardConfig) {
    return new CardReader(cardConfig);
  }

  @Test
  public void testCardReaderValid() {
    File cardConfig = new File("cardconfigs/card_configuration.txt");
    CardReader cardReader = createCardReader(cardConfig);

    int expected = 41;

    List<Cards> deck = cardReader.readConfiguration();

    Assert.assertEquals(expected, deck.size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameNullCardConfig() {
    CardReader nullCardReader = createCardReader(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameCardConfigFileNotFound() {
    File cardConfigNotFound = new File("this_should_not_exist");
    CardReader fileNotFound = createCardReader(cardConfigNotFound);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameCardConfigErrorNoCardName() {
    File cardConfigNoCardName = new File(
            "cardconfigs/incorrect_card_config_no_name.txt");
    CardReader cardReaderNoCardName = createCardReader(cardConfigNoCardName);

    cardReaderNoCardName.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameCardConfigErrorMissingCompassNumbers() {
    File cardConfigMissingNumbers = new File(
            "cardconfigs/incorrect_card_config_missing_numbers.txt");
    CardReader cardReaderMissingNumbers = createCardReader(cardConfigMissingNumbers);

    cardReaderMissingNumbers.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameCardConfigNumbersGreaterThanTen() {
    File cardConfigNumGreaterThanTen = new File(
            "cardconfigs/incorrect_card_config_num_above_ten.txt");
    CardReader cardReaderNumGreaterThanTen = createCardReader(cardConfigNumGreaterThanTen);

    cardReaderNumGreaterThanTen.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameCardConfigNumbersLessThanOne() {
    File cardConfigNumGreaterThanTen = new File(
            "cardconfigs/incorrect_card_config_num_less_one.txt");
    CardReader cardReaderNumGreaterThanTen = createCardReader(cardConfigNumGreaterThanTen);

    cardReaderNumGreaterThanTen.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameCardConfigEmpty() {
    File cardConfigEmpty = new File(
            "cardconfigs/incorrect_card_config_empty.txt");
    CardReader cardReaderEmpty = createCardReader(cardConfigEmpty);

    cardReaderEmpty.readConfiguration();
  }

  @Test
  public void testModelStartGameCardConfigLowerCaseUpperCaseIntegerAWithThree() {
    File cardConfigLowercaseUppercaseIntegerA = new File(
            "card_config_lowercase_uppercase_integer_a.txt");
    CardReader cardReaderLowercaseUppercaseIntegerA =
            createCardReader(cardConfigLowercaseUppercaseIntegerA);

    List<Cards> deck = cardReaderLowercaseUppercaseIntegerA.readConfiguration();

    PlayingCard expected = new PlayingCard("lowerCaseACardWith3", CardNumbers.A,
            CardNumbers.THREE, CardNumbers.A, CardNumbers.A);

    Assert.assertEquals(deck.get(0), expected);
  }

  @Test
  public void testCardReaderDuplicateCardsDifNames() {
    File cardConfigDupDifName = new File(
            "cardconfigs/card_config_duplicates_dif_name.txt");
    CardReader cardReaderDupDifName = createCardReader(cardConfigDupDifName);

    List<Cards> deck = cardReaderDupDifName.readConfiguration();

    PlayingCard expected1 = new PlayingCard("Card1", CardNumbers.ONE, CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE);
    PlayingCard expected2 = new PlayingCard("Card2", CardNumbers.ONE, CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE);

    Assert.assertEquals(expected1, deck.get(0));
    Assert.assertEquals(expected2, deck.get(1));
  }

  @Test (expected = IllegalStateException.class)
  public void testCardReaderDuplicateCardsSameNamesSameValues() {
    File cardConfigDupDifName = new File(
            "cardconfigs/incorrect_card_config_duplicates_same_name.txt");
    CardReader cardReaderDupDifName = createCardReader(cardConfigDupDifName);

    List<Cards> deck = cardReaderDupDifName.readConfiguration();
  }

  @Test (expected = IllegalStateException.class)
  public void testCardReaderDuplicateCardsSameNamesDifValues() {
    File cardConfigDupDifName = new File(
            "cardconfigs/incorrect_card_config_same_name_dif_value.txt");
    CardReader cardReaderDupDifName = createCardReader(cardConfigDupDifName);

    List<Cards> deck = cardReaderDupDifName.readConfiguration();
  }
}