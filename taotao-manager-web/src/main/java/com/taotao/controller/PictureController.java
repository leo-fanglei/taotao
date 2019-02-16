package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common_utils.JsonUtils;
import com.taotao.utils.FastDFSClient;

/**
 * 图片上传controller
 * @author 10309
 *
 */
@Controller
public class PictureController {
	
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String picUpload(MultipartFile uploadFile) {
		try {
			//接受上传的文件
			//取扩展名(不带.)
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.indexOf(".")+1);
			//上传至图片服务器
			FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/client.conf");
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
			url = IMAGE_SERVER_URL + url;
			//内容响应url
			Map resultMap = new HashMap<>();
			resultMap.put("error", 0);
			resultMap.put("url",url);
			return JsonUtils.objectToJson(resultMap);
		} catch (Exception e) {
			e.printStackTrace();
			Map resultMap = new HashMap<>();
			resultMap.put("error", 1);
			resultMap.put("message","图片上传失败");
			return JsonUtils.objectToJson(resultMap);
		}
	}
}
