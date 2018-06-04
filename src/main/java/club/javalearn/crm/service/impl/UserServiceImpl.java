package club.javalearn.crm.service.impl;

import club.javalearn.crm.model.Role;
import club.javalearn.crm.service.UserService;
import club.javalearn.crm.utils.Constants;
import club.javalearn.crm.model.User;
import club.javalearn.crm.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author king-pan
 * @date 2018-06-01
 **/
@Service
@Slf4j
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
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        log.info("用户:" + username + "有以下角色");
        for(Role role:user.getRoles())
        {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            log.info("-->"+role.getRoleName()+"<--");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);
    }
}
