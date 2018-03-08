package com.ig.core.service;

import com.googlecode.zohhak.api.runners.ZohhakRunner;
import com.ig.core.model.ActivemqConfiguration;
import com.ig.core.model.Order;
import com.ig.core.util.XMLParser;
import com.ig.remote.events.service.EventProducer;
import com.ig.remote.events.service.EventService;
import com.ig.web.exception.ClientException;
import com.ig.web.exception.InvalidResponseException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static util.TestUtil.createActivemqConfiguration;
import static util.TestUtil.createOrder;

@RunWith(ZohhakRunner.class)
public class OrderServiceTest {

    public static final String ORDERS_FILE_NAME = "test-orders.xml";
    public static final String ORDERS_EMPTY_FILE_NAME = "test-orders-empty.xml";
    public static final String EMPTY_FILE_NAME = "empty-file.xml";
    private final String RELATIVE_PATH = "src/test/resources/";
    private final String TEST_XML_VALUE = "<Order><accont>AX001</accont><SubmittedAt>1507060723641</SubmittedAt><ReceivedAt>1507060723642</ReceivedAt><market>Market</market><action>BUY</action><size>100</size></Order>";

    private final File ORDER_FILE = new File(RELATIVE_PATH + ORDERS_FILE_NAME);
    private final File EMPTY_ORDER_FILE = new File(RELATIVE_PATH + ORDERS_EMPTY_FILE_NAME);
    private final File EMPTY_FILE = new File(RELATIVE_PATH + EMPTY_FILE_NAME);

    private OrderService service;

    private MultipartFile mockFile;

    @Mock
    private EventService mockEventService;

    @Mock
    private EventProducer mockEventProducer;

    @Mock
    private XMLParser mockXMLParser;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new OrderService(mockEventService, mockXMLParser);
    }

    @After
    public void tearDown() throws Exception {
        if(new File(ORDERS_FILE_NAME).exists()) {
            FileUtils.forceDelete(new File(ORDERS_FILE_NAME));
        }

        if(new File(EMPTY_FILE_NAME).exists()) {
            FileUtils.forceDelete(new File(EMPTY_FILE_NAME));
        }

        if(new File(ORDERS_EMPTY_FILE_NAME).exists()) {
            FileUtils.forceDelete(new File(ORDERS_EMPTY_FILE_NAME));
        }
    }

    @Test
    public void process() throws Exception {
        Order expectedOrder = createOrder();
        ActivemqConfiguration activemqConfiguration = createActivemqConfiguration();
        mockFile = new MockMultipartFile(ORDERS_FILE_NAME, ORDERS_FILE_NAME, "xml", Files.readAllBytes(Paths.get(ORDER_FILE.getAbsolutePath())));

        when(mockXMLParser.unmarshal(TEST_XML_VALUE, Order.class)).thenReturn(expectedOrder);
        when(mockEventService.getEventProducer()).thenReturn(mockEventProducer);

        List<Order> orders = service.process(mockFile, activemqConfiguration);

        assertThat(orders, hasSize(1));
        assertTrue(orders.get(0).equals(expectedOrder));
        verify(mockEventService).setupEventConfiguration(activemqConfiguration);
        verify(mockXMLParser).unmarshal(TEST_XML_VALUE, Order.class);
        verify(mockEventService).getEventProducer();
        verify(mockEventProducer).sendMessage(expectedOrder);
    }

    @Test(expected = ClientException.class)
    public void failToProcessWhenXmlUnmarshalFails() throws Exception {
        try {
            service.process(null, createActivemqConfiguration());
        } catch (ClientException e) {
            assertThat(e.getMessage(), is("Please provide valid file"));
            throw e;
        }
    }

    @Test(expected = ClientException.class)
    public void failToProcessWhenFileContentIsEmpty() throws Exception {
        try {
            mockFile = new MockMultipartFile(EMPTY_FILE_NAME, EMPTY_FILE_NAME, "xml", Files.readAllBytes(Paths.get(EMPTY_FILE.getAbsolutePath())));
            service.process(mockFile, createActivemqConfiguration());
        } catch (ClientException e) {
            assertThat(e.getMessage(), is("Please provide valid file"));
            throw e;
        }
    }

    @Test
    public void failToProcessWhenNoOrdersInXmlFile() throws Exception {
        ActivemqConfiguration activemqConfiguration = createActivemqConfiguration();
        mockFile = new MockMultipartFile(ORDERS_EMPTY_FILE_NAME, ORDERS_EMPTY_FILE_NAME, "xml", Files.readAllBytes(Paths.get(EMPTY_ORDER_FILE.getAbsolutePath())));

        List<Order> orders = service.process(mockFile, activemqConfiguration);
        when(mockEventService.getEventProducer()).thenReturn(mockEventProducer);

        assertThat(orders, hasSize(0));
        verify(mockEventService).setupEventConfiguration(activemqConfiguration);
    }


    @Test
    public void failToProcessWhenFileIsNotPresent() throws Exception {
        ActivemqConfiguration activemqConfiguration = createActivemqConfiguration();
        mockFile = new MockMultipartFile(ORDERS_EMPTY_FILE_NAME, ORDERS_EMPTY_FILE_NAME, "xml", Files.readAllBytes(Paths.get(EMPTY_ORDER_FILE.getAbsolutePath())));

        when(mockXMLParser.unmarshal(TEST_XML_VALUE, Order.class)).thenThrow(new InvalidResponseException("invalid xml format"));
        when(mockEventService.getEventProducer()).thenReturn(mockEventProducer);

        List<Order> orders = service.process(mockFile, activemqConfiguration);

        assertThat(orders, hasSize(0));
        verify(mockEventService).setupEventConfiguration(activemqConfiguration);

    }

    @Test(expected = InvalidResponseException.class)
    public void failToProcessWhenUnableToUploadFile() throws Exception {
        mockFile = new MockMultipartFile(ORDERS_FILE_NAME, Files.readAllBytes(Paths.get(ORDER_FILE.getAbsolutePath())));
        try {
            service.process(mockFile, createActivemqConfiguration());
        } catch (InvalidResponseException e) {
            assertThat(e.getMessage(), is("Unable to upload file"));
            throw e;
        }
    }

}