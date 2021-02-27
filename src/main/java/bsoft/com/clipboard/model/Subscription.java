package bsoft.com.clipboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity(name = "Subscription")
@Table(name = "subscription")
public class Subscription implements Serializable {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID")
    private Long id;

//    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

//    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "CLIPTOPIC_ID", nullable = false)
    private ClipTopic clipTopic;
}