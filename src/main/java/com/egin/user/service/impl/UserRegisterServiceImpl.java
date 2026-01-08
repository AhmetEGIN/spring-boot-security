package com.egin.user.service.impl;

import com.egin.user.exception.UserAlreadyExistsException;
import com.egin.user.model.User;
import com.egin.user.model.dto.request.UserRegisterRequest;
import com.egin.user.model.entity.UserEntity;
import com.egin.user.model.mapper.UserEntityToUserMapper;
import com.egin.user.model.mapper.UserRegisterRequestToUserEntityMapper;
import com.egin.user.repository.UserRepository;
import com.egin.user.service.UserRegisterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegisterServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserRegisterRequest request) {

        if (userRepository.existsUserEntityByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        final UserEntity userEntityToSave = UserRegisterRequestToUserEntityMapper.toUserEntity(request);

        userEntityToSave.setPassword(passwordEncoder.encode(request.getPassword()));
        UserEntity savedUserEntity = userRepository.save(userEntityToSave);
        return UserEntityToUserMapper.toUser(savedUserEntity);

    }
}
