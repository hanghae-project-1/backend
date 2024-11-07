package com.example.demo.domain.entity;

import com.example.demo.domain.entity.common.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_order")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@NotNull
	Integer totalPrice;

	@NotNull
	String destinationAddr;

	@NotNull
	String orderRequest;

	@NotNull
	@Builder.Default
	@ColumnDefault("false")
	Boolean isTakeOut = false;

	@NotNull
	@Enumerated(EnumType.STRING)
	Status.Order status;

}
