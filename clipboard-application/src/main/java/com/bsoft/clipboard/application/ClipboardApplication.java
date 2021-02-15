package com.bsoft.clipboard.application;

import com.bsoft.clipboard.persist.repositories.RegistrationTicketRepository;
import com.bsoft.clipboard.persist.repositories.UserRepository;
import com.bsoft.clipboard.service.Clipboard;
import com.bsoft.clipboard.web.ClipController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(
        {
        ClipController.class,
        Clipboard.class,
                UserRepository.class,
                RegistrationTicketRepository.class
        }
)
public class ClipboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClipboardApplication.class, args);
    }

}
