package no.tari.canensonic.api.user;

import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CanensUserService implements UserDetailsService {
    private final CanensUserRepository userRepository;

    public CanensUserService(CanensUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Optional<CanensUser> user = userRepository.findById(username);
        if (user.isPresent()){
            return new CanensUserDetails(user.get().getAuthoritiesList(),user.get().getUsername(),user.get().getPassword());
        }else {
            throw new UsernameNotFoundException("User does not exist");
        }
    }

    public CanensUser save(CanensUser user){
        return userRepository.save(user);
    }

    public record CanensUserDetails(List<? extends GrantedAuthority> authorities,String username,String password) implements UserDetails{
        @Override
        public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public @NonNull String getUsername() {
            return username;
        }
    }
}
