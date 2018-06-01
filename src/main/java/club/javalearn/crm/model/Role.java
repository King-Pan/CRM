package club.javalearn.crm.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 角色新
 *
 * @author king-pan
 * @date 2018-06-01
 **/
@Table(name = "sys_role")
@Entity
@Data
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
}
