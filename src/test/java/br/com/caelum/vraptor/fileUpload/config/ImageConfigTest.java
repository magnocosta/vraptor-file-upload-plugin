package br.com.caelum.vraptor.fileUpload.config;

import java.text.MessageFormat;

import junit.framework.Assert;

import org.junit.Test;

public class ImageConfigTest {

	private ImageConfig imageConfig;

	@Test
	public void testaCriacaoDoObjetoComSucesso() {
		String prefix = "mini_";
		int width = 10;
		int height = 20;
		String tostring = MessageFormat.format("[prefixo: {0}, largura: {1}, altura: {2}]", prefix, width, height);
		imageConfig = new ImageConfig(prefix, width, height);
		Assert.assertEquals("mini_", imageConfig.getPrefix());
		Assert.assertEquals(10, imageConfig.getWidth());
		Assert.assertEquals(20, imageConfig.getHeight());
		Assert.assertEquals(tostring, imageConfig.toString());
	}
	
}
