package club.javalearn.crm.model;

import club.javalearn.crm.Constants;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * 用户信息
 *
 * @author king-pan
 * @date 2018-06-01
 **/
@Table(name = "sys_user")
@Entity
@Data
public class User implements UserDetails, Serializable {
    /**
     * 用戶编码-自增长策略
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    /**
     * 登录用户名
     */
    @NotEmpty(message = "账号不能为空")
    @Size(min = 3, max = 20)
    @Column(name = "username",nullable = false, length = 20, unique = true)
    private String username;

    /**
     * 用户邮箱
     */
    @NotEmpty(message = "邮箱不能为空")
    @Size(max = 50)
    @Email(message = "邮箱格式不对")
    private String email;

    /**
     * 用户电话号码
     */
    private String phoneNum;

    /**
     * 用户昵称
     */
    @NotEmpty(message = "昵称不能为空")
    @Size(min = 2, max = 20)
    @Column(length = 20, nullable = false)
    private String nickName;

    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空")
    @Size(min = 8, max = 100)
    @Column(length = 100)
    private String password;

    /**
     * 加密盐
     */
    @Column(length = 100)
    private String slat;

    /**
     * 用户头像地址
     */
    @Column(length = 256)
    private String imgUrl;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 用户状态
     */
    @Column(length = 10)
    private String status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * JPA 的规范要求无参构造函数；
     *      设为 protected 防止直接使用
     */
    protected User() {
    }

    @Override
    public String getUsername() {
        return null;
    }


    /**
     * 账号是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return !this.getStatus().equals(Constants.USER_STATUS_EXPIRED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.getStatus().equals(Constants.USER_STATUS_LOCKED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus().equals(Constants.USER_STATUS_ENABLE);
    }
}
