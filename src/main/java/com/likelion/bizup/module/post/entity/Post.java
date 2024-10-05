package com.likelion.bizup.module.post.entity;

import java.util.Objects;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.likelion.bizup.global.common.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Post")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Builder
	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Post post))
			return false;

		return Objects.equals(this.id, post.getId()) &&
			Objects.equals(this.title, post.getTitle());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title);
	}
}
