package gt.edu.umg.invoice.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gt.edu.umg.invoice.model.TcRole;
import gt.edu.umg.invoice.model.TcUser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long userId;

	private String fullname;

	private String username;

	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrinciple(Long userId, String fullname, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.userId = userId;
		this.fullname = fullname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrinciple build(TcUser tcUser) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		TcRole tcRole = tcUser.getTcRole();
		authorities.add(new SimpleGrantedAuthority(tcRole.getRoleDesc()));
		return new UserPrinciple(tcUser.getUserId(), tcUser.getFullname(), tcUser.getUsername(), tcUser.getEmail(),
				tcUser.getPassword(), authorities);
	}

	public Long getUserId() {
		return userId;
	}

	public String getFullname() {
		return fullname;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserPrinciple user = (UserPrinciple) o;
		return Objects.equals(userId, user.userId);
	}
}