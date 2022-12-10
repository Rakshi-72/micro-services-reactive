package com.userservice.userservice.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.userservice.userservice.configs.MapperUtil;
import com.userservice.userservice.dtos.UserDto;
import com.userservice.userservice.models.User;
import com.userservice.userservice.repositories.UserRepo;
import com.userservice.userservice.services.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private MapperUtil mapper;

    /**
     * This function returns a Flux of UserDto objects, which is created by mapping
     * the User objects
     * returned by the findAll() function of the UserRepository to UserDto objects
     * using the
     * modelUserToDto() function of the UserMapper class.
     * 
     * @return Flux<UserDto>
     */
    @Override
    public Flux<UserDto> getUsers() {
        return repo.findAll()
                .map(mapper::modelUserToDto);
    }

    /**
     * If the userId is found in the database, map the user to a DTO and return a
     * 200 OK response. If
     * the userId is not found, return a 400 Bad Request response
     * 
     * @param userId The id of the user to be retrieved
     * @return A Mono of a ResponseEntity of a UserDto
     */
    @Override
    public Mono<ResponseEntity<UserDto>> getUserById(Integer userId) {
        return repo.findById(userId)
                .map(mapper::modelUserToDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    /**
     * If the userId is found in the database, delete it and return a 200 OK
     * response. If the userId is
     * not found in the database, return a 409 Conflict response
     * 
     * @param userId The id of the user to be deleted.
     * @return A Mono of a ResponseEntity of Void.
     */
    @Override
    public Mono<ResponseEntity<Void>> deleteById(Integer userId) {
        return repo.deleteById(userId).map(ResponseEntity::ok);
    }

    /**
     * Take a Mono of a UserDto, map it to a UserModel, save it to the database, map
     * it back to a
     * UserDto, wrap it in a ResponseEntity, and if there's an error, return a bad
     * request.
     * 
     * @param user Mono<UserDto>
     * @return A Mono of a ResponseEntity of a UserDto
     */
    @Override
    public Mono<ResponseEntity<UserDto>> saveUser(Mono<UserDto> user) {
        return user.map(mapper::dtoToUserModel)
                .flatMap(repo::save)
                .map(mapper::modelUserToDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    /**
     * If the user exists, update the user with the new information, otherwise
     * return a conflict error.
     * 
     * @param userDto A Mono of UserDto
     * @param userId  Integer
     * @return A Mono of a ResponseEntity of a User
     */
    @Override
    public Mono<ResponseEntity<UserDto>> updateUser(Mono<UserDto> userDto, Integer userId) {
        return repo.findById(userId)
                .flatMap(user -> userDto.flatMap(dto -> {
                    User freshUser = mapper.dtoToUserModel(dto);
                    freshUser.setId(user.getId());
                    return repo.save(freshUser);
                }))
                .map(mapper::modelUserToDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    /**
     * It takes a string as an argument, searches the database for a user with a
     * name that contains the
     * string, and returns a Flux of UserDto objects
     * 
     * @param key the search key
     * @return Flux<UserDto>
     */
    @Override
    public Flux<UserDto> searchUsersByName(String key) {
        return repo.findByNameContainingIgnoreCase(key)
                .map(mapper::modelUserToDto);
    }

}
