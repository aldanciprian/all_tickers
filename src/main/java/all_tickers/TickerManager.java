package all_tickers;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TickerManager {

    public static Exchange bitfinex = null;

    private static MarketDataService marketDataService = null;
    private Ticker ticker = null;
    List<CurrencyPair> tickers = null;

    private List<SampleList> samplesList;

    public TickerManager(List<CurrencyPair> tickers) {
        samplesList = new ArrayList<SampleList>();
        this.tickers = tickers;

        for (CurrencyPair cp : tickers) {
            SampleList sl = new SampleList(cp);
            samplesList.add(sl);
        }
    }

    public void init() {
        bitfinex = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());
        marketDataService = bitfinex.getMarketDataService();
    }

    public void addTickers() {
        try {
             for (SampleList sl : samplesList) {
                ticker = marketDataService.getTicker(sl.getCurrencyPair());
                DatabaseManager.getInstance().addTicker(ticker,sl.getCurrencyPair());
            //    sl.addSample(ticker);
                 try {
                     Thread.sleep(2000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String returnAllTickers() {
    	String all_pairs = new String();
        for (SampleList sl : samplesList) {
	    	String v2_pair= "t".concat(sl.getCurrencyPair().base.toString());
	    	v2_pair = v2_pair.concat(sl.getCurrencyPair().counter.toString());
	    	all_pairs = all_pairs.concat(v2_pair);
	    	if (samplesList.indexOf(sl) != (samplesList.size() - 1 ))
	    	{
		    	all_pairs = all_pairs.concat(",");	    		
	    	}
        }
    	
        return all_pairs;
    }

    @Override
    public String toString() {
        return "TickerManager{" +
//                "ticker=" + ticker +
//                ", tickers=" + tickers +
                ", samplesList=" + samplesList +
                '}';
    }

    public void analyze() {
        for (SampleList sl : samplesList) {
            sl.analyze();
        }        
        
        
    }
    
	public void getTrades() {
		try {
			Date last_timestamp = new Date();
			for (SampleList sl : samplesList) {
				Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD,last_timestamp);
				for (Trade t : trades.getTrades()) {
					System.out.println(t.getTimestamp().toString() + " " + t.getPrice() + " " + t.getOriginalAmount());
					last_timestamp = t.getTimestamp();
				}
			System.out.println("Last timestamp is "+last_timestamp.getTime());
			Thread.sleep(3000);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}   
    
}