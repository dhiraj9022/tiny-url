package com.urlshortner.repository;

import com.urlshortner.dto.UserDto;
import com.urlshortner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String authEmail);

    Optional<User> findByEmailOrUsername(String email, String username);

    @Query("select new com.urlshortner.dto.UserDto(u.email, u.username) from User u where u.email = :authEmail")
    Optional<UserDto> findDtoByEmail(String authEmail);
}
