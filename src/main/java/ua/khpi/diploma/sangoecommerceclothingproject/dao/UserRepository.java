package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByNickName(String name);
}
