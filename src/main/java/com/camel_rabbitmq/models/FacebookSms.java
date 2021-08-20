package com.camel_rabbitmq.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FacebookSms {
    private int messageId;
    private String originator;
    private String recipient;
    private String content;
    private long createdDate;
}
