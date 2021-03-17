package bsoft.com.clipboard.storage;

import bsoft.com.clipboard.repositories.PostMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Reader {

    @Autowired
    private PostMessageRepository postMessageRepository;

    @Bean
    public Reader getReader() {
        return this;
    }
}
