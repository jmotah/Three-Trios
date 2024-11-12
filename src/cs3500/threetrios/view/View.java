package cs3500.threetrios.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.function.Consumer;

/**
 * The view interface. To motivate the methods here
 * think about all the operations that the controller
 * would need to invoke on the view
 */
public interface View {
  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  void makeVisible();

  /**
   * Transmit an error message to the view, in case
   * the command could not be processed correctly
   *
   * @param error the error message
   */
  void showErrorMessage(String error);

  /**
   * Signals the view to re-draw itself.
   */
  void refresh();
}
