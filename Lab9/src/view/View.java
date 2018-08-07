package view;

import java.awt.*;
import java.util.Date;
import java.util.List;


import javax.swing.*;

import history.TickerSymbol;
import javafx.util.Pair;

public class View extends JFrame implements IView {

  int width = 550;
  int height = 550;
  TickerSymbol tickerSymbol;
  Date startDate;
  Date endDate;
  List<Pair<Double, Double>> data;


  public View(TickerSymbol symbol, Date startDate, Date endDate)
          throws IllegalArgumentException {
    //make sure you add date exceptions (if end date is before start date)
    this.tickerSymbol = symbol;
    this.startDate = startDate;
    this.endDate = endDate;
/*
    MyDrawingPanel drawingPanel = new MyDrawingPanel();
    drawingPanel.setData(this.data);

    setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(new Dimension(500,500));
    add(drawingPanel);
    setVisible(true);*/

  }


  @Override
  public void setData(List<Pair<Double, Double>> data) {
    this.data = data;
  }

  @Override
  public TickerSymbol getSymbol() {
    return this.tickerSymbol;
  }

  @Override
  public Date getStartDate() {
    return this.startDate;
  }

  @Override
  public Date getEndDate() {
    return this.endDate;
  }


  @Override
  public void render() {

    JFrame frame = new JFrame();

    frame.setLayout(new BorderLayout());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(new Dimension(width,height));

    MyDrawingPanel drawingPanel = new MyDrawingPanel();
    drawingPanel.setData(this.data);

    frame.add(drawingPanel);
    frame.setVisible(true);


  //  JFrame frame  = new JFrame();
   // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   // frame.add(drawingPanel);
    //frame.setSize(500,500);

  //  frame.setVisible(true);

  }




}
