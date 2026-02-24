package com.third.gen_office.domain.message;

import java.io.Serializable;
import java.util.Objects;

public class MessageId implements Serializable {
    private String messageCd;
    private String langCd;
    private String namespace;

    public MessageId() {
    }

    public MessageId(String messageCd, String langCd, String namespace) {
        this.messageCd = messageCd;
        this.langCd = langCd;
        this.namespace = namespace;
    }

    public String getMessageCd() {
        return messageCd;
    }

    public String getLangCd() {
        return langCd;
    }

    public String getNamespace() {
        return namespace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageId that = (MessageId) o;
        return Objects.equals(messageCd, that.messageCd)
            && Objects.equals(langCd, that.langCd)
            && Objects.equals(namespace, that.namespace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageCd, langCd, namespace);
    }
}
