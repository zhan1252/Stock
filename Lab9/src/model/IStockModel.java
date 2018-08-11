package model;

import java.util.Date;
import java.util.List;

import history.TickerSymbol;
import javafx.util.Pair;

/**
 * This interface represents an IStockModel, which allows a user to find the price of a
 * stock on a day, add the history of a stock, determine the buying opportunity of a stock,
 * find the history of a stock, return the total price of a basket on a day, and determine
 * if the basket is trending up.
 */
public interface IStockModel {

  /**
   * Add the history of a given stock to the database.
   *
   * @param tickerSymbol given ticker symbol
   * @throws IllegalArgumentException if history is null
   */
  void addIHistory(TickerSymbol tickerSymbol) throws IllegalArgumentException;

  /**
   * Retrieve the price of a stock on a given date.
   *
   * @param tickerSymbol given ticker symbol
   * @param date         given date
   * @return the closing price of a stock on a given day
   * @throws IllegalArgumentException if ticker symbol is not in the database
   * @throws IllegalArgumentException if date is not a valid business day
   */
  double priceOfDay(TickerSymbol tickerSymbol, Date date) throws IllegalArgumentException;

  /**
   * Determine whether there is a buying opportunity for the given stock on given day.
   *
   * @param tickerSymbol given tickerSymbol
   * @param date         given date
   * @return true if there is a buying opportunity, otherwise false
   * @throws IllegalArgumentException if ticker symbol is not in the database
   * @throws IllegalArgumentException if date is not a valid business day
   */
  boolean buyOpportunity(TickerSymbol tickerSymbol, Date date) throws IllegalArgumentException;

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
  List<Pair<Date, Double>> stockHistory(TickerSymbol tickerSymbol, Date startDate, Date endDate)
          throws IllegalArgumentException;

  /**
   * Determines the total price of a basket on a given date.
   *
   * @param date given date
   * @throws IllegalArgumentException if ticker symbol is not in the database
   * @throws IllegalArgumentException if date is not a valid business day
   */
  double totalPrice(Date date) throws IllegalArgumentException;


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
  boolean stockTrendUp(TickerSymbol tickerSymbol, Date startDate, Date endDate)
          throws IllegalArgumentException;

  /**
   * Returns true if the price of a basket on a start date is lower than the price of the basket
   * on an end date.
   *
   * @param startDate given start date
   * @param endDate   given end date
   * @throws IllegalArgumentException if either date is not a valid business day
   */
  boolean basketTrendUp(Date startDate, Date endDate) throws IllegalArgumentException;


  /**
   * Static method that converts historical prices to string.
   *
   * @param historicalPrices given list of historical prices
   * @return historical prices in string format
   */
  static String historicalPriceToString(List<Pair<Date, Double>> historicalPrices) {
    String result = "";
    if (historicalPrices.size() == 0 || historicalPrices == null) {
      result += "No prices are contained within this day range";
      return result;
    }
    for (Pair<Date, Double> historicalPrice : historicalPrices) {
      result += "Date: " + historicalPrice.getKey() + " Price: "
              + String.format("%.1f", historicalPrice.getValue()) + "\n";
    }
    return result;
  }

  /**
   * Return the max price of a symbol.
   *
   * @param symbol    given symbol.
   * @param startDate given start date.
   * @param endDate   given end date.
   * @return maximum price in date range.
   */
  double maxPrice(TickerSymbol symbol, Date startDate, Date endDate);

  /**
   * Return the minimum price of a symbol.
   *
   * @param symbol    given symbol.
   * @param startDate given start date.
   * @param endDate   given end date.
   * @return minimum price in date range.
   */
  double minPrice(TickerSymbol symbol, Date startDate, Date endDate);


}
