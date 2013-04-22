package br.com.caelum.vraptor.fileUpload;

import java.io.File;
import java.io.InputStream;

public interface FileManager {

    public String generateFileName();
    
    public boolean isValid(String fileName);
    
    public void upload(InputStream file, String folder, String fileName);
    
    public void upload(InputStream file, String fileName);
    
    public File download(String folder, String fileName);
    
    public void delete(String folder, String fileName);
    
}