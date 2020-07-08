package test1;


import java.util.HashMap;

public class AccountManage {
    private static HashMap<String,Account> account = new HashMap<>();
    public static void registerAccount(String ac){
        account.put(ac,new Account(ac));
    }

    public static Account getAccount(String ac){
        return account.get(ac);
    }
}
