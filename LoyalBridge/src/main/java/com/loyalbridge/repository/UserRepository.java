package com.loyalbridge.repository;

import com.loyalbridge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE " +
           "(:name IS NULL OR LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:phone IS NULL OR u.phone LIKE CONCAT('%', :phone, '%')) AND " +
           "(:status IS NULL OR u.status = :status)")
    List<User> searchUsers(@Param("name") String name,
                          @Param("phone") String phone,
                          @Param("status") String status);
} 