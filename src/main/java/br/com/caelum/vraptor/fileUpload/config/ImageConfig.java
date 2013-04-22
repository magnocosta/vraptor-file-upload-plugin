package br.com.caelum.vraptor.fileUpload.config;

import java.text.MessageFormat;

public class ImageConfig {

	private String prefix;

	private int width;

	private int height;

	public ImageConfig(String prefix, int width, int height) {
		this.prefix = prefix;
		this.width = width;
		this.height = height;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
    public String toString() {
        String toString = MessageFormat.format("IMAGE_CONFIG [prefixo: {0}, largura: {1}, altura: {2}]", prefix, width, height);
        return toString;
    }
}