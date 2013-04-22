package br.com.caelum.vraptor.fileUpload.components;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.fileUpload.FileManager;
import br.com.caelum.vraptor.fileUpload.config.ImageConfig;

public class ImageFileManagerDefault implements FileManager {

	protected static final List<String> DEFAULT_IMG_EXTENSION_VALID = Arrays
			.asList("gif", "jpg", "png", "jpeg");

	protected static final String FILE_EXTENSION_DELIMITADOR = ".";

	protected static final int NOT_ALIGN_HEIGTH = -1;

	private static final Logger LOG = Logger
			.getLogger(ImageFileManagerDefault.class);

	private Environment config;

	private String imagePath;

	private List<String> validExtensionImage;

	public ImageFileManagerDefault(Environment config) {
		this.config = config;
		this.imagePath = this.config.get(PropertiesConfig.KEY_IMG_PATH);
		this.validExtensionImage = getValidExtensioImage();
	}

	protected static final ImageConfig[] imageConfigs = {
			new ImageConfig("mini_", 149, 102),
			new ImageConfig("medium_", 249, 202),
			new ImageConfig("large_", 503, 408) };

	@Override
	public String generateFileName() {
		String pattern = "ddMMyyyyhhmmssSSS";
		Calendar now = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String fileName = format.format(now.getTime());
		LOG.debug(MessageFormat.format(LogMessages.GENERATE_NAME_SUCESS,
				fileName));
		return fileName;
	}

	@Override
	public boolean isValid(String fileName) {
		boolean valid = Boolean.FALSE;
		if (fileName != null) {
			String extension = fileName.substring(fileName
					.lastIndexOf(FILE_EXTENSION_DELIMITADOR));
			for (String ext : validExtensionImage) {
				if (extension
						.equalsIgnoreCase(FILE_EXTENSION_DELIMITADOR + ext)) {
					valid = Boolean.TRUE;
				}
			}
		}
		if (valid)
			LOG.debug(MessageFormat
					.format("Arquivo valido para upload: [nome:{0}, extensoesValidas: {1}]",
							fileName, validExtensionImage));
		else
			LOG.warn(MessageFormat
					.format("Arquivo invalido para upload: [nome:{0}, extensoesValidas: {1}]",
							fileName, validExtensionImage));

		return valid;
	}

	@Override
	public void upload(InputStream file, String folder, String fileName) {
		try {
			BufferedImage image = ImageIO.read(file);
			File customerDirectory = new File(PropertiesConfig.KEY_IMG_PATH
					+ folder);
			if (!customerDirectory.exists()) {
				customerDirectory.mkdir();
				LOG.info("Criando o diretorio de imagens do usuario");
			}
			for (ImageConfig config : imageConfigs) {
				File fileImage = new File(customerDirectory, config.getPrefix()
						+ fileName);
				LOG.info(MessageFormat.format(
						"Redimensionando a imagem: [nome:{0}, config:{1}]",
						fileImage.toString(), config.toString()));
				BufferedImage resizeImage = resizeImage(image,
						config.getWidth(), config.getHeight());
				ImageIO.write(resizeImage, "JPG", fileImage);
				LOG.info(MessageFormat.format(
						"Imagem gerada com sucesso: [nome:{0}, config: {1}]",
						fileImage.toString(), config.toString()));
			}
		} catch (IOException error) {
			error.printStackTrace();
			LOG.error(
					MessageFormat
							.format("Ocorreu algum erro ao ler a imagem do upload: [message: {0}]",
									error.getMessage()), error);
		}
	}

	@Override
	public File download(String folder, String fileName) {
		String path = MessageFormat.format("{0}/{1}/{2}", imagePath, folder,
				fileName);
		LOG.info(MessageFormat.format(
				"Realizando o download da imagem: [uri:{0}]", path));
		File file = new File(path);
		return file;
	}

	@Override
	public void delete(String folder, String fileName) {
		try {
			for (ImageConfig config : imageConfigs) {
				File file = new File(imagePath + folder, config.getPrefix()
						+ fileName);
				if (file.isFile()) {
					file.delete();
					LOG.info(MessageFormat.format(
							"Arquivo deletado com sucesso: [uri:{0}]",
							file.toString()));
				} else {
					LOG.warn(MessageFormat.format(
							"Nao foi possivel deletar a pasta: [uri:{0}]",
							file.toString()));
				}
			}
		} catch (Exception error) {
			String message = MessageFormat
					.format("Ocorreu algum erro ao tenta deletar o arquivo: [uri:{0}, erro: {1}]",
							fileName, error.getMessage());
			LOG.error(message, error);
		}
	}

	protected int[] calculateDimension(int oldWidth, int oldHeight,
			int newWidth, int newHeight) {
		if (newHeight == NOT_ALIGN_HEIGTH) {
			newHeight = oldHeight;
		}

		int width = (oldWidth * newHeight) / oldHeight;
		int height = newHeight;

		if (width < newWidth) {
			width = newWidth;
			height = (oldHeight * newWidth) / oldWidth;
		}

		int[] dimension = { width, height };
		return dimension;
	}

	protected BufferedImage resizeImage(BufferedImage oldImage, int newWidth,
			int newHeight) throws IOException {
		int oldHeight = oldImage.getHeight();
		int oldWidth = oldImage.getWidth();

		int[] dimension = calculateDimension(oldWidth, oldHeight, newWidth,
				newHeight);
		int width = dimension[0];
		int height = dimension[0];

		BufferedImage newImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = newImage.createGraphics();
		g.drawImage(oldImage, 0, 0, width, height, null);

		return newImage;
	}

	protected List<String> getValidExtensioImage() {
		if (config.has(PropertiesConfig.KEY_IMG_EXTENSION_VALID)) {
			String value = config.get(PropertiesConfig.KEY_IMG_EXTENSION_VALID);
			return Arrays.asList(value.split(","));
		} else {
			return DEFAULT_IMG_EXTENSION_VALID;
		}
	}

	@Override
	public void upload(InputStream file, String fileName) {
		// TODO Auto-generated method stub

	}

}