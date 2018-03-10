package com.ig.core.service;

import com.ig.core.model.ActivemqConfiguration;
import com.ig.core.model.Order;
import com.ig.core.util.XMLParser;
import com.ig.remote.events.service.EventService;
import com.ig.web.exception.ClientException;
import com.ig.web.exception.InvalidResponseException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    private final EventService eventService;

    private final XMLParser xmlParser;

    public OrderService(EventService eventService, XMLParser xmlParser) {
        this.eventService = eventService;
        this.xmlParser = xmlParser;
    }

    public List<Order> process(MultipartFile file, ActivemqConfiguration activemqConfiguration) throws InvalidResponseException {

        if (file == null || file.isEmpty()) {
            throw new ClientException("Please provide valid file");
        }

        uploadFile(file);
        List<String> xmlChildren = readFileContents(file.getOriginalFilename());
        List<Order> orders = new ArrayList<>();

        JmsTemplate queue = eventService.setupEventConfiguration(activemqConfiguration, true);
        JmsTemplate topic = eventService.setupEventConfiguration(activemqConfiguration, false);

        for (int i = 1; i < xmlChildren.size(); i++) {
            try {
                Order order = (Order) xmlParser.unmarshal(xmlChildren.get(i), Order.class);
                if (order != null) {
                    eventService.getEventProducer().sendMessage(topic, order,activemqConfiguration.getDestinationName());
                    eventService.getEventProducer().sendMessage(queue,order,activemqConfiguration.getDestinationName()+1);
                    LOGGER.debug("Order {} processed", order.toString());
                    orders.add(order);
                }
            } catch (InvalidResponseException e) {
                LOGGER.error("{}", e.getStackTrace());
            }
        }
        return orders;
    }

    private List<String> readFileContents(String originalFilename) throws InvalidResponseException {
        try {
            File uploadedFile = new File(originalFilename);
            return FileUtils.readLines(uploadedFile);
        } catch (IOException e) {
            LOGGER.error("{}", e.getStackTrace());
            throw new InvalidResponseException("Unable to read file contents");
        }
    }

    private void uploadFile(MultipartFile file) throws InvalidResponseException {
        try {
            Path path = Paths.get(new File("").getAbsolutePath() + "/" + file.getOriginalFilename());
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            LOGGER.error("{}", e.getStackTrace());
            throw new InvalidResponseException("Unable to upload file");
        }
    }
}
