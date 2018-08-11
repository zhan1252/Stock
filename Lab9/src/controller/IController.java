package controller;


import model.IStockModel;
import view.IView;

/**
 * This interface represents the methods required of a controller.
 */
public interface IController {

  /**
   * Start controller.
   *
   * @param model given model.
   * @param view  given view.
   */
  void start(IStockModel model, IView view);

}
