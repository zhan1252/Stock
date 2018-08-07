package controller;

import java.util.Date;

import model.IStockModel;
import view.IView;

public interface IController {

  void start(IStockModel model, IView view);

}
