package com.example.transactional.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/v1/mock")
@RequiredArgsConstructor
@Slf4j
public class MockPayment {

    @GetMapping
    @ResponseBody
    public String payment(@RequestParam(value = "delay", required = false)boolean delay) {
        simulateResponseTime(delay);
        return "OK";
    }

    private void simulateResponseTime(boolean delay) {
        if (delay) {
            long leftLimit = 1000L;
            long rightLimit = 40000L;
            long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
            try {
                Thread.sleep(generatedLong);
            } catch (InterruptedException e) {
                log.debug("Thread Interrupt exception, {}", e.getMessage());
            }
        }
    }
}
