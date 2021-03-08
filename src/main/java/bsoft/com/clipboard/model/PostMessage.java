package bsoft.com.clipboard.model;

import lombok.Data;

import java.io.*;

@Data
public class PostMessage implements Serializable {
    private String message;
    private String clipTopicName; // name of the cliptopic

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
