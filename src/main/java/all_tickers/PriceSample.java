package all_tickers;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class PriceSample {

    private String pairId;

    private Date timeStamp;
    private Date localTime;
    private BigDecimal price;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPairId() {
        return pairId;
    }

    public void setPairId(String pairId) {
        this.pairId = pairId;
    }

    @Override
    public String toString() {
        return "\n\t\t\tPriceSample[" + pairId + "]{" +
                ", timeStamp=" + timeStamp +
                ", localtime=" + localTime +
                ", price=" + price +
                '}';
    }

    public Date getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Date localTime) {
        this.localTime = localTime;
    }
}
