package pl.plenczewski.sza3home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.plenczewski.sza3home.models.AppUserRole;

@Repository
public interface AppUserRoleRepo extends JpaRepository<AppUserRole, Long> {
}
