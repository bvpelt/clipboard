package bsoft.com.clipboard.storage;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class TaskInfo implements Serializable {
    private boolean goOn;
    private long interval;
    private String name;
}
