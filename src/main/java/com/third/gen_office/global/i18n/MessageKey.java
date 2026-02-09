package com.third.gen_office.global.i18n;

final class MessageKey {
    private static final String DEFAULT_NAMESPACE = "common";

    private final String namespace;
    private final String messageCd;
    private final String rawCode;

    private MessageKey(String namespace, String messageCd, String rawCode) {
        this.namespace = namespace;
        this.messageCd = messageCd;
        this.rawCode = rawCode;
    }

    static MessageKey parse(String code) {
        if (code == null || code.isBlank()) {
            return new MessageKey(DEFAULT_NAMESPACE, "unknown", "unknown");
        }
        int dotIndex = code.indexOf('.');
        if (dotIndex < 0 || dotIndex == code.length() - 1) {
            return new MessageKey(DEFAULT_NAMESPACE, code, code);
        }
        String namespace = code.substring(0, dotIndex);
        String messageCd = code.substring(dotIndex + 1);
        return new MessageKey(namespace, messageCd, code);
    }

    String namespace() {
        return namespace;
    }

    String messageCd() {
        return messageCd;
    }

    String rawCode() {
        return rawCode;
    }
}
