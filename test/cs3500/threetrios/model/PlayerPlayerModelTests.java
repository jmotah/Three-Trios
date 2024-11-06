package cs3500.threetrios.model;

import cs3500.threetrios.model.playervsplayer.PlayerPlayerModel;


/**
 * Represents tests for the PlayerPlayerModel class. A createModel method must be instantiated
 * for the creation of the specific model we want which is the PlayerPlayerModel in this case.
 * Any PlayerPlayerModel specific tests are beneath this.
 */
public class PlayerPlayerModelTests extends AbstractVariantModelTests {

  private ThreeTriosModel model;

  @Override
  protected PlayerPlayerModel createModel() {
    return new PlayerPlayerModel();
  }
}