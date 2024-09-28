package ru.ima.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ima.model.jpa.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail (String username);
    User getByEmail (String username);
    Optional<User> findByConfirmationCode(UUID confirmationCode);
}