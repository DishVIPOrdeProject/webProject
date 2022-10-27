package com.yu.reggie.function.database;


import com.yu.reggie.domain.Dish;
import javafx.util.Pair;

import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DishesDatabase extends MyDatabase {
    protected DishesDatabase() throws Exception {
        super();
    }

    public static List<Dish> getAllDishes() {
        Pair<List<List<String>>, List<Blob>> result = query("select * from dishes;", "d_image");
        if (!isResultValid(result.getKey())) {
//            return new Dish[0];
        return null;
        }
        List<Dish> users = new ArrayList<Dish>();
        List<List<String>> subList = result.getKey().subList(1, result.getKey().size());
        for (int i = 0; i < subList.size(); i++) {
            users.add(new Dish(subList.get(i), result.getValue().get(i)));
        }
//        return users.toArray(Dish[]::new);
        return users;
    }

    public static List<Dish> getDishes(int type) {
        Pair<List<List<String>>, List<Blob>> result = query("select * from dishes where d_type = " + type, "d_image");
        if (!isResultValid(result.getKey())) {
//            return new Dish[0];
            return null;
        }
        List<Dish> users = new ArrayList<Dish>();
        List<List<String>> subList = result.getKey().subList(1, result.getKey().size());
        for (int i = 0; i < subList.size(); i++) {
            users.add(new Dish(subList.get(i), result.getValue().get(i)));
        }
//        return users.toArray(Dish[]::new);
    return users;
    }

    public static double getDishScore(int dish_id) {
        List<List<String>> result = query(String.format("SELECT AVG(c_score) FROM comments WHERE c_dish_id = %d ;", dish_id));
        if (!isResultValid(result)) {
            return -1;
        }
        try {
            return Double.parseDouble(result.get(1).get(0));
        } catch (NullPointerException e) {//菜品无评论
            return 5;
        }
    }

    //删除菜品
    public static boolean deleteDish(int dish_id) {
        return nonQuery("DELETE FROM dishes WHERE d_id=" + dish_id);
    }

    //修改菜品（名字，价格，描述）
    public static boolean updateDish(int id, String name, String price, String detail, String type, InputStream image) {
        if (image == null) {
            return nonQuery(String.format("UPDATE dishes  SET `d_name`= '%s',`d_price`='%s',`d_detail`='%s',`d_type`='%s' WHERE `d_id`='%s';", name, price, detail, type, id));
        } else {
            return nonQuery(String.format("UPDATE dishes  SET `d_name`= '%s',`d_price`='%s',`d_detail`='%s',`d_type`='%s', `d_image` = (?) WHERE `d_id`='%s';", name, price, detail, type, id), image);
        }

    }

    //添加菜品
    public static boolean addDish(String name, String price, String detail, String type, InputStream image) {
        return nonQuery(String.format("INSERT dishes(d_name, d_detail, d_price, d_type, d_image) VALUE ('%s','%s','%s','%s', ?);", name, detail, price, type), image);
    }

    //通过Id查找菜品相关信息
    public static Dish selectDishByID(int id) {
        Pair<List<List<String>>, List<Blob>> pair = query("select * from dishes where d_id =" + id, "d_image");
        if (pair == null) {
            return null;
        }
        if (!isResultValid(pair.getKey())) {
            return null;
        }
        List<List<String>> subList = pair.getKey().subList(1, pair.getKey().size());
        for (int i = 0; i < subList.size(); i++) {
            Dish dish = new Dish(subList.get(i), pair.getValue().get(i));
            if (dish.getId() == id) {
                return dish;
            }
        }
        return null;
    }

    public static int getNextID() {
        List<List<String>> result = query("SELECT d_id + 1 FROM dishes  ORDER BY d_id DESC LIMIT 1;");
        if (!isResultValid(result)) {
            return -1;
        }
        return Integer.parseInt(result.get(1).get(0));
    }
}
