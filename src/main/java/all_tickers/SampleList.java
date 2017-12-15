package all_tickers;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import quickfix.field.Price;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class SampleList {

    private List<PriceSample> samples;
//    private String pairId;
    private CurrencyPair currencyPair;

    
    public SampleList(CurrencyPair currencyPair) {
        samples = new ArrayList<PriceSample>();
        this.currencyPair = currencyPair;
    }

    public boolean analyze()
    {
    	PriceSample min_ps = new PriceSample();
    	min_ps.setPrice(BigDecimal.valueOf(Double.MAX_VALUE));
    	for (PriceSample ps : samples)
    	{
    		if ( min_ps.getPrice().compareTo(ps.getPrice()) >= 0  )
    		{
    			min_ps =  ps;
    		}
    	}
    	
    	PriceSample max_ps = new PriceSample();
    	max_ps.setPrice(BigDecimal.ZERO);
    	for (int i = samples.indexOf(min_ps); i < samples.size(); i++) {
    		if ( max_ps.getPrice().compareTo(samples.get(i).getPrice()) < 0 )
    		{
    			max_ps = samples.get(i);
    		}
    	}
    	
    	double delta_incline = 0;
    	if ( max_ps.getPrice().compareTo(min_ps.getPrice()) >= 0 )
    	{
    		delta_incline = (((max_ps.getPrice().doubleValue() -  min_ps.getPrice().doubleValue()) * 100 ) / min_ps.getPrice().doubleValue());
    	}
    	
    	//System.out.println("Min value for "+currencyPair+" is " + min_ps.getPrice() + " " + min_ps.getLocalTime());
    	//System.out.println("Max value for "+currencyPair+" is " + max_ps.getPrice() + " " + max_ps.getLocalTime());
    	System.out.println("Delta value for "+currencyPair+" is " + delta_incline + " %");
    	DatabaseManager.getInstance().addDelta(currencyPair, delta_incline);
    	return true;
    }
    
    public void addSamples(List<PriceSample> psList) {
        samples.addAll(psList);
        shrinkList();
    }

    public void addSample(PriceSample ps) {
        samples.add(ps);
        shrinkList();
    }

    public void addSample(Ticker ticker) {

        PriceSample ps = new PriceSample();
        ps.setTimeStamp(ticker.getTimestamp());
        ps.setLocalTime(new Date());
        ps.setPrice(ticker.getLast());
        ps.setPairId(ticker.getCurrencyPair().toString());
        samples.add(ps);
        //System.out.println(ps);
        shrinkList();
    }
    private void shrinkList() {

        Long currentMilis = new Date().getTime();
        Long thresholdMilis = currentMilis - 5 * 60 * 1000;
        //System.out.println("Shrinking list... Initial size: " + samples.size());
        //System.out.println("Deleting older than: " + new Date(thresholdMilis) + ", Current time: " + new Date(currentMilis));

        Iterator<PriceSample> it = samples.iterator();
        while (it.hasNext()) {
            PriceSample ps = it.next();
            Long compareDate = ps.getLocalTime().getTime();
            if (compareDate < thresholdMilis) {
                it.remove();
                //System.out.println("Timestamp: " + new Date(compareDate) + " difference(milis):" + (thresholdMilis - compareDate)/1000);
            }
            else {
                break;
            }
        }
        //System.out.println("Shrinking finished... New size: " + samples.size());
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    @Override
    public String toString() {
        return "\nSampleList{" +
                "currencyPair=" + currencyPair + "," +
                "\n\tsamples=" + samples +
                '}';
    }

    //    public String getPairId() {
//        return pairId;
//    }
//
//    public void setPairId(String pairId) {
//        this.pairId = pairId;
//    }
}
