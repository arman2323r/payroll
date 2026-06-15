package org.payroll.service;

import org.payroll.model.UserEntity;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserEntity> findAll();
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmployeeId(Long employeeId);
    UserEntity save(UserEntity user);
    void delete(Long id);
    boolean existsByUsername(String username);
    void updatePassword(Long id, String newPassword);
}