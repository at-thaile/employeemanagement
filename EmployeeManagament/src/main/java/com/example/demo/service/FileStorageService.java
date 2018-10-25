package com.example.demo.service;
import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	@Value("${file.location.store}")
	private String locationStorage;

	@Autowired
	private ServletContext servletContext;

	public boolean storeFile(MultipartFile file) {
		try {
			String realLocation = servletContext.getRealPath(locationStorage);
			File locationStore = new File(realLocation);
			if (!locationStore.exists()) {
				locationStore.mkdirs();
			}
			String fileName = file.getOriginalFilename();
			if (!fileName.contains(".png") && !fileName.contains(".jpg") && !fileName.contains(".gif")
					&& !fileName.contains(".jpeg")) {
				return false;
			}
			File img = new File(realLocation + File.separator + file.getOriginalFilename());
			file.transferTo(img);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
//		InputStream imageFolder = UserProfileApiController.class.getResourceAsStream("static/images/");
//		ClassLoader classLoader = getClass().getClassLoader();
//	    File file = new File(classLoader.getResource("static/images/").getFile(),user.getFile().getOriginalFilename());
//	    System.out.println(file.getAbsolutePath());
//	    user.getFile().transferTo(file);
	}

}
