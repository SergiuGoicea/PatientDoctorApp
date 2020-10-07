package kronsoft.internship.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kronsoft.internship.entities.User;
import kronsoft.internship.entities.UserAuthority;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String string);

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM user WHERE username = :username AND id != :id")
	boolean countByUsernameNotId(@Param("username") String username, @Param("id") Long id);

	boolean existsByEmail(String email);
	
	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM user WHERE email = :email AND id != :id")
	int countByEmailNotId(@Param("email") String email,@Param("id") Long id);

	User getById(Long id);

	@Query(nativeQuery = true, value = "SELECT id FROM users WHERE username = :username")
	Long getIdByUsername(@Param("username") String username);

	@Query(nativeQuery = true, value = "SELECT user_type FROM users WHERE username = :username")
	UserAuthority checkRegularByUsername(@Param("username") String username);

}
