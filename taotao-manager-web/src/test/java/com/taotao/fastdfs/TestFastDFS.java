package com.taotao.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.utils.FastDFSClient;

public class TestFastDFS {
	
	@Test
	public void testUploadFile() throws Exception {
		//添加jar包(添加依赖)
		//创建配置文件,配置tracker服务器地址
		//加载配置文件
		ClientGlobal.init("C:/Users/10309/git/taotaoRepo/taotao-manager-web/src/main/resources/resource/client.conf");
		//创建TrackerClient对象
		TrackerClient trackerClient = new TrackerClient();
		//获取TrackerServer
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建StorageServer引用null就可以
		StorageServer storageServer = null;
		//创建一个StorageClient对象
		StorageClient storageClient =new StorageClient(trackerServer, storageServer);
		//上传文件
		String[] upload_file = storageClient.upload_file("J:/pic/baisewanou.jpg", "jpg", null);
		for (String string : upload_file) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testFastDfsClient() throws Exception {
		FastDFSClient fastDFSClient = new FastDFSClient("C:/Users/10309/git/taotaoRepo/taotao-manager-web/src/main/resources/resource/client.conf");
		String uploadFile = fastDFSClient.uploadFile("J:/pic/huahua.jpg", "jpg", null);
		System.out.println(uploadFile);
	}
}
