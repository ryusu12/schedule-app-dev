package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.exception.ConflictException;
import com.example.scheduleappdev.exception.NotFoundException;
import com.example.scheduleappdev.exception.UnauthorizedException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserEmailAndPassword(String userEmail, String password);

    Optional<User> findUserByUserName(String userName);

    Optional<User> findUserByUserEmail(String userEmail);

    default User findUserByUserEmailAndPasswordOrElseThrow(String userEmail, String password) {
        return findUserByUserEmailAndPassword(userEmail, password).orElseThrow(() ->
                new UnauthorizedException("이메일과 비밀번호가 일치하지 않습니다."));
    }

    default User findUserByUserNameOrElseThrow(String userName) {
        return findUserByUserName(userName).orElseThrow(() ->
                new NotFoundException("유저 " + userName + " 이(가) 존재하지 않습니다."));
    }

    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("유저가 존재하지 않습니다. id = " + id));
    }

    default void isExistUserNameOrEmail(String userName, String userEmail) {
        if (findUserByUserName(userName).isPresent()) {
            throw new ConflictException("이미 가입된 유저입니다.");
        }
        if (findUserByUserEmail(userEmail).isPresent()) {
            throw new ConflictException("이미 가입된 이메일입니다.");
        }
    }

}