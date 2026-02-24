package com.third.gen_office.domain.message;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_cm_message")
@IdClass(MessageId.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MessageEntity {
    @Id
    @Column(name = "message_cd")
    private String messageCd;

    @Id
    @Column(name = "lang_cd")
    private String langCd;

    @Id
    @Column(name = "namespace")
    private String namespace;

    @Column(name = "message_txt")
    private String messageTxt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "creation_date")
    private String creationDate;

    @LastModifiedBy
    @Column(name = "last_updated_by", insertable = false)
    private String lastUpdatedBy;

    @Column(name = "last_updated_date", insertable = false)
    private String lastUpdatedDate;
}
