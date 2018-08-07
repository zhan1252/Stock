package controller;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import history.TickerSymbol;
import javafx.util.Pair;
import model.IStockModel;
import view.IView;
import view.View;


public class Controller implements IController {
  int width = 500;
  int height = 500;

  public Controller() {

  }


  @Override
  public void start(IStockModel model, IView view) {

    TickerSymbol symbol = view.getSymbol();
    model.addIHistory(symbol);
    List<Pair<Date, Double>> data = model.stockHistory(symbol, view.getStartDate(), view.getEndDate());

    //need to run data through convert stock scale
    List<Pair<Double, Double>> listStockPrice = new ArrayList<>();
    List<Pair<Double,Double>> buyOpportunity = new ArrayList<>();

    for (int i = 0; i < data.size(); ++i) {
      double dateScale = convertScaleDate(data.get(i).getKey(), view);
     // System.out.println(dateScale);
      double stockScale = convertStockScale(data.get(i).getValue(), model, view);
      listStockPrice.add(new Pair<>(dateScale, stockScale));

      if(model.buyOpportunity(symbol,data.get(i).getKey())) {
        buyOpportunity.add(new Pair<>(dateScale,stockScale));
      }
    }



    view.setBuyOpportunity(buyOpportunity);
    view.setData(listStockPrice);
    view.render();

  }


  private double convertStockScale(double i, IStockModel model, IView view) {
    double minPrice = model.minPrice(view.getSymbol(), view.getStartDate(), view.getEndDate());
    double maxPrice = model.maxPrice(view.getSymbol(), view.getStartDate(), view.getEndDate());

    view.setMinPrice(minPrice);
    view.setMaxPrice(maxPrice);
    return (((i - (minPrice)) / ((maxPrice) - (minPrice)) * (height-50))) + 50;
  }



  private double convertScaleDate(Date i, IView view) {
    Calendar tempStart = Calendar.getInstance();
    tempStart.setTime(view.getStartDate());
    Calendar tempStart2 = Calendar.getInstance();
    tempStart2.setTime(view.getStartDate());
    int dateNumber = 0;
    int maxDate = 0;

    while (tempStart.getTime().before(i) || tempStart.getTime().equals(i)) {
      ++dateNumber;
      tempStart.add(Calendar.DATE, 1);
    }
    while (tempStart2.getTime().before(view.getEndDate()) || tempStart2.getTime().equals(view.getEndDate())) {
      ++maxDate;
      tempStart2.add(Calendar.DATE, 1);
    }

    return ((double)dateNumber) / (maxDate) * (width);

  }



}
