package com.banking.demo.api.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.banking.demo.utils.FileUtil;

@Controller
public class UploadFileController {
	@Value("${application.config.upload.basedir}")
	private String uploadPath;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String uploadfile(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

		// folder
		String filePathDirectory = new File(uploadPath).isAbsolute() ? uploadPath
				: request.getServletContext().getRealPath(uploadPath);
		// folder save
		File folder = new File(filePathDirectory);
		// create folder
		if (!folder.exists()) {
			folder.mkdirs();
		}
		// upload file
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();
			fileName = new Date().getTime() + FileUtil.getExtension(file.getOriginalFilename());
			File destFile = new File(folder, fileName);
			if (fileName != null && fileName.length() > 0) {
				FileUtil.appendFile(file.getInputStream(), destFile);
			}
		}

		return "upload file done";

	}

	@RequestMapping(path = "/uploadfile", method = RequestMethod.GET)
	public String uploadFile() {
		return "upload";
	}

	@RequestMapping(path = "/uploadMultiFile", method = RequestMethod.GET)
	public String uploadMultiFile() {
		return "uploadMultiFile";
	}

	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	@ResponseBody
	public String uploadFiles(@RequestParam("file") MultipartFile[] files, HttpServletRequest request) throws IOException {
		// folder
		String filePathDirectory = new File(uploadPath).isAbsolute() ? uploadPath
				: request.getServletContext().getRealPath(uploadPath);
		// folder save
		File folder = new File(filePathDirectory);
		// create folder
		if (!folder.exists()) {
			folder.mkdirs();
		}
		// upload file
		if (files.length > 0) {
			for (MultipartFile file : files) {
				String fileName = file.getOriginalFilename();
				fileName = new Date().getTime() + FileUtil.getExtension(file.getOriginalFilename());
				File destFile = new File(folder, fileName);
				if (fileName != null && fileName.length() > 0) {
					FileUtil.appendFile(file.getInputStream(), destFile);
				}

			}
		}

		return "upload file done";
	}

}
