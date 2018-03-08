package com.ig.web.converter;

import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.ig.core.model.ActivemqConfiguration;
import com.ig.web.requests.OrderRequest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(ZohhakRunner.class)
public class OrderRequestConverterToActivemqConfigurationTest {

    private final String RELATIVE_PATH = "src/test/resources/";

    private OrderRequestConverterToActivemqConfiguration orderRequestConverterToActivemqConfiguration;

    @Before
    public void setUp() throws Exception {
        orderRequestConverterToActivemqConfiguration = new OrderRequestConverterToActivemqConfiguration();
    }

    @TestWith({
            "true, true",
            "false, false",
            "null, false",
            "'', false",
    })
    public void convert(String isQueue, boolean expectedIsQueue) throws Exception {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setFile(new MockMultipartFile("test-orders.xml", "test-orders.xml", "xml", Files.readAllBytes(Paths.get(new File(RELATIVE_PATH + "test-orders.xml").getAbsolutePath()))));
        orderRequest.setBrokerUrl("brokerUrl");
        orderRequest.setUserName("username");
        orderRequest.setPassword("password");
        orderRequest.setDestinationName("destinationName");
        orderRequest.setIsQueue(isQueue);

        ActivemqConfiguration activemqConfiguration = orderRequestConverterToActivemqConfiguration.convert(orderRequest);

        assertThat(activemqConfiguration.getBrokerUrl(), is("brokerUrl"));
        assertThat(activemqConfiguration.getUserName(), is("username"));
        assertThat(activemqConfiguration.getPassword(), is("password"));
        assertThat(activemqConfiguration.getDestinationName(), is("destinationName"));
        assertThat(activemqConfiguration.isQueue(), is(expectedIsQueue));
    }
}