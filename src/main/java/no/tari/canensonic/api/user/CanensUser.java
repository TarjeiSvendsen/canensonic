package no.tari.canensonic.api.user;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "canens_users")
public class CanensUser {
    @Id
    private String username;
    private String email;
    private String password;
    private boolean scrobblingEnabled;
    private int maxBitRate;
    private LocalDateTime dateCreated;
    @Embedded
    private UserRoles authorities;

    public CanensUser(){
        this.dateCreated = LocalDateTime.now();
    }

    public CanensUser(String username,String password,String email){
        this.username = username;
        this.email = email;
        this.password = password; // Assumes password is already encoded when passed from the controller.
        this.dateCreated = LocalDateTime.now();
        this.authorities = new UserRoles(false,
                true,
                true,
                false,
                true,
                false,
                true,
                true,
                true,
                true,
                true,
                false);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isScrobblingEnabled() {
        return scrobblingEnabled;
    }

    public void setScrobblingEnabled(boolean scrobblingEnabled) {
        this.scrobblingEnabled = scrobblingEnabled;
    }

    public int getMaxBitRate() {
        return maxBitRate;
    }

    public void setMaxBitRate(int maxBitRate) {
        this.maxBitRate = maxBitRate;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public UserRoles getAuthorities() {
        return authorities;
    }

    public Map<String,Boolean> getAuthoritiesMap() {
        Map<String,Boolean> roles = new HashMap<>(12);
        roles.put("adminRole",authorities.adminRole());
        roles.put("settingsRole",authorities.settingsRole());
        roles.put("downloadRole",authorities.downloadRole());
        roles.put("uploadRole",authorities.uploadRole());
        roles.put("playlistRole",authorities.playlistRole());
        roles.put("coverArtRole",authorities.coverArtRole());
        roles.put("commentRole",authorities.commentRole());
        roles.put("podcastRole",authorities.podcastRole());
        roles.put("streamRole",authorities.streamRole());
        roles.put("jukeboxRole",authorities.jukeboxRole());
        roles.put("shareRole",authorities.shareRole());
        roles.put("videoConversionRole",authorities.videoConversionRole());
        return roles;
    }

    public List<? extends GrantedAuthority> getAuthoritiesList(){
        Map<String,Boolean> roleMap = this.getAuthoritiesMap();
        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
        for(String key: roleMap.keySet()){
            if (roleMap.get(key)){
                authoritiesList.add(new SimpleGrantedAuthority(key));
            }
        }
        return authoritiesList;
    }

    public void setAuthorities(UserRoles authorities) {
        this.authorities = authorities;
    }

    public record UserRoles(boolean adminRole,
                            boolean settingsRole,
                            boolean downloadRole,
                            boolean uploadRole,
                            boolean playlistRole,
                            boolean coverArtRole,
                            boolean commentRole,
                            boolean podcastRole,
                            boolean streamRole,
                            boolean jukeboxRole,
                            boolean shareRole,
                            boolean videoConversionRole){}

}
