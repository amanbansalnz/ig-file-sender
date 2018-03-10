//package com.ig.remote.events.service;
//
//import com.ig.core.model.ActivemqConfiguration;
//
//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//import org.springframework.jms.core.JmsTemplate;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static util.TestUtil.createActivemqConfiguration;
//
//public class EventServiceTest {
//
//    public static final String DESTINATION_NAME = "destinationName";
//    private EventService service;
//
//    @Mock
//    private JmsTemplate mockJmsTemplate;
//
//    @Mock
//    private EventProducer mockEventProducer;
//
//    @Mock
//    private EventConsumer mockEventConsumer;
//
//
//    @Mock
//    private EventConsumer2 mockEventConsumer2;
//
//    @Mock
//    private Logger mockLogger;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//        service = new EventService(mockJmsTemplate, mockEventProducer, mockEventConsumer, mockEventConsumer2);
//    }
//
//    @Test
//    public void configureJmsTemplateWithQueue() throws Exception {
//        ActivemqConfiguration activemqConfiguration = createActivemqConfiguration();
//        ActiveMQConnectionFactory expectedConnectionFactory = getActiveMQConnectionFactory(activemqConfiguration);
//
//        assertJmsConnectionFactoryBeforeSetup();
//
//        when(mockJmsTemplate.getConnectionFactory()).thenReturn(expectedConnectionFactory);
//
//        service.setupEventConfiguration(activemqConfiguration);
//
//        assertions();
//    }
//
//    @Test
//    public void configureJmsTemplateWithTopic() throws Exception {
//        ActivemqConfiguration activemqConfiguration = createActivemqConfiguration();
//        activemqConfiguration.setIsQueue(false);
//        ActiveMQConnectionFactory expectedConnectionFactory = getActiveMQConnectionFactory(activemqConfiguration);
//
//        assertJmsConnectionFactoryBeforeSetup();
//
//        when(mockJmsTemplate.getConnectionFactory()).thenReturn(expectedConnectionFactory);
//
//        service.setupEventConfiguration(activemqConfiguration);
//
//        assertions();
//    }
//
//    private void assertJmsConnectionFactoryBeforeSetup() {
//        ActiveMQConnectionFactory connectionFactoryBeforeConfig = (ActiveMQConnectionFactory) service.getJmsTemplate().getConnectionFactory();
//        assertNull(connectionFactoryBeforeConfig);
//    }
//
//    private void assertions() {
//        ActiveMQConnectionFactory actualConnectionFactory = (ActiveMQConnectionFactory) service.getJmsTemplate().getConnectionFactory();
//        assertThat(actualConnectionFactory.getUserName(), is("username"));
//        assertThat(actualConnectionFactory.getPassword(), is("password"));
//        assertThat(actualConnectionFactory.getBrokerURL(), is("tcp://localhost:61666"));
//
//        verify(mockEventProducer).setQueue(DESTINATION_NAME);
//        verify(mockEventProducer).setJmsTemplate(mockJmsTemplate);
//    }
//
//    private ActiveMQConnectionFactory getActiveMQConnectionFactory(ActivemqConfiguration activemqConfiguration) {
//        ActiveMQConnectionFactory expectedConnectionFactory = new ActiveMQConnectionFactory();
//        expectedConnectionFactory.setBrokerURL(activemqConfiguration.getBrokerUrl());
//        expectedConnectionFactory.setUserName(activemqConfiguration.getUserName());
//        expectedConnectionFactory.setPassword(activemqConfiguration.getPassword());
//        return expectedConnectionFactory;
//    }
//
//}