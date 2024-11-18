package com.example.demo.domain.user.common.entity;

import com.example.demo.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {

	@Id
	String username;

	@NotNull
	String password;

	@Enumerated(EnumType.STRING)
	Role role;


	public void changePassword(String newPassword) {
		this.password = newPassword;
	}

}
