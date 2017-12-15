package all_tickers;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.ArrayList;
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
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}