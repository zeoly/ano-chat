package com.yahacode.yagami.service.client2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengyongli 2018-05-25
 */
@RestController
public class SleuthController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/sleuth")
    public String sleuth() {
        logger.info("sleuth client 2");
        return "sleuth result";
    }
}
