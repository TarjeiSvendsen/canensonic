package no.tari.canensonic.api.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CanensUserRepository extends JpaRepository<CanensUser,String> {
    Optional<CanensUser> findCanensUserByUsername(String username);
}
