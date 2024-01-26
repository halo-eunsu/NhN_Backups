package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록

        if (userRepository.countByUserId(user.getUserId()) == 0) {
            userRepository.save(user);
        } else {
            throw new UserAlreadyExistsException(user.getUserId());
        }
    }

    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정
        if (userRepository.countByUserId(user.getUserId()) == 1) {
            userRepository.update(user);
        } else {
            throw new UserNotFoundException(user.getUserId());
        }
    }

    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        if (userRepository.countByUserId(userId) == 0) {
            throw new RuntimeException();
        }
        userRepository.deleteByUserId(userId);
    }


    @Override
    public User doLogin(String userId, String userPassword) {

        //todo# User.java에 setLatestLoginAt 을 구현함, 추후 수정해야 할 경우 확인 확인

        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        return userRepository.findByUserIdAndUserPassword(userId, userPassword)
                .map(user -> {
                    user.setLatestLoginAt(LocalDateTime.now()); // 로그인 시간 업데이트
                    userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now()); // DB에 업데이트 반영
                    return user;
                })
                .orElseThrow(() -> {

                    log.debug("아이디나 비밀번호를 잘못 입력했거나 없는 계정");
                    return new UserNotFoundException("로그인 오류: 아이디나 비밀번호를 잘못 입력했거나 없는 계정");
                });
    }


}
