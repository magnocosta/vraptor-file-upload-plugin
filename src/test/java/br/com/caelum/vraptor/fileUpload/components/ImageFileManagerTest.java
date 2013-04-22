package br.com.caelum.vraptor.fileUpload.components;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("static-access")
public class ImageFileManagerTest {

    private static final String[] IMG_EXTENSION_INVALID = { "doc", "txt", "pdf", "mp4", "" };

    private static final String FILE_NAME_DEFAULT = "imagem.";

    private ImageFileManagerDefault imageFileManager;

    @Before
    public void init() {
        imageFileManager = new ImageFileManagerDefault();
    }

    @Test
    public void testGetName() {
        String name = imageFileManager.generateFileName();
        Assert.assertNotNull(name);
        Assert.assertEquals(name.length(), imageFileManager.DEFAULT_FILE_NAME_FORMAT.length());
    }

    @Test
    public void testIsValidWithExtensionInvalid() {
        for (int i = 0; i < IMG_EXTENSION_INVALID.length; i++) {
            String file = FILE_NAME_DEFAULT + IMG_EXTENSION_INVALID[i];
            boolean valid = imageFileManager.isValid(file);
            Assert.assertEquals(false, valid);
        }
    }

    @Test
    public void testIsValidWithExtensionValid() {
        for (String ext : imageFileManager.getValidExtensioImage()) {
            String file = FILE_NAME_DEFAULT + ext;
            boolean valid = imageFileManager.isValid(file);
            Assert.assertEquals(true, valid);
        }
    }

    @Test
    public void testIsValidWithFileNameNull() {
        String file = null;
        boolean valid = imageFileManager.isValid(file);
        Assert.assertEquals(false, valid);
    }
    
    @Test
    public void testUploadFileWithSucess() {
        
    }

}