package bsoft.com.clipboard.application;

import bsoft.com.clipboard.storage.config.StorageConfig;
import bsoft.com.clipboard.storage.repositories.*;
import bsoft.com.clipboard.storage.service.Clipboard;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import javax.security.auth.Subject;

@SpringBootApplication
@Import ( {
        Clipboard.class,
        //StorageConfig.class
})
public class ClipboardApplication {

    public static void main(String[] args) {

        SpringApplication.run(ClipboardApplication.class, args);
    }

}
