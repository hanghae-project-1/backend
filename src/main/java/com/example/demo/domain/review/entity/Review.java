package com.example.demo.domain.review.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.domain.order.entity.Order;
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
@Table(name = "p_review")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	@NotNull
	String content;

	@NotNull
	Integer rating;

	String imageUrl;

	@OneToOne
	@JoinColumn(name = "order_id")
	Order order;

}
