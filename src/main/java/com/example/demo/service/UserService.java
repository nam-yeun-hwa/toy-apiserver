package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null || userEntity.getUsername() == null){
            throw new RuntimeException("Invalid arguments");
        }

        final String username = userEntity.getUsername();

        if(userRepository.existsByUsername(username)){
            log.warn("Username already exists {}" , username);
            throw new RuntimeException("Username already exists");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String username, final String password){
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder){
        final UserEntity originalUser = userRepository.findByUsername(username);

        //matchs 메서드를 이용해 패스워드가 같은지 확인
        if(originalUser != null && encoder.matches(password, originalUser.getPassword())){
            return originalUser;
        }
        return  null;
    }
    //보통 암호화된 패스워드를 비교해야 하는 경우, 사용자에게 받은 패스워드를 같은 방법으로 암호화한 후 그 결과를 데이터베이스의 값과 비교하는 것이
    //자연스러운 흐름이다. 하지만 matches() 메서드를 사용하였다. 이유는 BCryptPasswordEncoder는 같은 값을 인코딩 하더라도 할때마다
    //값이 다르기 때문이다. 패스워드에 랜덤하게 의미없는 값을 붙여 결과를 생성하기 때문이다. 이런 의미없는 값을 보안 용어로 Salt라 하고 Salt를 붙여
    //인코딩 하는 것을 Salting라고 한다.
    //대신 BCryptPasswordEncoder는 어떤 두 값이 일치여부를 알려주는 메서드인 match()메서드를 제공한다.
    //제공되는 match()메서드는 Salt를 고려해 두 값을 비교해 준다.
}
