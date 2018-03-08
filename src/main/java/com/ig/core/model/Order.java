package com.ig.core.model;

import com.ig.core.util.LocalDateAdapter;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "Order")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements Serializable {

    @XmlElement(name = "accont")
    private String account;

    @XmlElement(name = "SubmittedAt")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDateTime submittedAt;

    @XmlElement(name = "ReceivedAt")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDateTime receivedAt;

    private String market;

    private String action;

    private int size;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return new EqualsBuilder()
                .append(getSize(), order.getSize())
                .append(getAccount(), order.getAccount())
                .append(getSubmittedAt(), order.getSubmittedAt())
                .append(getReceivedAt(), order.getReceivedAt())
                .append(getMarket(), order.getMarket())
                .append(getAction(), order.getAction())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getAccount())
                .append(getSubmittedAt())
                .append(getReceivedAt())
                .append(getMarket())
                .append(getAction())
                .append(getSize())
                .toHashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("acount='").append(account).append('\'');
        sb.append(", SubmittedAt='").append(submittedAt).append('\'');
        sb.append(", ReceivedAt='").append(receivedAt).append('\'');
        sb.append(", market='").append(market).append('\'');
        sb.append(", action='").append(action).append('\'');
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }

}
