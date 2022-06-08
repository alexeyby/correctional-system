package alexeyby.corsys.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import alexeyby.corsys.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, UUID> {}
