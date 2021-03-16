package bsoft.com.clipboard.storage.test;

import bsoft.com.clipboard.storage.config.StorageConfig;
import bsoft.com.clipboard.storage.model.Publisher;
import bsoft.com.clipboard.storage.repositories.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.fenum.qual.SwingTextOrientation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
public class StorageTest {

    private StorageConfig storageConfig;

    @Autowired
    public StorageTest(final StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Test
    public void test01() {
        PublisherRepository publisherRepository = storageConfig.getPublisherRepository();
        List<Publisher> publishers = publisherRepository.findAll();
        Assert.notNull(publishers, "Publishers expected");

    }
}
