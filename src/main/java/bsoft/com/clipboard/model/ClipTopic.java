package bsoft.com.clipboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity(name = "ClipTopic")
@Table(name = "cliptopic")
public class ClipTopic implements Serializable {
    @JsonIgnore
    @OneToMany(mappedBy = "clipTopic", fetch = FetchType.LAZY)
    Set<Subscription> subscriptions;
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID")
    private Long id;
    @NotBlank
    @Size(min = 0, max = 24)
    @Column(name = "NAME")
    private String name;
    @Size(min = 0, max = 128)
    @Column(name = "DESCRIPTION")
    private String description;
}
