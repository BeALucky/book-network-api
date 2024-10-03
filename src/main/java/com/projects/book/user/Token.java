package com.projects.book.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.projects.book.role.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique = true)
	private String token;
	
	private LocalDateTime createdAt;
	private LocalDateTime expiresAt;
	private LocalDateTime validatedAt;
	
	@ManyToOne
	@JoinColumn(name="userId", nullable = false)
	private User user;
}
