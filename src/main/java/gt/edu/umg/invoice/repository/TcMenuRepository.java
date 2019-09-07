package gt.edu.umg.invoice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gt.edu.umg.invoice.model.TcMenu;

@Repository
public interface TcMenuRepository extends JpaRepository<TcMenu, Long> {
    Optional<TcMenu> findByMenuDesc(String MenuDesc);
    
    @Query("SELECT tm FROM TcRoleMenu rm INNER JOIN rm.tcMenu tm WHERE rm.statusId = 1 AND tm.statusId = 1 AND tm.tcFather = :tcFather")
    List<TcMenu> findByTcFather(@Param("tcFather") TcMenu tcFather);
}