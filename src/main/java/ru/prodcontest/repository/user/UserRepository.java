package ru.prodcontest.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.prodcontest.domain.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findByLoginOrEmailOrPhone(String login, String email, String phone);

    Optional<User> findByLoginOrEmail(String login, String email);
}
