package cs3500.threetrios.model;


/**
 * Represents tests for the PlayerPlayerModel class. A createModel method must be instantiated
 * for the creation of the specific model we want which is the PlayerPlayerModel in this case.
 * Any PlayerPlayerModel specific tests are beneath this.
 */
public class GameModelTests extends AbstractVariantModelTests {

  private ThreeTriosModel model;

  @Override
  protected ThreeTriosModel createModel() {
    return new GameModel();
  }
}