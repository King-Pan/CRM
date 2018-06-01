package club.javalearn.crm.repository;

import club.javalearn.crm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * crm
 *
 * @author king-pan
 * @date 2018-06-01
 **/
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据用户名查找用户
     * @param userName 用户名
     * @return 返回用户信息
     */
    User findByUsername(String userName);
}
