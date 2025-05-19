package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserEmailAndPassword(String userEmail, String password);

    Optional<User> findUserByUserName(String userName);

    default User findUserByUserEmailAndPasswordOrElseThrow(String userEmail, String password) {
        return findUserByUserEmailAndPassword(userEmail, password).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일과 비밀번호가 일치하지 않습니다."));
    }

    default User findUserByUserNameOrElseThrow(String userName) {
        return findUserByUserName(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist username = " + userName));
    }

    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

}