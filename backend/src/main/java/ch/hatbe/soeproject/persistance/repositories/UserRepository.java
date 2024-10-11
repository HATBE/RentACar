package ch.hatbe.soeproject.persistance.repositories;

import ch.hatbe.soeproject.persistance.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll();
}
