package stockapp;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import history.TickerSymbol;

/**
 * This is a StockApplication class that uses an API to pull historical pricing data
 * for a given tickerSymbol.
 */
public class StockApplication {


  /**
   * Given a tickerSymbol, this pulls the pricing history of a given stock and stores the
   * information in a hashmap hasing a Date to the closing price.
   *
   * @param tickerSymbol given tickerSymbol.
   * @return hashmap of date,double
   * @throws Exception if API fails
   */
  public static HashMap<Date, Double> makeHistory(TickerSymbol tickerSymbol) throws Exception {
    URL url;

    /**
     The URL below is to access the Alpha Vantage API.
     1. Run this program to see its outputsize
     2. Go to https://www.alphavantage.co/documentation/
     3. Try some of their examples using a web browser or replacing the URL below with it
     4. You will need to get your own API key. Look at the documentation web page for instructions
     5. Look at the format of the data it sends in response. Think about how you would parse
     this data (divide it into chunks so that you can read and make sense of it)
     6. Design wrappers around this service so that it helps your model
     key 5FXWZ6AFS647GIUC
     */

    /**
     * historical data from vantage API.
     * */

    String temp1 = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="
            + tickerSymbol + "&outputsize=full&apikey=5FXWZ6AFS647GIUC&datatype=csv";



    url = new URL(temp1);


    InputStream in;

    /**
     The line below "sends" this URL and receives a response, as an InputStream object.

     This is equivalent to pasting this URL in a web browser and having the browser
     show you what it got in return.

     Thus the line below is basically how you can do within a Java program what  your
     web browser would normally do (communicate with another server, and show the output
     it received).
     */
    in = url.openStream();

    /**
     Read this output as a string, word-by-word. You don't have to read it word by word
     necessarily, but doing so here as an example.
     */
    Scanner sc = new Scanner(in);
    sc.useDelimiter(",|\\n");

    HashMap<Date, Double> newHistory = new HashMap<>();
    sc.nextLine();


    while (sc.hasNext()) {
      String tempDate = sc.next();
      DateFormat tempFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date date = tempFormat.parse(tempDate);

      int counter = 0;
      while (counter < 3) {
        sc.next();
        counter++;
      }

      String price = sc.next();
      double priceFinal = Float.parseFloat(price);

      newHistory.put(date, priceFinal);
      sc.next();

    }
    return newHistory;


  }

}

