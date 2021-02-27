package bsoft.com.clipboard.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostMessage implements Serializable {
    private String message;
    private String clipTopicName; // name of the cliptopic
}
