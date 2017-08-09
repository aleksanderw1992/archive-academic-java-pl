/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.domain;

import static common.domain.Post.TABLE;

/**
 *
 * @author Aleksander Wojcik
 */
public class Topic {

    public static final String INSERT = getInsert();
    public static final String TABLE = "topic";

    private int topic_id;
    private int author_id;
    private String title;
    private int last_post;
    private String topic_content;

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLast_post() {
        return last_post;
    }

    public void setLast_post(int last_post) {
        this.last_post = last_post;
    }

    public String getTopic_content() {
        return topic_content;
    }

    public void setTopic_content(String topic_content) {
        this.topic_content = topic_content;
    }

    @Override
    public String toString() {
        return "Topic{" + "topic_id=" + topic_id + ", author_id=" + author_id + ", title=" + title + ", last_post=" + last_post + ", topic_content=" + topic_content + '}';
    }

    public Topic(int topic_id, int author_id, String title, int last_post, String topic_content) {
        this.topic_id = topic_id;
        this.author_id = author_id;
        this.title = title;
        this.last_post = last_post;
        this.topic_content = topic_content;
    }

    public Topic() {
    }

    private static String getInsert() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(" ")
                .append(Topic.TABLE)
                .append("(")
                .append("author_id,")
                .append("title,")
                .append("last_post,")
                .append("topic_content")
                .append(") ");
        return stringBuilder.toString();
    }

}
