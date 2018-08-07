package view;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

import javax.swing.*;

import javafx.util.Pair;

public class MyDrawingPanel extends JPanel {

  List<Pair<Double, Double>> data;
  int width = 500;
  int height = 500;

  //public MyDrawingPanel(List<Pair<Double, Double>> data) {
  //  this.data = data;
 // }

  public void setData(List<Pair<Double, Double>> data) {
    this.data = data;
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
    for(int i = 0; i < this.data.size()-2; ++i) {
      double x1 = this.data.get(i).getKey();
     // System.out.println(x1);
      double x2 = this.data.get(i+1).getKey();
   // System.out.println(x2);
      double y1 = changeScaleOnGraph(this.data.get(i).getValue());
    //System.out.println(y1);
      double y2 = changeScaleOnGraph(this.data.get(i+1).getValue());
    //System.out.println(y2);

      Line2D lin = new Line2D.Double(x1+50,y1,x2+50,y2); //shift right by 50 to make room for axis
      g2.draw(lin);

   }

   Line2D yAxis = new Line2D.Double(50,0,50,500);
    g2.draw(yAxis);
    Line2D xAxis = new Line2D.Double(50,500,550,500);
    g2.draw(xAxis);





    }



}
