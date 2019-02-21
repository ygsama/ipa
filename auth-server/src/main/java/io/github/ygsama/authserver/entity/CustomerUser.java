package io.github.ygsama.authserver.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class CustomerUser extends User implements UserDetails {

    private static final long serialVersionUID = 1L;

    private List<Role> roless;
    private User user;

    public CustomerUser(User user, List<Role> roles) {
        super(user);
        this.user = user;
        this.roless = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(roless==null || roless.size()<1) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList("");
        }
        StringBuilder commaBuilder = new StringBuilder();
        for(Role role : roless){
            commaBuilder.append("ROLE_"+role.getRname()).append(",");
        }
        String authorities = commaBuilder.substring(0,commaBuilder.length()-1);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }

    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getUname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
