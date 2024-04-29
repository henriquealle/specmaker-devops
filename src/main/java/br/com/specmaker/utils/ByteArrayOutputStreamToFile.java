package br.com.specmaker.utils;

import br.com.specmaker.controller.QueryController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteArrayOutputStreamToFile {

    private static final Logger logger = LogManager.getLogger(ByteArrayOutputStreamToFile.class);

    public static File createFileBy(String fileName, ByteArrayOutputStream byteArrayOutputStream) {
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(byteArray);
        } catch (IOException e) {
            logger.error(e);
        }

        return file;
    }
}
