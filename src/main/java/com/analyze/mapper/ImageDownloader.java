package com.analyze.mapper;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;

public class ImageDownloader {
	public static byte[] saveImage(String url1, String name) {
		BufferedImage image = null;
		
		try {

			URL url = new URL(url1);
			// read the url
			image = ImageIO.read(url);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// for jpg
			//ImageIO.write(image, "jpg", baos);
			
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();
			return imageInByte;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
