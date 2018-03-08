package com.ig.web.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/upload")
public class FileController {

    private final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @GetMapping
    public String upload() {
        LOGGER.info(">>> Request: Retrieve upload template");
        return "upload";
    }
}
