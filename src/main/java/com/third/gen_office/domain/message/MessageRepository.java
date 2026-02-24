package com.third.gen_office.domain.message;

import com.third.gen_office.mis.admin.message.dto.MissingMessageItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository
    extends JpaRepository<MessageEntity, MessageId>, JpaSpecificationExecutor<MessageEntity> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        value = """
            UPDATE tb_cm_message
            SET message_txt = :messageTxt,
                last_updated_date = CURRENT_TIMESTAMP
            WHERE namespace = :namespace
              AND message_cd = :messageCd
              AND lang_cd = :langCd
            """,
        nativeQuery = true
    )
    int updateMessageText(
        @Param("namespace") String namespace,
        @Param("messageCd") String messageCd,
        @Param("langCd") String langCd,
        @Param("messageTxt") String messageTxt
    );

    @Query("""
        select new com.third.gen_office.mis.admin.message.dto.MissingMessageItem(
            b.namespace,
            b.messageCd,
            :baseLang,
            :targetLang
        )
        from MessageEntity b
        where b.langCd = :baseLang
          and not exists (
            select 1
            from MessageEntity t
            where t.langCd = :targetLang
              and t.namespace = b.namespace
              and t.messageCd = b.messageCd
          )
          and (:namespace is null or :namespace = '' or b.namespace = :namespace)
        order by b.namespace asc, b.messageCd asc
        """)
    List<MissingMessageItem> findMissingMessages(
        @Param("namespace") String namespace,
        @Param("baseLang") String baseLang,
        @Param("targetLang") String targetLang
    );
}
