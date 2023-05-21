package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.UserDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByNickName(String name);
    User findFirstByEmail(String email);
    User findFirstByPhone(String number);
    @Query(value = "select * from users u where u.role like '%CLIENT%'", nativeQuery = true)
    List<User> findAllByRoleClient();
    UserDto findUserById(String id);
}
