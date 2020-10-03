package kronsoft.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kronsoft.internship.entities.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM patient WHERE phoneNumber = :phoneNumber AND id != :id")
	long countByPhoneNumberNotId(@Param("phoneNumber") String phoneNumber, @Param("id") Long id);

	boolean existsByEmail(String email);

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM patient WHERE email = :email AND id != :id")
	long countByEmailNotId(@Param("email") String email, @Param("id") Long id);

	Patient getById(Long id);

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByPin(String pin);

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM patient WHERE pin = :pin AND id != :id")
	long countByPinNotId(@Param("pin") String pin, @Param("id") Long id);

	@Query(nativeQuery = true, value = "SELECT id FROM patient WHERE user_Id = :userId")
	Long getIdByUserId(@Param("userId") Long userId);

}
