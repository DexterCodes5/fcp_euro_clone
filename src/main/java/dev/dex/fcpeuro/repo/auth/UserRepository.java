package dev.dex.fcpeuro.repo.auth;

import dev.dex.fcpeuro.entity.auth.*;
import org.springframework.data.jpa.repository.*;

import java.util.*;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailVerificationToken(String emailVerificationToken);

    @Query("SELECT EXISTS(SELECT u FROM User u WHERE u.email = :email)")
    Boolean selectExistsEmail(String email);

    @Query("SELECT EXISTS(SELECT u FROM User u WHERE u._username = :username)")
    Boolean selectExistsUsername(String username);
}
