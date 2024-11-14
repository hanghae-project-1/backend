package com.example.demo.domain.order.entity;

import com.example.demo.domain.entity.Menu;
import com.example.demo.domain.entity.common.BaseEntity;
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
@Table(name = "p_order_detail")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@NotNull
	Integer price;

	@NotNull
	Integer quantity;

	@ManyToOne
	@JoinColumn(name = "order_id")
	Order order;

	@OneToOne
	@JoinColumn(name = "menu_id")
	Menu menu;

}