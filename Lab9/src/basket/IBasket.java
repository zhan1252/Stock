package basket;

import java.util.Date;
import java.util.HashMap;

import history.IHistory;
import history.TickerSymbol;

/**
 * This interface presents all stocks held by a user and the total value of the stock.
 */
public interface IBasket {

  /**
   * Adds given number of stocks of a given company to the portfolio. If the company is already
   * there, sum up the quantity. If not, just adding a new entry to the collection.
   *
   * @param tickerSymbol given ticker symbol of the stock
   * @param quantity     given number of stocks
   */
  void add(TickerSymbol tickerSymbol, int quantity);


  /**
   * Calculates the total value of the stock basket held by one user on the given date.
   *
   * @param date      given date
   * @param histories given histories
   * @return the total value
   * @throws IllegalArgumentException if history is null or empty or there is no value for day.
   */
  double basketValue(Date date, HashMap<TickerSymbol, IHistory> histories)
          throws IllegalArgumentException;

  /**
   * Return true if there is trending up for the stock portfolio for the user within the given day
   * range.
   *
   * @param startDate given start date
   * @param endDate   given end date
   * @param histories given histories
   * @return true if this stock trends up, otherwise false
   * @throws IllegalArgumentException if endDate is a date before the start date
   */
  boolean trendUp(Date startDate, Date endDate, HashMap<TickerSymbol, IHistory> histories);


}
