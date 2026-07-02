package ru.mariiic.impl.model;

import jakarta.persistence.*;
import lombok.Getter;
import ru.mariiic.impl.security.authorities.UserAuthority;
import ru.mariiic.impl.security.authorities.UserRole;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private Set<UserAuthority> authorities;

    protected UserAccount() {}

    public UserAccount(String username, String passwordHash, String email, UserRole role, Set<UserAuthority> authorities) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.role = role;
        this.authorities = authorities;
    }

    public void changePassword(String newPasswordHash){
        this.passwordHash = newPasswordHash;
    }
}
