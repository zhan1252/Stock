
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import basket.IBasket;
import basket.IBasketImpl;
import controller.Controller;
import controller.IController;
import history.TickerSymbol;
import model.IStockModel;
import model.IStockModelImpl;
import view.IView;
import view.View;

public class Main {

  /**
   * Create model, view and controller and pass control to controller.
   *
   * @param args given input.
   */
  public static void main(String[] args) {

    Date date1 = parseDate("2018-05-09");
    Date date2 = parseDate("2018-07-03");
    IBasket basket = new IBasketImpl();
    IStockModel model = new IStockModelImpl(basket);

    IView view = new View(TickerSymbol.MSFT, date1, date2);
    IController controller = new Controller();
    controller.start(model, view);

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
