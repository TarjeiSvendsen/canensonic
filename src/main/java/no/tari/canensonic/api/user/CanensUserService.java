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

    /**
     * Useful in cases where the user is known to exist.
     * @param username the user to query the database for
     * @return the {@link CanensUser} object of the specified user.
     */
    public CanensUser findCanensUserByUsername(String username){
        Optional<CanensUser> user =  userRepository.findCanensUserByUsername(username);
        if (user.isPresent())
            return user.get();
        else throw new UsernameNotFoundException("User does not exist");
    }

    /**
     * Simple check to see if user exists or not, some day I want to try and implement a bloom filter instead of this.
     * @param username the username to check existence of.
     * @return true or false given the user exists or not.
     */
    public boolean doesUserExist(String username){
        return  (userRepository.findCanensUserByUsername(username).isPresent());
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
