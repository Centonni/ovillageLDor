package ci.ovillage.ldor.repository;

import ci.ovillage.ldor.domain.LivreDor;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LivreDor entity.
 */
public interface LivreDorRepository extends JpaRepository<LivreDor,Long> {

    @Query("select livreDor from LivreDor livreDor where livreDor.dignitaire.login = ?#{principal.username}")
    List<LivreDor> findByDignitaireIsCurrentUser();

}
