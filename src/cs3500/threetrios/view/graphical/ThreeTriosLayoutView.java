package cs3500.threetrios.view.graphical;

/**
 * An interface for layout views including a method to update the components within the layout view.
 */
public interface ThreeTriosLayoutView {
  /**
   * Updates all the components within the grid layout. Manages this by removing the current
   * components and re-adding the updated versions of the components. Revalidates after removal and
   * re-addition of components.
   */
  void updateComponents();
}
