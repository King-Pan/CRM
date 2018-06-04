package club.javalearn.crm.security.properties;


import club.javalearn.crm.security.SecurityConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * crm
 *
 * @author king-pan
 * @date 2018-06-04
 **/
@Getter
@Setter
@ToString
public class BrowserProperties {

    /**
     * 登录页面，当引发登录行为的url以html结尾时，会跳到这里配置的url上
     */
    private String signInPage = SecurityConstants.DEFAULT_SIGN_IN_PAGE_URL;

    /**
     * 默认登录页面
     *
     */
    String DEFAULT_SIGN_IN_PAGE_URL = "/imooc-signIn.html";

    /**
     * 退出成功时跳转的url，如果配置了，则跳到指定的url，如果没配置，则返回json数据。
     */
    private String signOutUrl;

    /**
     * '记住我'功能的有效时间，默认1小时
     */
    private int rememberMeSeconds = 3600;


}
