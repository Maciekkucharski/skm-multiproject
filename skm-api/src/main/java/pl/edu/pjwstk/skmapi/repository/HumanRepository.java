package pl.edu.pjwstk.skmapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pjwstk.skmapi.model.Human;

@Repository
public interface HumanRepository extends JpaRepository<Human, Long> {
}
