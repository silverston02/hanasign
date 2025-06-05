package com.hanasign.project.controller.abs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;


public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected ResponseEntity<Map<String, Object>> createResponseEntity(HttpStatus status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);
        return ResponseEntity.status(status).body(response);
    }

}
