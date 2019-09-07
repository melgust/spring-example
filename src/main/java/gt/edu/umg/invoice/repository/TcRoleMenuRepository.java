package gt.edu.umg.invoice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gt.edu.umg.invoice.model.TcMenu;
import gt.edu.umg.invoice.model.TcRole;
import gt.edu.umg.invoice.model.TcRoleMenu;

@Repository
public interface TcRoleMenuRepository extends JpaRepository<TcRoleMenu, Long> {
	
	@Query("SELECT rm FROM TcRoleMenu rm WHERE rm.tcRole = :tcRole")
	List<TcRoleMenu> findAllByRole(@Param("tcRole") TcRole tcRole);
	
	@Query("SELECT rm FROM TcRoleMenu rm WHERE rm.tcRole = :tcRole AND rm.tcMenu = :tcMenu")
	Optional<TcRoleMenu> findByRoleAndMenu(@Param("tcRole") TcRole tcRole, @Param("tcMenu") TcMenu tcMenu);
	
	@Query("SELECT rm FROM TcRoleMenu rm INNER JOIN rm.tcMenu tm WHERE rm.tcRole = :tcRole AND rm.statusId = 1 AND tm.tcFather IS NULL AND tm.statusId = 1")
	List<TcRoleMenu> findAllByRootMenu(@Param("tcRole") TcRole tcRole);
	
}