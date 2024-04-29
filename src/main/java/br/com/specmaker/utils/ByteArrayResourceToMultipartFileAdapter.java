package br.com.specmaker.utils;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class ByteArrayResourceToMultipartFileAdapter implements MultipartFile {

    private final ByteArrayResource byteArrayResource;
    private final String name;
    private final String originalFilename;
    private final String contentType;

    public ByteArrayResourceToMultipartFileAdapter(ByteArrayResource byteArrayResource, String name, String originalFilename, String contentType) {
        this.byteArrayResource = byteArrayResource;
        this.name = name;
        this.originalFilename = originalFilename;
        this.contentType = contentType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return byteArrayResource == null || byteArrayResource.contentLength() == 0;
    }

    @Override
    public long getSize() {
        return byteArrayResource == null ? 0 : byteArrayResource.contentLength();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return byteArrayResource == null ? new byte[0] : byteArrayResource.getByteArray();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return byteArrayResource == null ? new ByteArrayInputStream(new byte[0]) : byteArrayResource.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        if (byteArrayResource != null) {
            try (OutputStream out = new FileOutputStream(dest)) {
                out.write(byteArrayResource.getByteArray());
            }
        }
    }
}
