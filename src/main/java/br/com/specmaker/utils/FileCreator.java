package br.com.specmaker.utils;

import br.com.specmaker.service.ProjetoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public final class FileCreator {

    private static final Logger logger = LogManager.getLogger(FileCreator.class);

    public static File createFileBy(String fileName, String content){
        File arquivo = new File(fileName);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            logger.error(e);
        }

        return arquivo;
    }

}
