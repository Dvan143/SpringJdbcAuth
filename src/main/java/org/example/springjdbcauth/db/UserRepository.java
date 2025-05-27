package org.example.springjdbcauth.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserClass,Long>{
    boolean existsByUsername(String username);
    Optional<UserClass> getUserByUsername(String username);
}
