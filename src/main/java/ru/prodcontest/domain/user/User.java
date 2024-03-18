package ru.prodcontest.domain.user;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ru.prodcontest.domain.friend.Friend;
import ru.prodcontest.domain.likes_and_dislikes.Dislike;
import ru.prodcontest.domain.likes_and_dislikes.Like;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "usr")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User implements UserDetails {
    @Id
    @SequenceGenerator(sequenceName = "usr_sequence", name = "usr_sequence", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private Boolean isPublic;

    private String phone;

    private String image;

    @ManyToMany
    @JoinTable(
            name = "usr_friends",
            joinColumns = @JoinColumn(name = "usr_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Friend> friends;

    @OneToMany(mappedBy = "user")
    private List<Like> likes;

    @OneToMany(mappedBy = "user")
    private List<Dislike> dislikes;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String login, String email, String password, String countryCode, Boolean isPublic, String phone, String image) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.countryCode = countryCode;
        this.isPublic = isPublic;
        this.phone = phone;
        this.image = image;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return login;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(login, user.login)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(password, user.password)) return false;
        if (!Objects.equals(countryCode, user.countryCode)) return false;
        if (!Objects.equals(isPublic, user.isPublic)) return false;
        if (!Objects.equals(phone, user.phone)) return false;
        if (!Objects.equals(image, user.image)) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (isPublic != null ? isPublic.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}