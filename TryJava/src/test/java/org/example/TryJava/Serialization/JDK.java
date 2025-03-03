package org.example.TryJava.Serialization;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Arrays;

public class JDK {
    @Test
    void testSerialization() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (ObjectOutputStream output = new ObjectOutputStream(buffer)) {
            // 写入int:
            output.writeInt(12345);
            output.writeInt(54675);
            // 写入String:
            output.writeUTF("Hello");
            // 写入Object:
            output.writeObject(Double.valueOf(123.456));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Arrays.toString(buffer.toByteArray()));

        ByteArrayInputStream bufferIn = new ByteArrayInputStream(buffer.toByteArray());
        try (ObjectInputStream input = new ObjectInputStream(bufferIn)) {
            int n = input.readInt();
            int n1 = input.readInt();
            String s = input.readUTF();
            Double d = (Double) input.readObject();

            System.out.println(n);
            System.out.println(n1);
            System.out.println(s);
            System.out.println(d);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
