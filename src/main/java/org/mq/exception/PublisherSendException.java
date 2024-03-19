package org.mq.exception;

public class PublisherSendException extends RuntimeException {

    public PublisherSendException(String message, Throwable throwable){
        super(message, throwable);
    }
}
