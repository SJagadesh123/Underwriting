package com.zettamine.mpa.ucm.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@CreatedDate
	@Column(name="created_on", updatable = false)
	private LocalDateTime createdOn;
	
	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private String createdBy;
	
	@LastModifiedDate
	@Column(name = "updated_on", insertable = false)
	private LocalDateTime updatedOn;
	
	@LastModifiedBy
	@Column(name = "updated_by", insertable = false)
	private String updatedBy;
}
