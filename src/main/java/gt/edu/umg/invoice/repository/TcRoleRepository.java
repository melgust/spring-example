package gt.edu.umg.invoice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gt.edu.umg.invoice.model.TcRole;

@Repository
public interface TcRoleRepository extends JpaRepository<TcRole, Long> {
    Optional<TcRole> findByRoleDesc(String roleDesc);
    
}