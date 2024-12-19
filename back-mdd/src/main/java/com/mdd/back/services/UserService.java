package com.mdd.back.services;

import com.mdd.back.dto.UserDto;
import com.mdd.back.dto.requests.ModifyUserRequestDto;
import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.entities.User;
import com.mdd.back.mapper.UserMapper;
import com.mdd.back.repositories.UserRepository;
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

    public UserDto getUserByEmail(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return userMapper.toUserDto(user);
    }

    public void modifyUser(String email, ModifyUserRequestDto userDto) {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            boolean emailExists = userRepository.findByEmail(userDto.getEmail())
                    .filter(existingUser -> !existingUser.getId().equals(user.getId()))
                    .isPresent();

            if (emailExists) {
                throw new IllegalArgumentException("Cet email est déjà utilisé par un autre utilisateur.");
            }
        }

        userMapper.updateUserFromDto(userDto, user);

        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws BadCredentialsException {
        return this.userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Mauvaise authentification"));
    }



}
