package com.mdd.back.services;

import com.mdd.back.dto.UserDto;
import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.entities.User;
import com.mdd.back.repositories.UserRepository;
import com.mdd.back.security.mapper.UserMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    public void register(RegisterRequestDto registerRequestDto) {
        registerRequestDto.setPassword(bCryptPasswordEncoder.encode(registerRequestDto.getPassword()));
        User user = userMapper.toUser(registerRequestDto);
        userRepository.save(user);
    }

    public UserDto getUserById(Long userId) throws UsernameNotFoundException {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return userMapper.toUserDto(user);
    }

    @Override
    public User loadUserByUsername(String username) throws BadCredentialsException {
        return this.userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Mauvaise authentification"));
    }



}
