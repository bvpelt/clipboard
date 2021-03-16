package bsoft.com.clipboard.storage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicTicket {
    private String status;
    private String topicKey; // unique key used to add content
}
