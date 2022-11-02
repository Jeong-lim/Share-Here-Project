package com.cos.shareHere.service;

import com.cos.shareHere.domain.user.User;
import com.cos.shareHere.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service // 1.IOC 2. 트랜잭션 관리
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional // 함수 실행부터 종료까지 트랜잭션 관리 (Write(insert, Update, Delete))
    public User 회원가입(User user) {
        // 회원 가입 진행
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword); // password 암호화
        user.setRole("ROLE_USER"); // 관리자는 ROLE_ADMIN
        User userEntity = userRepository.save(user);
        return userEntity;
    }
}