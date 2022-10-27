package com.yu.reggie.domain;

import com.yu.reggie.function.Methods;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Order {
    private int id;
    private int ref_user_id;
    private String username;
    private int ref_table_id;
    private int ref_dish_id;
    private String dish_name;
    private Calendar date;
    private int count;
    private boolean isFinished;

    public Order(int id, int ref_user_id, String username, int ref_table_id, int ref_dish_id, String dish_name, Calendar date, int count, boolean isFinished) {
        this.id = id;
        this.ref_user_id = ref_user_id;
        this.username = username;
        this.ref_table_id = ref_table_id;
        this.ref_dish_id = ref_dish_id;
        this.dish_name = dish_name;
        this.date = date;
        this.count = count;
        this.isFinished = isFinished;
    }

    public Order(List<String> sqlLine) {
        this.id = Integer.parseInt(sqlLine.get(0));
        this.ref_user_id = Integer.parseInt(sqlLine.get(1));
        this.username = sqlLine.get(2);
        this.ref_table_id = Integer.parseInt(sqlLine.get(3));
        this.ref_dish_id = Integer.parseInt(sqlLine.get(4));
        this.dish_name = sqlLine.get(5);
        this.date = Methods.changeString2Calendar(sqlLine.get(6));
        this.count = Integer.parseInt(sqlLine.get(7));
        this.isFinished = sqlLine.get(8).equals("1");
    }

    public int getRef_table_id() {
        return ref_table_id;
    }

    public void setRef_table_id(int ref_table_id) {
        this.ref_table_id = ref_table_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRef_user_id() {
        return ref_user_id;
    }

    public void setRef_user_id(int ref_user_id) {
        this.ref_user_id = ref_user_id;
    }

    public int getRef_dish_id() {
        return ref_dish_id;
    }

    public void setRef_dish_id(int ref_dish_id) {
        this.ref_dish_id = ref_dish_id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    @Override
    public String toString() {
        return String.format("Order{id=%d, ref_user_id=%d, username='%s', ref_table_id=%d, ref_dish_id=%d, dish_name='%s', date=%s, count=%d, isFinished=%s}", id, ref_user_id, username, ref_table_id, ref_dish_id, dish_name, date, count, isFinished);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Order order = (Order) obj;
        return id == order.id && ref_user_id == order.ref_user_id && ref_table_id == order.ref_table_id && ref_dish_id == order.ref_dish_id && count == order.count && isFinished == order.isFinished && username.equals(order.username) && dish_name.equals(order.dish_name) && date.equals(order.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ref_user_id, username, ref_table_id, ref_dish_id, dish_name, date, count, isFinished);
    }
}
