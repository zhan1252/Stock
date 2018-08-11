package view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JScrollPane;

import history.TickerSymbol;
import javafx.util.Pair;

/**
 * Represents a view that gives a graph output of a stock in a given date range.
 */
public class View extends JFrame implements IView {

  int width = 550;
  int height = 550;
  TickerSymbol tickerSymbol;
  Date startDate;
  Date endDate;
  double minPrice;
  double maxPrice;
  List<Pair<Double, Double>> data;
  List<Pair<Double, Double>> buyOpportunity;

  /**
   * Constructs a view.
   *
   * @param symbol    given symbol.
   * @param startDate given start date.
   * @param endDate   given end date.
   * @throws IllegalArgumentException if end date is before start date.
   */
  public View(TickerSymbol symbol, Date startDate, Date endDate)
          throws IllegalArgumentException {
    if (endDate.before(startDate)) {
      throw new IllegalArgumentException("Start date after end date");
    }
    this.tickerSymbol = symbol;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  /**
   * Set buying opportunity.
   *
   * @param data given data.
   */
  @Override
  public void setBuyOpportunity(List<Pair<Double, Double>> data) {
    this.buyOpportunity = data;
  }

  /**
   * Set data in view.
   *
   * @param data given data.
   */
  @Override
  public void setData(List<Pair<Double, Double>> data) {
    this.data = data;
  }

  /**
   * Get ticket symbol.
   *
   * @return ticker symbol.
   */
  @Override
  public TickerSymbol getSymbol() {
    return this.tickerSymbol;
  }

  /**
   * Get start date in given range.
   *
   * @return start date.
   */
  @Override
  public Date getStartDate() {
    return this.startDate;
  }

  /**
   * Get end date in given range.
   *
   * @return end date.
   */
  @Override
  public Date getEndDate() {
    return this.endDate;
  }

  /**
   * Set the minimum price.
   *
   * @param minPrice given minimum price.
   */
  @Override
  public void setMinPrice(double minPrice) {
    this.minPrice = minPrice;
  }

  /**
   * Set maximum price.
   *
   * @param maxPrice given maximum price.
   */
  @Override
  public void setMaxPrice(double maxPrice) {
    this.maxPrice = maxPrice;
  }

  /**
   * Render the image.
   */
  @Override
  public void render() {

    JFrame frame = new JFrame();

    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(new Dimension(width, height));

    MyDrawingPanel drawingPanel = new MyDrawingPanel();
    drawingPanel.setData(this.data, this.startDate, this.endDate, this.minPrice, this.maxPrice
            , this.buyOpportunity);

    JScrollPane jScrollPane = new JScrollPane(drawingPanel);

    frame.add(jScrollPane);
    frame.setVisible(true);

  }


}
