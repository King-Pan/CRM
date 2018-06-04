package club.javalearn.crm.security.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * crm
 *
 * @author king-pan
 * @date 2018-06-04
 **/

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "customer.security")
public class SecurityProperties {
    /**
     * 浏览器环境配置
     */
    private BrowserProperties browser = new BrowserProperties();


}
