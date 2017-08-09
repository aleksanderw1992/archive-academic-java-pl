/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.domain;

import java.util.Date;

/**
 *
 * @author Aleksander Wojcik
 */
public class Post {

    public static final String INSERT = getInsert();
    public static final String TABLE = "posts";

    private int post_id;
    private int author_id;
    private int topic_id;
    /**
     * I use @Deprecated java.util.Date to simplify my project and avoid adding
     * jars. In the next version it will be replaces by org.joda.time.DateTime
     * from Apache Commons library
     */
    private Date post_time;
    private String text;

    public Post(int post_id, int author_id, int topic_id, Date post_time, String text) {
        this.post_id = post_id;
        this.author_id = author_id;
        this.topic_id = topic_id;
        this.post_time = post_time;
        this.text = text;
    }

    public Post() {
    }

    @Override
    public String toString() {
        return "Post{" + "post_id=" + post_id + ", author_id=" + author_id + ", topic_id=" + topic_id + ", post_time=" + post_time + ", text=" + text + '}';
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public Date getPost_time() {
        return post_time;
    }

    public void setPost_time(Date post_time) {
        this.post_time = post_time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    private static String getInsert() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(" ")
                .append(Post.TABLE)
                .append("(")
                .append("author_id,")
                .append("topic_id,")
                .append("post_time,")
                .append("text")
                .append(") ");
        return stringBuilder.toString();
    }
}
