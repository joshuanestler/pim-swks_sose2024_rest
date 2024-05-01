/*
 * Copyright (c) 2024. 
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.htwsaar.pimswks.rest.resources;

import de.htwsaar.pimswks.rest.model.UserDto;
import de.htwsaar.pimswks.rest.model.entities.UserEntity;
import de.htwsaar.pimswks.rest.repositories.UserRepository;
import de.htwsaar.pimswks.rest.security.Secured;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path(UsersResource.PATH)
public class UsersResource {

    public static final String PATH = "/users";
    private static final String USER_NOT_FOUND_MESSAGE_TEMPLATE = "User with ID \"%d\" not found";

    private final UserRepository userRepository;

    @Inject
    public UsersResource(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> getUsers(@QueryParam("limit") final int limit, @QueryParam("offset") final int offset) {
        return userRepository.readAll(offset, limit).stream()
            .map(UserEntity::convertToDto)
            .toList();
    }

    @POST
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(final UserDto user) {

        final UserEntity createUserEntity = new UserEntity();
        createUserEntity.setUsername(user.getUsername());
        createUserEntity.setEmail(user.getEmail());

        final UserEntity createdUser = userRepository.create(createUserEntity);
        return Response.created(URI.create(PATH + "/" + createdUser.userId)).build();
    }

    @GET
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUser(@PathParam("userId") long userId) {
        return userRepository.findById(userId)
            .map(UserEntity::convertToDto)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE_TEMPLATE));
    }

    @PUT
    @Path("{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public UserDto updateUser(@PathParam("userId") long userId, final UserDto user) {
        return userRepository.findById(userId)
            .map(userEntity -> {
                userEntity.setUsername(user.getUsername());
                userEntity.setEmail(user.getEmail());
                return userRepository.update(userEntity).convertToDto();
            })
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE_TEMPLATE));
    }

    @DELETE
    @Path("{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public UserDto deleteUser(@PathParam("userId") long userId) {
        return userRepository.delete(userId)
            .map(UserEntity::convertToDto)
            .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND_MESSAGE_TEMPLATE));
    }
}
