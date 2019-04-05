package com.kuryla.test.requestinitiator.handler.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public class ProcessorInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessorInfo.class);

    private String key;
    private File file;
    private FileOutputStream fileOutputStream;
    private long totalBytes = 0;

    public ProcessorInfo(String key, String outputFilePath) {
        this.key = key;
        this.file = new File(outputFilePath + File.separator + key + ".pdf");
    }

    public String getKey() {
        return key;
    }

    public void writeData(byte[] data) {
        try {

            if (fileOutputStream == null) {
                if (!file.exists()) {
                    file.createNewFile();
                }
                fileOutputStream = new FileOutputStream(file);
            }
            totalBytes += data.length;
            fileOutputStream.write(data);
        } catch (IOException ioe) {
            closeFileStream();
            throw new UncheckedIOException(ioe);
        }
    }

    public void closeFileStream() {
        try {
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public long getTotalBytes() {
        return totalBytes;
    }
}
