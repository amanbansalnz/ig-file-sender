package com.ig.web.controllers;

import com.ig.Application;
import com.jayway.restassured.RestAssured;

import org.apache.activemq.broker.BrokerService;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Collection;
import java.util.Collections;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrdersControllerTest {
    public static final String ORDERS_FILE_NAME = "test-orders.xml";
    public static final String TCP_LOCALHOST_61616 = "tcp://localhost:61613";
    public static final String ORDER_QUEUE = "order-queue";
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "admin";
    private final String RELATIVE_PATH = "src/test/resources/";
    @LocalServerPort
    private int port;

    private BrokerService broker;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
        setupEmbeddedActiveMq();
    }

    @After
    public void tearDown() throws Exception {
        broker.stop();
        if (new File(ORDERS_FILE_NAME).exists()) {
            FileUtils.forceDelete(new File(ORDERS_FILE_NAME));
        }

    }

    @Test
    public void sendOrderToQueue() throws Exception {

         given().multiPart(new File(RELATIVE_PATH + ORDERS_FILE_NAME))
                .formParam("userName", ADMIN_USERNAME)
                .formParam("brokerUrl", TCP_LOCALHOST_61616)
                .formParam("password", ADMIN_USERNAME)
                .formParam("destinationName", ADMIN_PASSWORD)
                .formParam("isQueue", "true")
                .when().post("/ig-messaging-api/orders").then()
                .statusCode(200)
                .body(is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><orderResponse><order><accont>AX001</accont><SubmittedAt>2017-10-03T20:58:43.641</SubmittedAt><ReceivedAt>2017-10-03T20:58:43.642</ReceivedAt><market>Market</market><action>BUY</action><size>100</size></order></orderResponse>"));
    }


    @Test
    public void sendOrderToTopic() throws Exception {

        given().multiPart(new File(RELATIVE_PATH + ORDERS_FILE_NAME))
                .formParam("userName", ADMIN_USERNAME)
                .formParam("brokerUrl",  TCP_LOCALHOST_61616)
                .formParam("password", ADMIN_USERNAME)
                .formParam("destinationName", ADMIN_PASSWORD)
                .formParam("isQueue", "false")
                .when().post("/ig-messaging-api/orders").then()
                .statusCode(200)
                .body(is("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><orderResponse><order><accont>AX001</accont><SubmittedAt>2017-10-03T20:58:43.641</SubmittedAt><ReceivedAt>2017-10-03T20:58:43.642</ReceivedAt><market>Market</market><action>BUY</action><size>100</size></order></orderResponse>"));
    }


    private void setupEmbeddedActiveMq() throws Exception {
        broker = new BrokerService();
        broker.setBrokerName("embedded-activemq");
        broker.addConnector(TCP_LOCALHOST_61616);
        broker.setUseJmx(false);
        broker.setAdvisorySupport(false);
        broker.setPersistent(false);
        broker.start();
    }
}