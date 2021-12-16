package ar.edu.itba.infocracks.bd2.dolaresdeconfianza.security;

import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.model.postgres.UserEntity;
import ar.edu.itba.infocracks.bd2.dolaresdeconfianza.repository.postgres.UserEntityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserEntityRepository userRepository;

    public JwtUserDetailsService(UserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        return new MyUserDetails(user.getEmail(), user.getPassword(), authorities);
    }


}

