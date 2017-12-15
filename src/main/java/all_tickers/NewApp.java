package all_tickers;

import org.knowm.xchange.currency.CurrencyPair;

import java.util.ArrayList;
import java.util.List;

public class NewApp {

    private static List<CurrencyPair> tickers = new ArrayList<CurrencyPair>();

    static {
        tickers.add(new CurrencyPair("BTG", "USD"));
        //tickers.add(new CurrencyPair("RRT", "USD"));
        tickers.add(new CurrencyPair("AVT", "USD"));
        tickers.add(new CurrencyPair("QTM", "USD"));
        tickers.add(new CurrencyPair("EDO", "USD"));
        tickers.add(new CurrencyPair("ETP", "USD"));
        tickers.add(new CurrencyPair("NEO", "USD"));
        tickers.add(new CurrencyPair("SAN", "USD"));
        tickers.add(new CurrencyPair("EOS", "USD"));
        tickers.add(new CurrencyPair("DAT", "USD"));
        tickers.add(CurrencyPair.BTC_USD);
        tickers.add(CurrencyPair.LTC_USD);
        tickers.add(CurrencyPair.ETH_USD);
        tickers.add(CurrencyPair.ETC_USD);
        tickers.add(CurrencyPair.ZEC_USD);
        tickers.add(CurrencyPair.XMR_USD);
        tickers.add(CurrencyPair.DASH_USD);
        //tickers.add(CurrencyPair.BCC_USD);
        tickers.add(CurrencyPair.XRP_USD);
        tickers.add(CurrencyPair.IOTA_USD);
        tickers.add(CurrencyPair.OMG_USD);
        tickers.add(CurrencyPair.BCH_USD);        
    }

    public static void main(String args[]) {
    	DatabaseManager.getInstance().createTickersTables(tickers);
    	DatabaseManager.getInstance().createTickersDeltaTables(tickers);
    	
        TickerManager tickerManager = new TickerManager(tickers);
        tickerManager.init();
        while (true) {
            tickerManager.addTickers();
           // tickerManager.analyze();
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}