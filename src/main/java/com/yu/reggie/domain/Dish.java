package com.yu.reggie.domain;

import com.yu.reggie.function.DishTypes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

public class Dish {
    private int id;
    private String name;
    private String detail;
    private double price;
    private Blob image;
    private int type;

    private String base64Image;

    public Dish(int id, String name, String detail, double price, int type, Blob image) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.image = image;
        this.type = type;
    }

    public Dish(List<String> sqlLine, Blob image) {
        this.id = Integer.parseInt(sqlLine.get(0));
        this.name = sqlLine.get(1);
        this.detail = sqlLine.get(2);
        this.price = Double.parseDouble(sqlLine.get(3));
        this.image = image;
        this.type = Integer.parseInt(sqlLine.get(5));
        try {
            decodeBlob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeBlob() throws IOException, SQLException {
        if (image == null) {
            return;
        }
        InputStream inputStream = image.getBinaryStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] imageBytes = outputStream.toByteArray();

        base64Image = Base64.getEncoder().encodeToString(imageBytes);

        inputStream.close();
        outputStream.close();
    }

    public String getStringType() {
        if (type == DishTypes.DISH_TYPE_MAIN) {
            return "主菜";
        } else if (type == DishTypes.DISH_TYPE_DESSERT) {
            return "甜点";
        } else if (type == DishTypes.DISH_TYPE_DRINK) {
            return "饮品";
        } else if (type == DishTypes.DISH_TYPE_CHIPS) {
            return "小吃";
        }
        return "";
    }

    public String getBase64Image() {
        return base64Image;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getPrice() {
        return price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Dish{id=%d, name='%s', detail='%s'}",
                id, name, detail);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Dish dish = (Dish) obj;
        return id == dish.id &&
                name.equals(dish.name) &&
                detail.equals(dish.detail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, detail);
    }
}
