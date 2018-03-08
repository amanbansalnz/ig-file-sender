package util;

import com.ig.core.model.ActivemqConfiguration;
import com.ig.core.model.Order;

import java.time.LocalDateTime;

public class TestUtil {

    public static ActivemqConfiguration createActivemqConfiguration() {
        ActivemqConfiguration activemqConfiguration = new ActivemqConfiguration();
        activemqConfiguration.setBrokerUrl("tcp://localhost:61666");
        activemqConfiguration.setUserName("username");
        activemqConfiguration.setPassword("password");
        activemqConfiguration.setDestinationName("destinationName");
        activemqConfiguration.setIsQueue(true);
        return activemqConfiguration;
    }

    public static Order createOrder(){
        Order order = new Order();
        order.setAccount("AX001");
        order.setAction("BUY");
        order.setMarket("Market");
        order.setReceivedAt(LocalDateTime.parse("2017-10-03T20:58:43.642"));
        order.setSubmittedAt(LocalDateTime.parse("2017-10-03T20:58:43.641"));
        order.setSize(100);
        return order;
    }
}
