package model;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import basket.IBasket;
import history.IHistory;
import history.IHistoryImpl;
import history.TickerSymbol;
import javafx.util.Pair;

/**
 * This class represents a model for storing and retrieving stock information by implementing the
 * IStockModel interface.
 */
public class IStockModelImpl implements IStockModel {
  private HashMap<TickerSymbol, IHistory> histories;
  private IBasket basket;

  /**
   * Constructor for IStockModelImpl class.  This class takes in a basket of stock and
   * creates a new hashmap.
   *
   * @param basket given basket of stocks.
   */
  public IStockModelImpl(IBasket basket) {
    this.histories = new HashMap<>();
    this.basket = basket;
  }

  /**
   * Add the history of a given stock to the database.
   *
   * @param tickerSymbol given ticker symbol
   * @throws IllegalArgumentException if history is null
   */
  @Override
  public void addIHistory(TickerSymbol tickerSymbol) {

    IHistory history = new IHistoryImpl(tickerSymbol);
    this.histories.put(tickerSymbol, history);

  }

  /**
   * Retrieve the price of a stock on a given date.
   *
   * @param tickerSymbol given ticker symbol
   * @param date         given date
   * @return the closing price of a stock on a given day
   * @throws IllegalArgumentException if ticker symbol is not in the database
   * @throws IllegalArgumentException if date is not a valid business day
   */
  @Override
  public double priceOfDay(TickerSymbol tickerSymbol, Date date) {
    this.throwIllegalTickerSymbol(tickerSymbol);
    return this.histories.get(tickerSymbol).priceOnDay(date);
  }

  /**
   * Determine whether there is a buying opportunity for the given stock on given day.
   *
   * @param tickerSymbol given tickerSymbol
   * @param date         given date
   * @return true if there is a buying opportunity, otherwise false
   * @throws IllegalArgumentException if ticker symbol is not in the database
   * @throws IllegalArgumentException if date is not a valid business day
   */
  @Override
  public boolean buyOpportunity(TickerSymbol tickerSymbol, Date date) {
    this.throwIllegalTickerSymbol(tickerSymbol);
    return this.histories.get(tickerSymbol).buyOpportunity(date);
  }

  /**
   * Return a list of IStock on within given range of time.
   *
   * @param tickerSymbol given ticker symbol
   * @param startDate    given start date
   * @param endDate      given end date
   * @return a list of all stocks within the two dates
   * @throws IllegalArgumentException if ticker symbol is not in the database
   * @throws IllegalArgumentException if start date is after the end date
   */
  @Override
  public List<Pair<Date, Double>> stockHistory(TickerSymbol tickerSymbol, Date startDate,
                                               Date endDate) throws IllegalArgumentException {
    this.throwIllegalTickerSymbol(tickerSymbol);
    return this.histories.get(tickerSymbol).historicalPrices(startDate, endDate);
  }

  /**
   * Determines the total price of a basket on a given date.
   *
   * @param date given date
   * @throws IllegalArgumentException if ticker symbol is not in the database
   * @throws IllegalArgumentException if date is not a valid business day
   */
  @Override
  public double totalPrice(Date date) {
    return basket.basketValue(date, histories);
  }

  /**
   * Returns true if the price of a stock on the end date is higher than the price of a stock
   * on the start date.
   *
   * @param tickerSymbol ticker symbol of the history to be found
   * @param startDate    given start date
   * @param endDate      given end date
   * @return true if this stock is trending up, otherwise false.
   * @throws IllegalArgumentException if ticker symbol is not in the database
   * @throws IllegalArgumentException if either date is not a valid business day
   */
  @Override
  public boolean stockTrendUp(TickerSymbol tickerSymbol, Date startDate, Date endDate) {
    this.throwIllegalTickerSymbol(tickerSymbol);
    return this.histories.get(tickerSymbol).trendUpPerStock(startDate, endDate);
  }

  /**
   * Returns true if the price of a basket on a start date is lower than the price of the basket
   * on an end date.
   *
   * @param startDate given start date
   * @param endDate   given end date
   * @throws IllegalArgumentException if either date is not a valid business day
   */
  @Override
  public boolean basketTrendUp(Date startDate, Date endDate) {
    return basket.trendUp(startDate, endDate, this.histories);
  }

  /**
   * Effectuate throwing of exception if tickerSymbol is not in the database.
   *
   * @param tickerSymbol given ticker symbol
   * @throws IllegalArgumentException if ticker symbol is not in the database
   */
  private void throwIllegalTickerSymbol(TickerSymbol tickerSymbol)
          throws IllegalArgumentException {
    if (!histories.containsKey(tickerSymbol)) {
      throw new IllegalArgumentException("this stock is not contained in the database.");
    }
  }

  /**
   * Returns maximum price of symbol in date range.
   *
   * @param symbol    given symbol.
   * @param startDate given start date.
   * @param endDate   given end date.
   * @return maximum price.
   */
  @Override
  public double maxPrice(TickerSymbol symbol, Date startDate, Date endDate) {

    double maxPrice = 0;
    Calendar tempStart = Calendar.getInstance();
    tempStart.setTime(startDate);
    double temp = 0;

    while (tempStart.getTime().before(endDate) || tempStart.getTime().equals(endDate)) {
      try {
        temp = this.priceOfDay(symbol, tempStart.getTime());
      } catch (Exception e) {
      // empty because we don't need it to do anything
      }
      if (temp > maxPrice) {
        maxPrice = temp;
      }
      tempStart.add(Calendar.DATE, 1);
    }
    return maxPrice;
  }


  /**
   * Returns minimum price of symbol in range.
   *
   * @param symbol    given symbol.
   * @param startDate given start date.
   * @param endDate   given end date.
   * @return minimum price.
   */
  @Override
  public double minPrice(TickerSymbol symbol, Date startDate, Date endDate) {
    double minPrice = this.priceOfDay(symbol, startDate);
    Calendar tempStart = Calendar.getInstance();
    tempStart.setTime(startDate);
    double temp = this.priceOfDay(symbol, startDate);

    while (tempStart.getTime().before(endDate) || tempStart.getTime().equals(endDate)) {
      try {
        temp = this.priceOfDay(symbol, tempStart.getTime());
      } catch (Exception e) {
      //empty because we don't need it to do anything.
      }
      if (temp < minPrice) {
        minPrice = temp;
      }
      tempStart.add(Calendar.DATE, 1);
    }
    return minPrice;
  }


}


