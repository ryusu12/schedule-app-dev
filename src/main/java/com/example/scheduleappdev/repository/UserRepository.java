package com.example.scheduleappdev.repository;

import com.example.scheduleappdev.entity.User;
import com.example.scheduleappdev.exception.ConflictException;
import com.example.scheduleappdev.exception.NotFoundException;
import com.example.scheduleappdev.exception.UnauthorizedException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String userEmail);

    Optional<User> findUserByName(String userName);

    default User findUserByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundException("유저가 존재하지 않습니다. id = " + id));
    }

    default User findUserByEmailOrElseThrow(String email) {
        return findUserByEmail(email).orElseThrow(() ->
                new UnauthorizedException("이메일 " + email + "을 사용하는 유저가 존재하지 않습니다."));
    }

    default void isExistUserNameOrEmail(String name, String email) {
        if (findUserByName(name).isPresent()) {
            throw new ConflictException("이미 가입된 유저입니다.");
        }
        if (findUserByEmail(email).isPresent()) {
            throw new ConflictException("이미 가입된 이메일입니다.");
        }
    }

}