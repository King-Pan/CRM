package club.javalearn.crm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

/**
 * 角色新
 *
 * @author king-pan
 * @date 2018-06-01
 **/
@Table(name = "sys_role")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users","permissions"})
public class Role {
    /**
     * 角色编码
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    /**
     * 角色名称
     */
    @NotEmpty
    @Column(length = 256)
    private String roleName;

    /**
     * 角色备注
     */
    @Column(length = 2000)
    private String remark;

    /**
     * 角色状态
     */
    @Column(length = 10)
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Set<User> users = new HashSet<>();


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "sys_role_permission",joinColumns ={@JoinColumn(name = "role_id", referencedColumnName = "roleId")},
            inverseJoinColumns =  {@JoinColumn(name = "permission_id", referencedColumnName = "permissionId")})
    @JsonIgnore
    private Set<Permission> permissions = new HashSet<>();

    @Transient
    private List<Long> permissionIdList;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Role){
            Role role =(Role)obj;
            return role.getRoleId().equals(this.getRoleId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return  this.getRoleId()!=null?this.getRoleId().hashCode():0;
    }

}
