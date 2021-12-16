package renan.springcrm.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import renan.springcrm.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
