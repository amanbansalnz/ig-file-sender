package com.ig.core.model;

public class ActivemqConfiguration {

    private String brokerUrl;

    private String userName;

    private String password;

    private String destinationName;

    private boolean isQueue;

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public boolean isQueue() {
        return isQueue;
    }

    public void setIsQueue(boolean queue) {
        isQueue = queue;
    }
}
