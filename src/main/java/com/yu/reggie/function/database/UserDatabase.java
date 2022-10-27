package com.yu.reggie.function.database;


import com.yu.reggie.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase extends MyDatabase {

    protected UserDatabase() throws Exception {
        super();
    }

    public static ArrayList<User> getAllUsers() {
        List<List<String>> result = query("select * from users");
        if (!isResultValid(result)) {
            return null;
        }
        ArrayList<User> users = new ArrayList<User>();
        for (List<String> line : result.subList(1, result.size())) {
            users.add(new User(line));
        }
        return users;
    }

    //查询一个用户所有信息
    public static User getUser(int id) {
        List<List<String>> result = query(String.format("select * from users where u_id = '%d'", id));
        if (!isResultValid(result)) {
            return null;
        }
        for (List<String> line : result.subList(1, result.size())) {
            User user = new User(line);
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    //修改密码（通过输入原密码直接修改）
    public static boolean changePassword(int id, String new_password) {
        User user = getUser(id);
        if (user == null) {
            return false;
        }
        return nonQuery(String.format("update users set u_password = '%s' where u_id = %d and u_password = '%s'", new_password, user.getId(), user.getPassword()));
    }

    //通过用户名 手机号 邮箱 查找用户ID
    public static Object[] getUserIDByOthers(String name, String phone, String email) {
        List<List<String>> result = query(String.format("SELECT u_id,u_username FROM users WHERE `u_username`='%s' AND `u_phone`='%s' AND `u_email`='%s';", name, phone, email));
        if (isResultValid(result)) {

            return new Object[]{
                    Integer.parseInt(result.get(1).get(0)), result.get(1).get(1)
            };
        }
        return new Object[]{
                -1, null
        };
    }

    //查找是否用户重名
    public static boolean checkUsernameDuplicate(String name) {
        List<List<String>> result = query(String.format("SELECT u_username FROM users WHERE u_username = '%s'", name));
        if (!isResultValid(result)) {
            return false;
        }
        printResult(result);
        return false;
    }

    //创建新用户 输入各种信息
    public static boolean createNewUser(String name, String password, String phone, String email) {
        return nonQuery(String.format("INSERT users(u_username, u_password, u_manager,`u_phone`,`u_email`,`u_register_date`) value('%s','%s','0','%s','%s',now())",
                name, password, phone, email));

    }

    public static User check(String username, String password) {
        List<List<String>> result = query(String.format("select * from users where u_username = '%s'", username));
        if (!isResultValid(result)) {
            return null;
        }
        User user = new User(result.get(1));//skip title row
        return user.getPassword().equals(password) ? user : null;
    }

    //修改用户信息
    public static boolean editUserInformation(int user_id, String name, String email, String phone) {
        return nonQuery(String.format("UPDATE users SET u_username='%s',u_email='%s',u_phone='%s' WHERE u_id='%s';", name, email, phone, user_id));
    }

}
