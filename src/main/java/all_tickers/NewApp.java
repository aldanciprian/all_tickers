package all_tickers;

import org.knowm.xchange.currency.CurrencyPair;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class NewApp {

    private static List<CurrencyPair> tickers = new ArrayList<CurrencyPair>();

    public static ObjectMapper json_mapper;
    public final static String symbols_URL = "https://api.bitfinex.com/v2/tickers?symbols=";
    static {
//        tickers.add(new CurrencyPair("BTG", "USD"));
//        //tickers.add(new CurrencyPair("RRT", "USD"));
//        tickers.add(new CurrencyPair("AVT", "USD"));
//        tickers.add(new CurrencyPair("QTM", "USD"));
//        tickers.add(new CurrencyPair("EDO", "USD"));
//        tickers.add(new CurrencyPair("ETP", "USD"));
//        tickers.add(new CurrencyPair("NEO", "USD"));
//        tickers.add(new CurrencyPair("SAN", "USD"));
//        tickers.add(new CurrencyPair("EOS", "USD"));
//        tickers.add(new CurrencyPair("DAT", "USD"));
//        tickers.add(CurrencyPair.BTC_USD);
//        tickers.add(CurrencyPair.LTC_USD);
//        tickers.add(CurrencyPair.ETH_USD);
//        tickers.add(CurrencyPair.ETC_USD);
//        tickers.add(CurrencyPair.ZEC_USD);
//        tickers.add(CurrencyPair.XMR_USD);
//        tickers.add(CurrencyPair.DASH_USD);
//        //tickers.add(CurrencyPair.BCC_USD);
//        tickers.add(CurrencyPair.XRP_USD);
//        tickers.add(CurrencyPair.IOTA_USD);
//        tickers.add(CurrencyPair.OMG_USD);
//        tickers.add(CurrencyPair.BCH_USD); 
        
        tickers.add(new CurrencyPair("BTC", "USD"));
        tickers.add(new CurrencyPair("LTC", "USD"));
        tickers.add(new CurrencyPair("ETH", "USD"));
        tickers.add(new CurrencyPair("ETC", "USD"));
        tickers.add(new CurrencyPair("RRT", "USD"));
        tickers.add(new CurrencyPair("ZEC", "USD"));
        tickers.add(new CurrencyPair("XMR", "USD"));
        tickers.add(new CurrencyPair("DSH", "USD"));
        tickers.add(new CurrencyPair("XRP", "USD"));
        tickers.add(new CurrencyPair("IOT", "USD"));
        tickers.add(new CurrencyPair("EOS", "USD"));
        tickers.add(new CurrencyPair("SAN", "USD"));
        tickers.add(new CurrencyPair("OMG", "USD"));
        tickers.add(new CurrencyPair("BCH", "USD"));
        tickers.add(new CurrencyPair("NEO", "USD"));
        tickers.add(new CurrencyPair("ETP", "USD"));
        tickers.add(new CurrencyPair("QTM", "USD"));
        tickers.add(new CurrencyPair("AVT", "USD"));
        tickers.add(new CurrencyPair("EDO", "USD"));
        tickers.add(new CurrencyPair("BTG", "USD"));
        tickers.add(new CurrencyPair("DAT", "USD"));
        tickers.add(new CurrencyPair("QSH", "USD"));
        tickers.add(new CurrencyPair("YYW", "USD"));
        tickers.add(new CurrencyPair("GNT", "USD"));
        tickers.add(new CurrencyPair("SNT", "USD"));
        tickers.add(new CurrencyPair("BAT", "USD"));
        tickers.add(new CurrencyPair("MNA", "USD"));
        tickers.add(new CurrencyPair("FUN", "USD"));
        tickers.add(new CurrencyPair("ZRX", "USD"));
        tickers.add(new CurrencyPair("TNB", "USD"));
        tickers.add(new CurrencyPair("SPK", "USD"));
        tickers.add(new CurrencyPair("TRX", "USD"));
        tickers.add(new CurrencyPair("RCN", "USD"));
        tickers.add(new CurrencyPair("RLC", "USD"));
        tickers.add(new CurrencyPair("AID", "USD"));
        tickers.add(new CurrencyPair("SNG", "USD"));
        tickers.add(new CurrencyPair("REP", "USD"));
        tickers.add(new CurrencyPair("ELF", "USD"));
        tickers.add(new CurrencyPair("IOS", "USD"));
        tickers.add(new CurrencyPair("AIO", "USD"));
        tickers.add(new CurrencyPair("REQ", "USD"));
        tickers.add(new CurrencyPair("RDN", "USD"));
        tickers.add(new CurrencyPair("LRC", "USD"));
        tickers.add(new CurrencyPair("WAX", "USD"));
        tickers.add(new CurrencyPair("DAI", "USD"));
        tickers.add(new CurrencyPair("CFI", "USD"));
        tickers.add(new CurrencyPair("AGI", "USD"));
        tickers.add(new CurrencyPair("BFT", "USD"));
        tickers.add(new CurrencyPair("MTN", "USD"));
        tickers.add(new CurrencyPair("ODE", "USD"));
        
    }

    
    public static String getHTML(String urlToRead) throws Exception {
    	System.out.println(urlToRead);
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
		} catch (IOException ioex) {
			//ioex.printStackTrace();
			System.out.println("error in getHTML");
			return null;
		}
        System.out.println(result.toString());
        return result.toString();
     }    
    
    
    public static void main(String args[]) {
    	DatabaseManager.getInstance().createTickersTables(tickers);
    	DatabaseManager.getInstance().createTickersDeltaTables(tickers);
    	
        TickerManager tickerManager = new TickerManager(tickers);
        tickerManager.init();
        
        json_mapper = new ObjectMapper();
        
//        System.out.println(tickerManager.returnAllTickers());
        while (true) {
        	String result = new String();
            //tickerManager.addTickers();
           // tickerManager.analyze();
        	
            try {
            	
    			result = getHTML(symbols_URL+tickerManager.returnAllTickers());
    			if ( result != null )
    			{
    				System.out.println("processing");
    				tickerManager.getTrades();
//    				System.out.println((json_mapper.readValue(result, Ticker_JSON[].class)).toString());
//    				System.out.println((json_mapper.readValue(result, new TypeReference<List<Ticker_JSON>>(){})).toString());    				
    			}
    			else
    			{
    				System.out.println("error from getHTML");
    			}
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } 
            catch (JsonParseException e) { e.printStackTrace();}
            catch (JsonMappingException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }            
            catch (Exception e) {
				// TODO Auto-generated catch block
            	System.out.println("default exception from main");
				e.printStackTrace();
			}
        }
    }
}