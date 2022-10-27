package com.yu.reggie.domain;

import com.yu.reggie.function.Methods;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Comment {
    private int id;
    private int ref_user_id;
    private String user_name;
    private int ref_dish_id;
    private Calendar date;
    private String content;
    private double score;
    private int upvotes;
    private int downvotes;

    public Comment(int id, int ref_user_id, String user_name, int ref_dish_id, Calendar date, String content, double score, int upvotes, int downvotes) {
        this.id = id;
        this.ref_user_id = ref_user_id;
        this.user_name = user_name;
        this.ref_dish_id = ref_dish_id;
        this.date = date;
        this.content = content;
        this.score = score;
        this.upvotes = upvotes;
        this.downvotes = downvotes;
    }

    public Comment(List<String> sqlLine) {
        try {
            Methods.checkSqlLineSize(sqlLine, 9);
            this.id = Integer.parseInt(sqlLine.get(0));
            this.ref_user_id = Integer.parseInt(sqlLine.get(1));
            this.user_name = sqlLine.get(2);
            this.ref_dish_id = Integer.parseInt(sqlLine.get(3));
            this.date = Methods.changeString2Calendar(sqlLine.get(4));
            this.content = sqlLine.get(5);
            this.score = Double.parseDouble(sqlLine.get(6));
            this.upvotes = Integer.parseInt(sqlLine.get(7));
            this.downvotes = Integer.parseInt(sqlLine.get(8));
        } catch (Exception e) {
            e.printStackTrace();
            this.id = -1;
            this.ref_user_id = -1;
            this.user_name = "error";
            this.ref_dish_id = -1;
            this.date = null;
            this.content = "error";
            this.score = -1.11d;
            this.upvotes = -9999;
            this.downvotes = -9999;
        }
    }

    public int getVotes() {
        return upvotes - downvotes;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("Comment{id=%d, ref_user_id=%d, user_name='%s', ref_dish_id=%d, date=%s, content='%s', score=%s, upvotes=%d, downvotes=%d}", id, ref_user_id, user_name, ref_dish_id, date, content, score, upvotes, downvotes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Comment comment = (Comment) obj;
        return id == comment.id && ref_user_id == comment.ref_user_id && ref_dish_id == comment.ref_dish_id && Double.compare(comment.score, score) == 0 && upvotes == comment.upvotes && downvotes == comment.downvotes && user_name.equals(comment.user_name) && date.equals(comment.date) && content.equals(comment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ref_user_id, user_name, ref_dish_id, date, content, score, upvotes, downvotes);
    }
}
