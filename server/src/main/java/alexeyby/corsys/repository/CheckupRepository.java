package alexeyby.corsys.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.Checkup;

public interface CheckupRepository  extends JpaRepository<Checkup, UUID>
{
	@Query(value = "SELECT chkup FROM Checkup chkup WHERE chkup.patient.patientUuid = :patientUuid")
	public Optional<Checkup> findByPatientId(@Param("patientUuid") UUID patientId);
}