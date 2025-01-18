package com.mdd.back.services;

import com.mdd.back.dto.requests.ModifyUserRequestDto;
import com.mdd.back.dto.requests.RegisterRequestDto;
import com.mdd.back.dto.responses.UserResponseDto;
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


    public UserService(
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * Enregistre un nouvel utilisateur dans la base de données.
     *
     * @param registerRequestDto les informations de l'utilisateur à enregistrer
     * @throws RuntimeException si une erreur survient lors de l'enregistrement
     */
    public void register(RegisterRequestDto registerRequestDto) {
        registerRequestDto.setPassword(bCryptPasswordEncoder.encode(registerRequestDto.getPassword()));
        User user = userMapper.toUser(registerRequestDto);
        userRepository.save(user);
    }

    /**
     * Récupère un utilisateur par son email.
     *
     * @param email l'email de l'utilisateur à récupérer
     * @return un DTO {@link UserResponseDto} représentant l'utilisateur
     * @throws UsernameNotFoundException si l'utilisateur avec cet email n'existe pas
     */
    public UserResponseDto getUserByEmail(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        return userMapper.toUserDto(user);
    }

    /**
     * Récupère un utilisateur par son email ou son nom d'utilisateur.
     *
     * @param identifier l'email ou le nom d'utilisateur de l'utilisateur à récupérer
     * @return un DTO {@link UserResponseDto} représentant l'utilisateur
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé avec cet identifiant
     */
    public UserResponseDto getUserByEmailOrUsername(String identifier) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(identifier, identifier)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec email ou username: " + identifier));
        return userMapper.toUserDto(user);
    }

    /**
     * Modifie les informations d'un utilisateur existant.
     *
     * @param email l'email de l'utilisateur à modifier
     * @param userDto les nouvelles informations de l'utilisateur
     * @throws UsernameNotFoundException si l'utilisateur n'existe pas
     * @throws IllegalArgumentException si l'email proposé est déjà utilisé par un autre utilisateur
     */
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
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            String hashedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
            userDto.setPassword(hashedPassword);
        } else {
            userDto.setPassword(null);
        }

        userMapper.updateUserFromDto(userDto, user);

        userRepository.save(user);
    }

    /**
     * Charge un utilisateur basé sur son nom d'utilisateur (utilisé pour l'authentification).
     *
     * @param username le nom d'utilisateur (ici l'email) de l'utilisateur à charger
     * @return l'utilisateur correspondant
     * @throws BadCredentialsException si l'utilisateur n'est pas trouvé
     */
    @Override
    public User loadUserByUsername(String username) throws BadCredentialsException {
        return this.userRepository.findByEmail(username)
                .orElseThrow(() -> new BadCredentialsException("Mauvaise authentification"));
    }

}