package com.third.gen_office.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "creation_date", insertable = false, updatable = false)
    private String creationDate;

    @LastModifiedBy
    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_date", insertable = false, updatable = false)
    private String lastUpdatedDate;

}
