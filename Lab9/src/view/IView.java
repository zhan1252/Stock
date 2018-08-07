package view;

import java.util.Date;
import java.util.List;

import history.TickerSymbol;
import javafx.util.Pair;

public interface IView {

  void render();

  TickerSymbol getSymbol();

  void setData(List<Pair<Double, Double>> data);

  Date getStartDate();

  Date getEndDate();
}
