package com.example.domain.entity;

import com.example.domain.entity.common.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_payment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@NotNull
	String approvalNumber;

	@NotNull
	String merchantNumber;

	@NotNull
	String pgProvider;

	@NotNull
	Integer paymentPrice;

	@NotNull
	@Enumerated(EnumType.STRING)
	Status.Payment status;
}
