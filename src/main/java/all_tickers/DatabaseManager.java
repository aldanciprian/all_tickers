package all_tickers;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;


public class DatabaseManager {
	private static DatabaseManager instance = null;
    public Connection conn;
    public Statement stmt;   
    public SimpleDateFormat dateFormat;
    protected DatabaseManager() {
        // Exists only to defeat instantiation.
	  try {
		conn = DriverManager.getConnection("jdbc:mysql://localhost/bitfinex?useSSL=false&" + "user=ciprian&password=ciprian");
		stmt = conn.createStatement();		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  dateFormat = new SimpleDateFormat("yyyy-MM-dd_kk-mm-ss");
    }
    
	
    public static DatabaseManager getInstance() {
        if(instance == null) {
           instance = new DatabaseManager();
        }
        return instance;
     }
    public boolean createTickersTables (List<CurrencyPair> tickers)
    {
    	for ( CurrencyPair cp: tickers)
    	{
    		String ticker_text = cp.toString().replaceAll("/", "_");
	       	 try {
	       		//System.out.println("CREATE TABLE IF NOT EXISTS "+ticker_text+" (symbol VARCHAR(40) UNIQUE) ");
	 			stmt.execute("CREATE TABLE IF NOT EXISTS "+ticker_text+" (symbol VARCHAR(40) UNIQUE) ");
	 			stmt.execute("CREATE TABLE IF NOT EXISTS tickers_" + ticker_text + " (tstmp VARCHAR(40),last DOUBLE,bid DOUBLE,ask DOUBLE,high DOUBLE,low DOUBLE,volume DECIMAL(30,15)) ");
	 		} catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
    	}
    	return true;
    }
    public boolean createTickersDeltaTables (List<CurrencyPair> tickers)
    {
    	for ( CurrencyPair cp: tickers)
    	{
    		String ticker_text = cp.toString().replaceAll("/", "_")+"_delta";
	       	 try {
	       		//System.out.println("CREATE TABLE IF NOT EXISTS "+ticker_text+" (symbol VARCHAR(40) UNIQUE) ");
	 			stmt.execute("CREATE TABLE IF NOT EXISTS "+ticker_text+" (tstmp VARCHAR(40),delta DOUBLE) ");
	 		} catch (SQLException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
    	}
    	return true;
    }
    
    public boolean addDelta(CurrencyPair cp,double delta)
    {
    	String ticker_text = cp.toString().replaceAll("/", "_")+"_delta";
    	try {
			stmt.execute("INSERT INTO " + ticker_text + " (tstmp,delta) values ('"+dateFormat.format(new Date())+"',"+delta+")" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return true;
    }
    
    public boolean addTicker(Ticker ticker,CurrencyPair pair) {
        try {
			System.out.println(ticker.toString()+ " " + pair.toString());
            String ticker_text = pair.toString().replaceAll("/", "_");
            StringBuilder strb = new StringBuilder();
            strb.append("INSERT INTO tickers_" + ticker_text + " (tstmp,last,bid,ask,high,low,volume) values ('");
            strb.append(dateFormat.format(new Date()));
            strb.append("',");
            strb.append(ticker.getLast());
            strb.append(",");
            strb.append(ticker.getBid());
            strb.append(",");
            strb.append(ticker.getAsk());
            strb.append(",");
            strb.append(ticker.getHigh());
            strb.append(",");
            strb.append(ticker.getLow());
            strb.append(",");
            strb.append(ticker.getVolume());
            strb.append(")");

            //System.out.println(strb.toString());
            stmt.execute(strb.toString());
            // );

            // stmt.close();
        }
        // catch (SQLException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // // handle any errors
        // System.out.println("SQLException: " + e.getMessage());
        // System.out.println("SQLState: " + e.getSQLState());
        // System.out.println("VendorError: " + e.getErrorCode());
        // }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }    
    
    
    public void close() {
    	try {
    		stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        instance = null;
     }    
    
	
}