package club.javalearn.crm.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyGrantedAuthority implements GrantedAuthority {
    private String url;
    private String method;
    @Override
    public String getAuthority() {
        return this.url + ";" + this.method;
    }
}
