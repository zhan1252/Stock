package basket;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import history.IHistory;
import history.TickerSymbol;

/**
 * This class represents all stocks name and quantities held by a user.
 */
public class IBasketImpl implements IBasket {

  private final HashMap<TickerSymbol, Integer> portfolio;

  /**
   * Constructs an IBackstImpl by creating an empty hashmap of TicketSymbol to Integer.
   * The Integer represents the quantity of that stock held by a user.
   */
  public IBasketImpl() {
    this.portfolio = new HashMap<>();
  }

  /**
   * Adds given number of stocks of a given company to the portfolio. If the company is already
   * there, sum up the quantity. If not, just adding a new entry to the collection.
   *
   * @param tickerSymbol given ticker symbol of the stock
   * @param quantity     given number of stocks
   */
  @Override
  public void add(TickerSymbol tickerSymbol, int quantity) throws IllegalArgumentException {
    if (portfolio.containsKey(tickerSymbol)) {
      portfolio.put(tickerSymbol, portfolio.get(tickerSymbol) + quantity);
    } else {
      portfolio.put(tickerSymbol, quantity);
    }
  }

  /**
   * Calculates the total value of the stock basket held by one user on the given date.
   *
   * @param date      given date
   * @param histories given histories
   * @return the total value
   * @throws IllegalArgumentException if history is null or empty or there is no value for day.
   */
  @Override
  public double basketValue(Date date, HashMap<TickerSymbol, IHistory> histories)
          throws IllegalArgumentException {
    if (histories == null || histories.size() == 0) {
      throw new IllegalArgumentException("histories is null or empty");
    }
    double sum = 0;
    for (Map.Entry<TickerSymbol, Integer> entry : this.portfolio.entrySet()) {
      if (!histories.containsKey(entry.getKey())) {
        throw new IllegalArgumentException("no history for this company!");
      }

      try {
        sum += histories.get(entry.getKey()).priceOnDay(date) * entry.getValue();
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("can not get the stock" + entry.getKey()
                + "on this day!");
      }
    }
    return sum;

  }

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
  @Override
  public boolean trendUp(Date startDate, Date endDate, HashMap<TickerSymbol, IHistory> histories)
          throws IllegalArgumentException {
    if (endDate.before(startDate)) {
      throw new IllegalArgumentException("End Date is Before Start Date.");
    }
    return this.basketValue(endDate, histories) > basketValue(startDate, histories);
  }


}
