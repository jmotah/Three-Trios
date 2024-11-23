package cs3500.threetrios.controller;

/**
 * Represents an interface for updating the view when a card has been played to the grid in the
 * model. Whenever the model state changes, listeners will notify classes that implement this
 * interface and perform an action accordingly.
 */
public interface ThreeTriosModelListener {

  /**
   * Refreshes the view and adds and readjusts the listeners to every clickable panel.
   */
  void modelWasUpdated();
}