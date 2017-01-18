package com.briannakayama.jvm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.FileSystemNotFoundException;

import javax.imageio.ImageIO;

import com.briannakayama.draw2d.ImageFactory;

public class ImageFactoryJVM extends ImageFactory<BufferedImage>{

	private FileFilter fs = new FileFilter() {

		@Override
		public boolean accept(File f) {
			if (f.getAbsolutePath().endsWith(".jpg")) {
				return true;
			}

			if (f.getAbsolutePath().endsWith(".png")) {
				return true;
			}

			return false;
		}

	};
	
	@Override
	public boolean loadDirectory(String location) {
		File f = new File(location);
		if (f.isDirectory()) {
			File[] images = f.listFiles(fs);
			for (int i = 0; i < images.length; i++) {
				BufferedImage bI = null;
				String name = images[i].getName();
				name = name.substring(0,name.length()-4);
				try {
					bI = ImageIO.read(images[i]);
					
					String[] s = name.split("__");
					
					if (s.length == 3){
						int width = Integer.parseInt(s[1]);
						int height = Integer.parseInt(s[2]);
						int x_wid = bI.getWidth() / width;
						int y_wid = bI.getHeight() / height;
						BufferedImage[] bIA = new BufferedImage[width * height];
	
						for (int x = 0; x < width; x++) {
							for (int y = 0; y < height; y++) {
								BufferedImage bI_n = bI.getSubimage(x * x_wid,
										y * y_wid, x_wid, y_wid); 
								
								bIA[y * width + x] = bI_n;

								this.images.put(s[0] + "__" + x + "__" + y, bI_n);
							}
						}
						this.animations.put(s[0], bIA);
					} else {
						this.images.put(name, bI);
					}
				} catch (IOException ex) {
					System.out.println("Could not read in the following file:"
							+ images[i].getName());
				}
			}

		}
		else {
			throw new FileSystemNotFoundException("The directory requested does not exist!");
		}
		return false;
	}

}
