package cs3500.threetrios.controller.filereader;

/**
 * Represents an interface for re-setting up the grid panel listeners when toggling has been
 * turned on or off. Whenever the view toggles hints, listeners will notify the controller to
 * call the hintsToggled method.
 */
public interface HintsToggleListener {

  /**
   * A method that will be called whenever the hints are toggled within the view.
   */
  void hintsToggled();
}
