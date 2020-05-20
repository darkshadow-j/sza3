package pl.plenczewski.sza3home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import pl.plenczewski.sza3home.models.AppUser;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    AppUser getAppUserByUsername(String username);
}
