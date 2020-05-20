package pl.plenczewski.sza3home.repository;

import com.sun.org.apache.xpath.internal.objects.XBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.plenczewski.sza3home.models.VerificationToken;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByValue(String value);
}
