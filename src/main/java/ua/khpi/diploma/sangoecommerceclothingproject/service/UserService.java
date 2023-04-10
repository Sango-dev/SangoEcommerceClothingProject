package ua.khpi.diploma.sangoecommerceclothingproject.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.UserDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

public interface UserService extends UserDetailsService {
	User findFirstByNickName(String name);

	boolean save(UserDto userDTO);

	void save(User user);
}
