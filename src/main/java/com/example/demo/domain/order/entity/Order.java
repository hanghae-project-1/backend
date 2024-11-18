package com.example.demo.domain.order.entity;

import com.example.demo.common.entity.BaseEntity;
import com.example.demo.domain.entity.common.Status;
import com.example.demo.domain.store.entity.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_order")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends BaseEntity {

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

	@ManyToOne
	@JoinColumn(name = "store_id")
	Store store;

	@JsonIgnore
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<OrderDetail> orderDetailList;

	public void addOrderDetail(List<OrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public void updateStatus(Status.Order status) {
		this.status = status;
	}

}
