package view;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Date;
import java.util.List;

import javax.swing.*;

import javafx.util.Pair;

public class MyDrawingPanel extends JPanel {

  List<Pair<Double, Double>> data;
  List<Pair<Double, Double>> buyOpportunity;
  int width = 500;
  int height = 500;
  Date minDate;
  Date maxDate;
  double minStock;
  double maxStock;

  //public MyDrawingPanel(List<Pair<Double, Double>> data) {
  //  this.data = data;
 // }

  public void setData(List<Pair<Double, Double>> data, Date startDate, Date endDate
  ,double minPrice, double maxPrice, List<Pair<Double, Double>> buyOpportunity) {
    this.data = data;
    this.buyOpportunity = buyOpportunity;
    this.maxDate = endDate;
    this.minDate = startDate;
    this.minStock = minPrice;
    this.maxStock = maxPrice;
    setPreferredSize(new Dimension(490,490));
  }

  private double changeScaleOnGraph(double y) {
    return height-y;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
   // super.paint(g);
    //g.drawRect(10,10,100,100);
    //g.setColor(Color.green);
    //g.fillRect(10,10,100,100);

    Graphics2D g2 = (Graphics2D) g;

    //for trend
    double xStart = 0;
    double yStart = 0;
    double xEnd = 0;
    double yEnd = 0;

    for(int i = 0; i < this.buyOpportunity.size(); ++i) {
      double x1 = this.buyOpportunity.get(i).getKey();
      double y1 = changeScaleOnGraph(this.buyOpportunity.get(i).getValue());

      g2.fillOval((int)x1+50,(int)y1,5,5);

    }

    for(int i = 0; i < this.data.size()-1; ++i) {
      double x1 = this.data.get(i).getKey();

     // System.out.println(x1);
      double x2 = this.data.get(i+1).getKey();
   // System.out.println(x2);
      double y1 = changeScaleOnGraph(this.data.get(i).getValue());
    //System.out.println(y1);
      double y2 = changeScaleOnGraph(this.data.get(i+1).getValue());
    //System.out.println(y2);

      if(i== 0){
        xStart = x1 + 50;//shift right by 50 to make room for axis
        yStart = y1 ;
      }

      if(i == this.data.size()-2){
        xEnd = x2 + 50; //shift right by 50 to make room for axis
        yEnd = y2;
      }

      Line2D lin = new Line2D.Double(x1+50,y1,x2+50,y2); //shift right by 50 to make room for axis
      g2.draw(lin);

   }




    Line2D yAxis = new Line2D.Double(50,0,50,500);
    g2.draw(yAxis);
    Line2D xAxis = new Line2D.Double(50,500,550,500);
    g2.draw(xAxis);


    g2.drawString(minDate.toString(), 10, 520);
    g2.drawString(maxDate.toString(), 490, 520);
    g2.drawString(""+(int)minStock, 5, 500);
    g2.drawString(""+(int)maxStock, 5, 10);
    g2.fillOval(340,12,7,7);
    g2.drawString(" indicates Buying Opportunity",350,20);

    Stroke dashed = new BasicStroke(3,BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
            new float[]{9},0);
    g2.setStroke(dashed);
    g2.drawLine((int)xStart, (int)yStart, (int)xEnd, (int)yEnd);








    }



}
