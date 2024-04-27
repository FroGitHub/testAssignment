package com.ClearSolutions.testassig;

import com.ClearSolutions.testassig.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserById(Long id);
    void deleteUserById(Long id);

    @Query("from User u where u.birthDate between :date_from and :date_to")
    List<User> findByBirthDateBetween(@Param("date_from") String date_from, @Param("date_to") String date_to);

}
