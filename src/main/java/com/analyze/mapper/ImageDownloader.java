package com.analyze.mapper;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class ImageDownloader {
	public static byte[] saveImage(String url1, String name) {
		BufferedImage image = null;
		
		try {
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url1);
			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			int imageLength = (int)(entity.getContentLength());
			InputStream is = entity.getContent();

			byte[] imageBlob = new byte[imageLength];
			int bytesRead = 0;
			while (bytesRead < imageLength) {
			    int n = is.read(imageBlob, bytesRead, imageLength - bytesRead);
			    if (n <= 0)
			        ; // do some error handling
			    bytesRead += n;
			}
			return imageBlob;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
