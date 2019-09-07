package gt.edu.umg.invoice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gt.edu.umg.invoice.model.TcUser;

@Repository
public interface TcUserRepository extends JpaRepository<TcUser, Long> {
    Optional<TcUser> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}