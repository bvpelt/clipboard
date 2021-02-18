package bsoft.com.clipboard.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity(name = "ClipTopic")
@Table(name = "cliptopic")
public class ClipTopic {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Size(min=0, max=24)
    @Column(name = "NAME")
    private String name;

    @Size(min=0, max=128)
    @Column(name = "DESCRIPTION")
    private String description;
}
