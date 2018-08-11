package view;

import java.util.Date;
import java.util.List;

import history.TickerSymbol;
import javafx.util.Pair;

/**
 * Represents all of the methods required of an IView.
 */
public interface IView {

  /**
   * Render the image.
   */
  void render();

  /**
   * Get ticket symbol.
   *
   * @return ticker symbol.
   */
  TickerSymbol getSymbol();

  /**
   * Set data in view.
   *
   * @param data given data.
   */
  void setData(List<Pair<Double, Double>> data);

  /**
   * Get start date in given range.
   *
   * @return start date.
   */
  Date getStartDate();

  /**
   * Get end date in given range.
   *
   * @return end date.
   */
  Date getEndDate();

  /**
   * Set the minimum price.
   *
   * @param minPrice given minimum price.
   */
  void setMinPrice(double minPrice);

  /**
   * Set maximum price.
   *
   * @param maxPrice given maximum price.
   */
  void setMaxPrice(double maxPrice);

  /**
   * Set buying opportunity.
   *
   * @param data given data.
   */
  void setBuyOpportunity(List<Pair<Double, Double>> data);
}
