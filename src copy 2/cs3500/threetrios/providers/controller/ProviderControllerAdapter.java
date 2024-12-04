//package cs3500.threetrios.providers.controller;
//
//import cs3500.threetrios.controller.ThreeTriosController;
//import cs3500.threetrios.model.player.Players;
//import cs3500.threetrios.providers.Utilities;
//import cs3500.threetrios.providers.model.Player;
//import cs3500.threetrios.providers.model.ThreeTriosModel;
//import cs3500.threetrios.providers.view.ExtendedView;
//import cs3500.threetrios.view.ThreeTriosView;
//import cs3500.threetrios.view.graphical.GraphicalView;
//
//public class ProviderControllerAdapter implements ThreeTriosFeatures,
//        ModelStateListener {
//
//  private final ThreeTriosController controller;
//  private ThreeTriosView view;
//
//  private final Utilities utilities;
//
//  public ProviderControllerAdapter(ThreeTriosModel model,
//                                   Player player,
//                                   ExtendedView view) {
//    if (model == null) {
//      throw new IllegalArgumentException("Model cannot be null");
//    } else if (player == null) {
//      throw new IllegalArgumentException("Player cannot be null");
//    } else if (view == null) {
//      throw new IllegalArgumentException("View cannot be null");
//    }
//
//    utilities = new Utilities();
//
//    controller = new ThreeTriosController(utilities.providerModelToMainModel(model),
//            utilities.providerPlayerToMainPlayers(player),
//            utilities.providerViewToMainView(view));
//  }
//
//  @Override
//  public void selectCard(int cardIndex) {
//    controller.selectCard(cardIndex);
//  }
//
//  @Override
//  public void selectCell(int row, int col) {
//    controller.selectGridCell(row, col);
//  }
//
//  @Override
//  public void updateView() {
//    controller.modelWasUpdated();
//  }
//
//  @Override
//  public void initializeGame() {
//    //model takes care of initializing the game, so we simply are refreshing the view
//    controller.modelWasUpdated();
//  }
//}