package bsoft.com.clipboard;

import bsoft.com.clipboard.model.Publisher;
import bsoft.com.clipboard.repositories.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@SpringBootTest
class ClipboardApplicationTests {

	@Autowired
	PublisherRepository publisherRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void test01() {
		List<Publisher> publishers = publisherRepository.findAll();

		Assert.notNull(publishers, "No publishers exist");
		Assert.isTrue(1 == publishers.size(), "Expected 1 publisher, found: " + publishers.size());
		Publisher publisher = publishers.get(0);
		log.info("Publisher, id: {}, name: {}, email: {}, endpoint: {}", publisher.getId(), publisher.getName(), publisher.getEmail(), publisher.getEndpoint());
	}
}
