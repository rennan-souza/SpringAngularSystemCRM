package renan.springcrm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import renan.springcrm.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}