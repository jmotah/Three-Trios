package cs3500.threetrios.providers.model.object_adapters;

import cs3500.threetrios.controller.ThreeTriosController;
import cs3500.threetrios.providers.controller.ModelStateListener;
import cs3500.threetrios.providers.controller.ThreeTriosFeatures;


public class MainControllerToProviderController implements ThreeTriosFeatures,
        ModelStateListener {

  private final ThreeTriosController controller; //is sending the class instance okay since 2 imports?

  public MainControllerToProviderController(ThreeTriosController controller) {
    if(controller == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }

    this.controller = controller;
  }

  /**
   * Called when the model's state changes.
   * Implementing classes should define how they react to the model's updates.
   */
  @Override
  public void updateView() {
    controller.modelWasUpdated();
  }

  /**
   * Initializes the game. This cannot be adapted.
   */
  @Override
  public void initializeGame() {
    return; //CANNOT ADAPT
  }

  /**
   * Handles the action of selecting a card from the player's hand by delegating.
   *
   * @param cardIndex the index of the card in the player's hand.
   */
  @Override
  public void selectCard(int cardIndex) {
    controller.selectCard(cardIndex);
  }

  /**
   * Handles the action of selecting a grid cell to play a card by delegating.
   *
   * @param row the row index of the selected cell.
   * @param col the column index of the selected cell.
   */
  @Override
  public void selectCell(int row, int col) {
    controller.selectGridCell(row, col);
  }
}