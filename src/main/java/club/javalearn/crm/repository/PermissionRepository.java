package club.javalearn.crm.repository;

import club.javalearn.crm.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
}
