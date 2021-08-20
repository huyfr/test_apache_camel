package com.camel_rabbitmq.controllers;

import com.camel_rabbitmq.models.FacebookSms;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class MessageController {

    @Produce(value = "direct:startQueuePoint")
    private ProducerTemplate producerTemplate;

    @GetMapping(value = "/sendMessage")
    public HttpStatus createEmployee(@RequestParam int messageId, @RequestParam String originator, @RequestParam String recipient, @RequestParam String content, @RequestParam long createdDate) {
        FacebookSms facebookSms = new FacebookSms(messageId, originator, recipient, content, createdDate);
        producerTemplate.asyncSendBody(producerTemplate.getDefaultEndpoint(), facebookSms);
        return HttpStatus.OK;
    }
}
