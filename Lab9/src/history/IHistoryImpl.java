package history;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javafx.util.Pair;
import stockapp.StockApplication;

/**
 * This class represents an IHistoryImpl, which store the history of a SKU.
 * This history is obtained using the API in StockApplication class.
 */
public class IHistoryImpl implements IHistory {
  private HashMap<Date, Double> history;

  /**
   * Constructor for IHistoryImpl.  This takes in a ticketSymbol and gets the history of
   * the stock using the API in the StockApplication class.  If history cannot be obtained,
   * then an empty hashmap is created.
   *
   * @param tickerSymbol given tickerSymbol.
   */
  public IHistoryImpl(TickerSymbol tickerSymbol) {
    try {
      this.history = StockApplication.makeHistory(tickerSymbol);
    } catch (Exception e) {
      this.history = new HashMap<>();
    }
  }

  /**
   * Get the price of a stock on a certain day.
   *
   * @param date given day
   * @return the price
   * @throws IllegalArgumentException if the date is not valid in the history
   */
  @Override
  public double priceOnDay(Date date) throws IllegalArgumentException {
    if (!this.history.containsKey(date)) {
      throw new IllegalArgumentException("Date doesn't exist in the history!");
    }
    return history.get(date);
  }

  /**
   * Return true if there is a buying opportunity for this stock on a certain day, else return
   * false. Buying opportunity is the day at which the 50-day moving average crosses the 200-day
   * moving average. If the given date is not a valid entry in the history, or the history is not
   * long enough, return false.
   *
   * @param date given day
   * @return true if there is a buying opportunity, otherwise false
   */
  @Override
  public boolean buyOpportunity(Date date) {
    if (this.history.size() < 200) {
      return false;
    } else if (!this.history.containsKey(date)) {
      return false;
    }
    try {
      boolean result = this.calculateAverage(50, date)
              > this.calculateAverage(200, date);
      return result;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Calculates the average price of a stock over a given number of days starting at a given date.
   * Only takes into account business days and non-holidays.
   *
   * @param days given days to calculate average.
   * @param date given date.
   * @return double that represents the average price over given days at given date.
   * @throws IllegalArgumentException if negative days given or date is not in hashmap.
   */
  private double calculateAverage(int days, Date date) throws IllegalArgumentException {
    Calendar temp = Calendar.getInstance();
    temp.setTime(date);
    if (days <= 0) {
      throw new IllegalArgumentException("Number of days must be positive!");
    }
    if (!history.containsKey(date)) {
      throw new IllegalArgumentException("You cannot buy stock on this day!");
    }
    double sum = 0;
    for (int i = 0; i < days; i++) {
      int j = 0;
      while (j < 10 && (!this.history.containsKey(temp.getTime()))) {
        //check if there is holiday, maximum 10 days in a roll
        temp.add(Calendar.DATE, -1);
        j++;
      }
      if (j == 10) {
        //check if the earliest history is reached
        throw new IllegalArgumentException("earliest history is reached");
      }
      sum += this.history.get(temp.getTime());

      temp.add(Calendar.DATE, -1);
    }
    return sum / days;
  }

  /**
   * Get closing prices for this stock for a certain day ranges.  Returns a list of pairs of
   * dates and doubles representing the price of a stock on a given day.
   *
   * @param startDate starting date
   * @param endDate   ending date
   * @return the closing prices for a certain day ranges
   * @throws IllegalArgumentException if end date before start date.
   */
  @Override
  public List<Pair<Date, Double>> historicalPrices(Date startDate, Date endDate)
          throws IllegalArgumentException {

    List<Pair<Date, Double>> historicalPrices = new ArrayList<>();
    Calendar tempStart = Calendar.getInstance();
    tempStart.setTime(startDate);

    if (endDate.before(startDate)) {
      throw new IllegalArgumentException("End date must be after start date!");
    }

    while (tempStart.getTime().before(endDate) || tempStart.getTime().equals(endDate)) {
      if (history.containsKey(tempStart.getTime())) {
        historicalPrices.add(new Pair<>(tempStart.getTime(), history.get(tempStart.getTime())));
      }
      tempStart.add(Calendar.DATE, 1);
    }
    return historicalPrices;
  }

  /**
   * Return true if there is trending up for this stock within the given day range.
   *
   * @param startDate given start date
   * @param endDate   given end date
   * @return true if this stock trends up, otherwise false
   * @throws IllegalArgumentException if endDate is a date before the start date
   */
  @Override
  public boolean trendUpPerStock(Date startDate, Date endDate) throws IllegalArgumentException {
    if (endDate.before(startDate) || endDate.equals(startDate)) {
      throw new IllegalArgumentException("End date must be a date after the start date!");
    }
    if (!this.history.containsKey(startDate) || !this.history.containsKey(endDate)) {
      throw new IllegalArgumentException("Start Date or End Date is not a valid business day!");
    }
    return trendSlope(startDate, endDate) >= 0;
  }


  /**
   * Returns the slope of prices between two given dates.
   *
   * @param startDate given start date.
   * @param endDate   given end date.
   * @return double that represents the slope of a line between the prices on two given days.
   */
  private double trendSlope(Date startDate, Date endDate) {
    return (history.get(endDate) - history.get(startDate)) /
            ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
  }


}
