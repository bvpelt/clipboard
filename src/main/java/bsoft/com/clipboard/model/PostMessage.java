package bsoft.com.clipboard.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.*;

@Data
@Entity(name = "PostMessage")
@Table(name = "postmessage")
public class PostMessage implements Serializable {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "MESSAGE")
    private String message;

    @NotBlank
    @Size(min = 0, max = 24)
    @Column(name = "CLIPTOPIC_NAME")
    private String clipTopicName; // name of the cliptopic

    @NotBlank
    @Size(min = 0, max = 48)
    @Column(name = "APIKEY")
    private String apiKey;

    public static byte[] objToByte(PostMessage postMessage) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objStream = new ObjectOutputStream(byteStream);
        objStream.writeObject(postMessage);

        return byteStream.toByteArray();
    }

    public static Object byteToObj(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objStream = new ObjectInputStream(byteStream);

        return objStream.readObject();
    }
}
