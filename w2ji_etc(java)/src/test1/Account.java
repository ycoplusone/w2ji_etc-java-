package test1;
import java.util.ArrayList;
import java.util.List;

public class Account {
	
    private String accountNumber;
    private List<TradeType> logs = new ArrayList<>();
    private int accountBalance;
    private int perDay;
    private int left = 0;

    public Account(String numb){
        this.accountNumber = numb;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public double withdraw(String withdraw,int am){
        if(am <= accountBalance){
            accountBalance -= am;
            left -= am;
            logs.add(new TradeType(withdraw,am));
        }else
            System.out.println("잔액이 부족합니다.");
        return am;
    }

    public double deposit(int am){
        accountBalance += am;
        return accountBalance;
    }

    public List<TradeType> getLogs() {
        return logs;
    }

    public void calcPerDay() {
        perDay = getAccountBalance() / 30;
        left = perDay;
    }

    public int getPerDay() {
        return perDay;
    }

    public int getLeft() {
        return Math.max(0,left);
    }
}
