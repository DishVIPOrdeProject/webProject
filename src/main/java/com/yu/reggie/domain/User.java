package com.yu.reggie.domain;

import com.yu.reggie.function.Methods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor//无参构造
public class User {
    private int id;
    private String username;
    private String password;
    private boolean isManager;
    private String phone;
    private String email;
    private Calendar date;


    public User(int id, String username, String password, boolean isManager, String phone, String email, Calendar data) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isManager = isManager;
        this.phone = phone;
        this.email = email;
        this.date = data;
    }

    public User(List<String> sqlLine) {
        this.id = Integer.parseInt(sqlLine.get(0));
        this.username = sqlLine.get(1);
        this.password = sqlLine.get(2);
        this.isManager = sqlLine.get(3).equals("1");
        this.phone = sqlLine.get(4);
        this.email = sqlLine.get(5);
        this.date = Methods.changeString2Calendar(sqlLine.get(6));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', password='%s', isManager=%s}",
                id, username, password, isManager);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return id == user.id &&
                isManager == user.isManager &&
                username.equals(user.username) &&
                password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, isManager);
    }
}
