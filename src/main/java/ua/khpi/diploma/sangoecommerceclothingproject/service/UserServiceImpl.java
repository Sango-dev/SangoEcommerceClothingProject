package ua.khpi.diploma.sangoecommerceclothingproject.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.UserRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.UserDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.UserMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.Role;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper mapper = UserMapper.MAPPER;
    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findFirstByNickName(String name) {
        return userRepository.findFirstByNickName(name);
    }

    @Override
    public User findFirstByEmail(String email) {
        return userRepository.findFirstByEmail(email);
    }

    @Override
    public User findFirstByPhone(String number) {
        return userRepository.findFirstByPhone(number);
    }

    @Override
    public UserDto getUserDtoByNickName(String name) {
        return mapper.fromUser(userRepository.findFirstByNickName(name));
    }

    @Override
    @Transactional
    public boolean save(UserDto userDto) {
        if (!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())) {
            throw new RuntimeException("Password is not equal");
        }
        userDto.setPassword(PASSWORD_ENCODER.encode(userDto.getPassword()));
        userDto.setRole(Role.CLIENT);
        userRepository.save(mapper.toUser(userDto));
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByNickName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with nickname: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getNickName(),
                user.getPassword(),
                roles);
    }

    @Override
    public void updatePassword(UserDto userDto) {
        User user = userRepository.findFirstByNickName(userDto.getNickName());
        user.setPassword(PASSWORD_ENCODER.encode(userDto.getPassword()));
        userRepository.save(user);
    }
}
