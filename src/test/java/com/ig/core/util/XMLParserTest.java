package com.ig.core.util;

import com.googlecode.zohhak.api.Coercion;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.ig.core.model.Order;
import com.ig.web.exception.InvalidResponseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(ZohhakRunner.class)
public class XMLParserTest {

    private XMLParser xmlParser;

    @Before
    public void setUp() throws Exception {
        xmlParser = new XMLParser();
    }

    @TestWith({
            "<Order>" +
                    "<accont>AX002</accont>" +
                    "<SubmittedAt>1507060723651</SubmittedAt>" +
                    "<ReceivedAt>1507060723652</ReceivedAt>" +
                    "<market>market</market>" +
                    "<action>BUY</action>" +
                    "<size>200</size>" +
                    "</Order>, AX002_2017-10-03T20:58:43.651_2017-10-03T20:58:43.652_market_BUY_200",
            "<Order>" +
                    "<accont>AX001</accont>" +
                    "<SubmittedAt>1507060723641</SubmittedAt>" +
                    "<ReceivedAt>1507060723642</ReceivedAt>" +
                    "<market>market</market>" +
                    "<action>BUY</action>" +
                    "<size>100</size>" +
                    "</Order>,AX001_2017-10-03T20:58:43.641_2017-10-03T20:58:43.642_market_BUY_100"
    })
    public void unmarshal(String xml, Order expectedOrder) throws Exception {
        Order actualOrder = (Order) xmlParser.unmarshal(xml, Order.class);
        assertTrue(actualOrder.equals(expectedOrder));
    }


    @TestWith({
            "AX001_2017-10-03T20:58:43.641_2017-10-03T20:58:43.642_market_BUY_100, com.ig.core.model.Order",
            "'', com.ig.core.model.Order",
    })
    public void failToUmarshalWhenXmlFormatIncorrect(String xml, String clazz) throws Exception {
        try {
            xmlParser.unmarshal("AX001_2017-10-03T20:58:43.641_2017-10-03T20:58:43.642_market_BUY_100", Order.class);
        } catch (InvalidResponseException e) {
            assertThat(e.getMessage(), is("Unable to unmarshal xml"));
        }
    }

    @Test
    public void failToUmarshal() throws Exception {
        try {
            xmlParser.unmarshal(null, null);
        } catch (InvalidResponseException e) {
            assertThat(e.getMessage(), is("Unable to unmarshal xml"));
        }
    }

    @Coercion
    public Order mapToOrder(String value) {
        String[] values = value.split("_");
        Order order = new Order();
        order.setAccount(values[0].trim());
        order.setSubmittedAt(LocalDateTime.parse(values[1].trim()));
        order.setReceivedAt(LocalDateTime.parse(values[2].trim()));
        order.setMarket(values[3].trim());
        order.setAction(values[4].trim());
        order.setSize(Integer.valueOf(values[5].trim()));
        return order;
    }

}
