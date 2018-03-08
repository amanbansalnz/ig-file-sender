package com.ig.web.requests;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class OrderRequest {

    private MultipartFile file;

    @NotBlank
    private String brokerUrl;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @NotBlank
    private String destinationName;

    private String isQueue;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

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

    public String isQueue() {
        return isQueue;
    }

    public void setIsQueue(String queue) {
        isQueue = queue;
    }
}
