package test1;

public class TradeType {
    private String withdrawType;
    private double value;
    public TradeType(String type,double val){
        this.withdrawType = type;
        this.value = val;
    }

    public String getWithdrawType() {
        return withdrawType;
    }

    public double getValue() {
        return value;
    }
}
