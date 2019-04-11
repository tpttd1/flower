package application.data.repository;

import application.data.model.User;
import application.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u.username, u.password FROM tbl_user u WHERE u.username = :username AND u.password = :password")
    User login(@PathVariable("username") String username, @PathVariable("password") String password);
}
