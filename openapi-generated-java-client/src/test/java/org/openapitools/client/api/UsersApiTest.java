/*
 * PIM-SWKS SoSe2024 REST API
 * PIM-SWKS SoSe2024 REST API
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.Comment;
import org.openapitools.client.model.Post;
import org.openapitools.client.model.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for UsersApi
 */
@Disabled
public class UsersApiTest {

    private final UsersApi api = new UsersApi();

    /**
     * Create a new user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createUserTest() throws ApiException {
        User user = null;
        User response = api.createUser(user);
        // TODO: test validations
    }

    /**
     * Delete a user by ID
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteUserByIdTest() throws ApiException {
        Long id = null;
        User response = api.deleteUserById(id);
        // TODO: test validations
    }

    /**
     * Returns all comments authored by this user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getCommentsForUserTest() throws ApiException {
        Long id = null;
        List<Comment> response = api.getCommentsForUser(id);
        // TODO: test validations
    }

    /**
     * Returns all posts authored by this user
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getPostsForUserTest() throws ApiException {
        Long id = null;
        List<Post> response = api.getPostsForUser(id);
        // TODO: test validations
    }

    /**
     * Returns a user by ID
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getUserByIdTest() throws ApiException {
        Long id = null;
        User response = api.getUserById(id);
        // TODO: test validations
    }

    /**
     * Returns all users
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getUsersTest() throws ApiException {
        List<User> response = api.getUsers();
        // TODO: test validations
    }

    /**
     * Update a user by ID
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateUserByIdTest() throws ApiException {
        Long id = null;
        User user = null;
        User response = api.updateUserById(id, user);
        // TODO: test validations
    }

}