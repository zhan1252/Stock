package history;

import java.util.Date;
import java.util.List;

import javafx.util.Pair;

/**
 * This interface represents the historical price of one stock.
 */
public interface IHistory {

  /**
   * Get the price of a stock on a certain day.
   *
   * @param date given day
   * @return the price
   * @throws IllegalArgumentException if the date is not valid in the history
   */
  double priceOnDay(Date date) throws IllegalArgumentException;

  /**
   * Return true if there is a buying opportunity for this stock on a certain day, else return
   * false. Buying opportunity is the day at which the 50-day moving average crosses the 200-day
   * moving average. If the given date is not a valid entry in the history, or the history is not
   * long enough, return false.
   *
   * @param date given day
   * @return true if there is a buying opportunity, otherwise false
   */
  boolean buyOpportunity(Date date);


  /**
   * Get closing prices for this stock for a certain day ranges.
   *
   * @param startDate starting date
   * @param endDate   ending date
   * @return the closing prices for a certain day ranges
   * @throws IllegalArgumentException if end date before start date.
   */
  List<Pair<Date, Double>> historicalPrices(Date startDate, Date endDate)
          throws IllegalArgumentException;


  /**
   * Return true if there is trending up for this stock within the given day range.
   *
   * @param startDate given start date
   * @param endDate   given end date
   * @return true if this stock trends up, otherwise false
   * @throws IllegalArgumentException if endDate is a date before the start date
   */
  boolean trendUpPerStock(Date startDate, Date endDate) throws IllegalArgumentException;


}
