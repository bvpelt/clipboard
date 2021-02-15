package com.bsoft.clipboard.application;

import com.bsoft.clipboard.web.ClipController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class ClipboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClipboardApplication.class, args);
    }

}
