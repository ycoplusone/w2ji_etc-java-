package test1;

import java.util.HashMap;

public class UserManage {
    private static HashMap<String,User> userData = new HashMap<>();

    private static HashMap<String,Admin> adminData = new HashMap<>();

    public static void register(Admin admin){
        adminData.put(admin.getId(),admin);
    }

    public static void register(User user){
        userData.put(user.getId(),user);
    }

    public static boolean isLoginSuccess(String user,String pw){
        User u = userData.get(user);
        if(u == null)
            return false;
        return u.getId().equals(user) && u.getPw().equals(pw);
    }

}
