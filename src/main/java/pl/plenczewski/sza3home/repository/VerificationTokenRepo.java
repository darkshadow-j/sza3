package pl.plenczewski.sza3home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.plenczewski.sza3home.models.VerificationToken;

@RequestMapping
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByValue(String value);

}
