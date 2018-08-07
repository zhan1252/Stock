import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import basket.IBasket;
import basket.IBasketImpl;
import history.IHistory;
import history.IHistoryImpl;
import history.TickerSymbol;
import model.IStockModel;
import model.IStockModelImpl;

import static model.IStockModel.historicalPriceToString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * This class represents a JUnit test for IStockModel.
 */
public class IStockModelTest {
  private IStockModel model1;
  private Date date1;
  private Date date2;
  private Date date3;

  /**
   * Set up Before scenario.  Create a history, basket, and model implementation.
   */
  @Before
  public void setUp() {
    IBasket basket1 = new IBasketImpl();
    basket1.add(TickerSymbol.MSFT, 2);
    basket1.add(TickerSymbol.IBM, 1);
    model1 = new IStockModelImpl(basket1);
    model1.addIHistory(TickerSymbol.MSFT);
    model1.addIHistory(TickerSymbol.IBM);

    date1 = parseDate("2018-07-03");
    date2 = parseDate("2018-05-09");
    date3 = parseDate("2018-07-04");

  }

  //changed addIhistory to only take in tickersymbol

  /**
   * Test that the priceOfDay method works for Microsoft.
   */
  @Test
  public void testPriceOfDayMsft() {
    assertEquals(99.05, model1.priceOfDay(TickerSymbol.MSFT, date1), 10E-2);
  }

  /**
   * Test that the priceOfDay method works for IBM.
   */
  @Test
  public void testPriceOfDayIbm() {
    assertEquals(139.57, model1.priceOfDay(TickerSymbol.IBM, date1), 10E-2);
  }

  /**
   * Test that an IllegalArgumentException is thrown if there is no price for a given date
   * for microsoft.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalPriceOfDay() {
    model1.priceOfDay(TickerSymbol.MSFT, date3);
  }

  /**
   * Test that an IllegalArgumentException is thrown if there is no price for a given date
   * for IBM.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalPriceOfDayApple() {
    model1.priceOfDay(TickerSymbol.AAPL, date1);
  }

  /**
   * Test the buying opportunity for microsoft.
   */
  @Test
  public void testBuyOpportunity() {
    //50day average: 98.27      200day average: 89.46
    assertTrue(model1.buyOpportunity(TickerSymbol.MSFT, date1));
  }

  /**
   * Test the buying opportunity for IBM.
   */
  @Test
  public void testBuyOpportunity2() {
    //50day average: 143.46   200day average: 151.53
    assertFalse(model1.buyOpportunity(TickerSymbol.IBM, date1));
  }


  /**
   * Test that false returned for Buying opportunity if date doesn't exist in history.
   */
  @Test
  public void testIllegalBuyOpportunity() {
    assertFalse(model1.buyOpportunity(TickerSymbol.MSFT, date3));
  }

  /**
   * Test Illegal Buying opportunity if ticket symbol doesn't exist in database of history.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBuyOpportunity2() {
    model1.buyOpportunity(TickerSymbol.AAPL, date1);
  }


  /**
   * Test StockHistory for IBM between two dates if no dates are between date range.
   */
  @Test
  public void testStockHistory() {
    String str0 = "No prices are contained within this day range";
    Date date3 = parseDate("2018-07-04");

    assertEquals(str0, historicalPriceToString(model1.stockHistory(TickerSymbol.IBM,
            date3, date3)));
  }

  /**
   * Test StockHistory for IBM between two different dates.
   */
  @Test
  public void testStockHistory2() {
    String str1 = "Date: Thu Jul 05 00:00:00 EDT 2018 Price: 141.4\n"
            + "Date: Fri Jul 06 00:00:00 EDT 2018 Price: 142.5\n"
            + "Date: Mon Jul 09 00:00:00 EDT 2018 Price: 144.4\n";
    Date date4 = parseDate("2018-07-05");
    Date date5 = parseDate("2018-07-09");
    assertEquals(str1, historicalPriceToString(model1.stockHistory(TickerSymbol.IBM,
            date4, date5)));
  }

  /**
   * Test if Dates are not in the correct order for IllegalStockHistory.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStockHistory() {
    Date date4 = parseDate("2018-07-05");
    Date date5 = parseDate("2018-07-09");
    model1.stockHistory(TickerSymbol.MSFT, date5, date4);
  }

  /**
   * Test if ticket symbol doesn't exist in database of history for stockHistory.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStockHistory2() {
    model1.stockHistory(TickerSymbol.AAPL, date2, date1);
  }


  /**
   * Test totalPrice for a basket.
   */
  @Test
  public void testTotalPrice() {
    assertEquals(337.67, model1.totalPrice(date1), 10E-2);
  }

  /**
   * Test totalPrice if date doesn't exist in history.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalTotalPrice() {
    model1.totalPrice(date3);
  }

  /**
   * Test stockTrendUp if it is true.
   */
  @Test
  public void testStockTrendUpTrue() {
    assertTrue(model1.stockTrendUp(TickerSymbol.MSFT, date2, date1));
  }

  /**
   * Test stockTrendUp if it is false.
   */
  @Test
  public void testStockTrendUpFalse() {
    assertFalse(model1.stockTrendUp(TickerSymbol.IBM, date2, date1));
  }

  /**
   * Test if IllegalArgumentException thrown if dates not given in correct order for
   * stockTrendUp.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStockTrendUp() {
    model1.stockTrendUp(TickerSymbol.MSFT, date1, date2);
  }

  /**
   * Test if IllegalArgumentException thrown if one of the dates is not in the history for
   * stockTrendUp.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStockTrendUp2() {
    model1.stockTrendUp(TickerSymbol.MSFT, date3, date1);
  }

  /**
   * Test if IllegalArgumentException thrown if tickerSymbol doesn't exist in database of history.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStockTrendUp3() {
    model1.stockTrendUp(TickerSymbol.AAPL, date2, date1);
  }


  /**
   * Test BasketTrendUp if true.
   */
  @Test
  public void testBasketTrendUp() {
    assertTrue(model1.basketTrendUp(date2, date1));
  }

  /**
   * Test that IllegalArgumentException is thrown if dates are not in the correct order for
   * basketTrendUp.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBasketTrendUp() {
    Date date4 = parseDate("2018-07-05");
    Date date5 = parseDate("2018-07-09");
    model1.basketTrendUp(date5, date4);
  }

  /**
   * Test that IllegalArgumentException is thrown for BasketTrendUp if date if not in history.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalBasketTrendUp2() {
    Date date4 = parseDate("2018-07-05");
    model1.basketTrendUp(date3, date4);

  }

  /**
   * Parse a String to date.
   *
   * @param date given date
   * @return date in string format
   */
  public static Date parseDate(String date) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(date);

    } catch (ParseException e) {
      return null;
    }
  }
}