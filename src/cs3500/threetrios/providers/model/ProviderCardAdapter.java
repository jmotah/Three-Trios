package cs3500.threetrios.providers.model;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.providers.Utilities;

public class ProviderCardAdapter implements Card {
  private final Cards card;
  private final Utilities utilities;
  private PlayerColor cardOwnerColor;

  public ProviderCardAdapter(String name, AttackValue northValue, AttackValue southValue,
                             AttackValue eastValue, AttackValue westValue) {
    this.utilities = new Utilities();
    cardOwnerColor = null;

    card = new PlayingCard(name,
            utilities.providerAttackValueToMainCardNumber(northValue),
            utilities.providerAttackValueToMainCardNumber(southValue),
            utilities.providerAttackValueToMainCardNumber(eastValue),
            utilities.providerAttackValueToMainCardNumber(westValue));
  }

  @Override
  public String getCard() {
    return card.getName();
  }

  @Override
  public AttackValue getAttackValue(CardDirection direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Direction cannot be null");
    }

    CardCompass fixedDirection = utilities.providerCardDirectionToMainCardCompass(direction);
    int directionValue = card.getValue(fixedDirection);

    return utilities.mainCardNumberIntToProviderAttackValue(directionValue);
  }

  @Override
  public PlayerColor getCardOwner() {
    return cardOwnerColor;
  }

  @Override
  public void setCardOwner(PlayerColor color) {
    cardOwnerColor = color;
  }
}