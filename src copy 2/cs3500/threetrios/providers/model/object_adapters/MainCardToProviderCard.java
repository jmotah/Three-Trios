package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.providers.model.AttackValue;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.CardDirection;
import cs3500.threetrios.providers.model.PlayerColor;
import cs3500.threetrios.providers.model.enum_adapters.MainIntegerToProviderAttackValue;
import cs3500.threetrios.providers.model.enum_adapters.ProviderCardDirectionToMainCardCompass;

public class MainCardToProviderCard implements Card {

  private final Cards card;

  private PlayerColor colorOwner;

  public MainCardToProviderCard(Cards card) {
    this.card = card;

    this.colorOwner = null;
  }

  @Override
  public String getCard() {
    return card.getName();
  }

  @Override
  public AttackValue getAttackValue(CardDirection direction) {
    ProviderCardDirectionToMainCardCompass cardDirectionToCardCompass =
            new ProviderCardDirectionToMainCardCompass(direction);

    int value = card.getValue(cardDirectionToCardCompass.convertEnums());

    MainIntegerToProviderAttackValue integerToAttackValue =
            new MainIntegerToProviderAttackValue(value);

    return integerToAttackValue.convertEnums();
  }

  @Override
  public PlayerColor getCardOwner() {
    return colorOwner;
  }

  @Override
  public void setCardOwner(PlayerColor color) {
    this.colorOwner = color;
  }
}
