package com.example.domain.entity.user;

import com.example.domain.entity.common.CommonConstant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "P_User")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@NotNull
	String nickname;

	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	@Pattern(regexp = CommonConstant.RegExp.EMAIL)
	String email;

	@NotNull
	@Size(max = 255)
	String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return "";
	}
}
