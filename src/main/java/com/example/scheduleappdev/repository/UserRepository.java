package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.exception.ConflictException;
import com.example.scheduleappdev.exception.NotFoundException;
import com.example.scheduleappdev.exception.UnauthorizedException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserEmail(String userEmail);

    Optional<User> findUserByUserName(String userName);

    default User findUserByUserEmailOrElseThrow(String userEmail) {
        return findUserByUserEmail(userEmail).orElseThrow(() ->
                new UnauthorizedException("이메일 " + userEmail + "을 사용하는 유저가 존재하지 않습니다."));
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