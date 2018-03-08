package com.ig.web.controllers;


import com.ig.core.model.ActivemqConfiguration;
import com.ig.core.model.Order;
import com.ig.core.service.OrderService;
import com.ig.web.exception.InvalidResponseException;
import com.ig.web.requests.OrderRequest;
import com.ig.web.responses.OrderResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

    private final OrderService orderService;

    private final ConversionService converter;

    public OrdersController(OrderService orderService, ConversionService converter) {
        this.orderService = orderService;
        this.converter = converter;
    }

    @PostMapping(produces = MediaType.APPLICATION_XML_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public OrderResponse processOrder(@Valid @ModelAttribute("orderRequest") OrderRequest orderRequest) throws InvalidResponseException {

        LOGGER.info(">>> Request: Processing orders");

        List<Order> orders = orderService.process(orderRequest.getFile(), converter.convert(orderRequest, ActivemqConfiguration.class));

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrder(orders);

        LOGGER.info("<<< Response: Processed orders");

        return orderResponse;
    }
}
