package org.broker.model;

public interface BrokerServer<T> {
    boolean healthCheck();

    T getConnection();
}
