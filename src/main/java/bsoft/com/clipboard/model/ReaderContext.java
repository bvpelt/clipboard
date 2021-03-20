package bsoft.com.clipboard.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Entity(name = "ReaderContext")
@Table(name = "readercontext")
public class ReaderContext implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(min = 0, max = 24)
    @Column(name = "CONTEXT_NAME")
    private String contextName; // valid names "reader"

    @Column(name = "LASTID")
    private Long lastId;

}
