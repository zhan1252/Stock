package view;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import basket.IBasket;
import basket.IBasketImpl;
import controller.Controller;
import controller.IController;
import history.TickerSymbol;
import model.IStockModel;
import model.IStockModelImpl;

import static org.junit.Assert.*;

public class IViewTest {

  @Test
  public void test() {
    Date date1 = parseDate("2018-05-09");
    Date date2 = parseDate("2018-07-03");
    IBasket basket = new IBasketImpl();
    IStockModel model = new IStockModelImpl(basket);

    //this is wrong- figure out how to get ticketSymbol and dates in
    IView view = new View(TickerSymbol.MSFT,date1, date2);
    IController controller = new Controller();
   // controller.start(model,view);



  }


  @Test
  public void test2() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    panel.setVisible(true);

  }



  /**
   * Parse a String to date.
   *
   * @param date given date
   * @return date in string format
   */
  private static Date parseDate(String date) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(date);

    } catch (ParseException e) {
      return null;
    }
  }

}