package com.camel_rabbitmq.controllers;

import com.camel_rabbitmq.models.FacebookSms;
import com.camel_rabbitmq.services.transit.TransitSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MessageController {

    private final TransitSms transitSms;

    @Autowired
    public MessageController(TransitSms transitSms) {
        this.transitSms = transitSms;
    }

    @GetMapping(value = "/sendMessage")
    public HttpStatus createEmployee(@RequestParam int messageId, @RequestParam String originator, @RequestParam String recipient, @RequestParam String content, @RequestParam long createdDate) {
        FacebookSms facebookSms = new FacebookSms(messageId, originator, recipient, content, createdDate);
        transitSms.testAddSmsToQueue(facebookSms);
        return HttpStatus.OK;
    }
}
