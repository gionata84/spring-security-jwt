package io.my.springsecurityjwt;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Collection<? extends GrantedAuthority> list = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (userName.equals("foo"))
            return new User("foo", "foo", list);
        if (userName.equals("bar"))
            return new User("bar", "bar", new ArrayList<>());

        return null;

    }
}
