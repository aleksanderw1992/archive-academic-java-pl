/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common.api;

import common.domain.Post;
import common.domain.Topic;
import common.domain.User;

/**
 *
 * @author Aleksander Wojcik
 */
public interface Service {
    int logIn(String login, String passwd) throws Exception;
    int register(String login, String password, String firstName, String surname, String email) throws Exception;
    int addTopic(int userId, String title, String description) throws Exception;
    int addPost(int userId, int topicId, String content) throws Exception;
    Post[] loadPosts(int topicId) throws Exception;
    Topic[] loadTopics() throws Exception;
    User getUser(int userId) throws Exception;
}
