package club.javalearn.crm.service;

import club.javalearn.crm.Constants;
import club.javalearn.crm.model.User;
import club.javalearn.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author king-pan
 * @date 2018-06-01
 **/
@Service
public class UserServiceImpl implements UserService,UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
            //被锁定，无法登录
        } else if(Constants.USER_STATUS_LOCKED.equals(user.getStatus())) {
            throw new LockedException("用户被锁定");
        }
        return user;
    }
}
