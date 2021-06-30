package com.flab.kidsafer.mail;

public class EmailMessage {

    private String to;

    private String subject;

    private String message;

    public EmailMessage(EmailMessage.Builder builder) {
        this.to = builder.to;
        this.subject = builder.subject;
        this.message = builder.message;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder {

        private String to;
        private String subject;
        private String message;

        public Builder() {
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public EmailMessage build() {
            return new EmailMessage(this);
        }
    }
}
