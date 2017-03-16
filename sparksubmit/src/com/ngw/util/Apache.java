package com.ngw.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.spark.SparkConf;
import org.apache.spark.deploy.yarn.Client;
import org.apache.spark.deploy.yarn.ClientArguments;
public class Apache {

	public static void save2Hdfs(String fileName){
		String local=fileName;
		System.out.println(local.substring(local.lastIndexOf("\\")+1,local.length()));
		String hdfs="hdfs://192.168.157.134:8020/tmp/sparkinput/file/"+local.substring(local.lastIndexOf("\\")+1,local.length());
		  
		try{
			//in��Ӧ���Ǳ����ļ�ϵͳ��Ŀ¼
			InputStream in = new BufferedInputStream(new FileInputStream(local));
			Configuration conf = new Configuration();
			//���hadoopϵͳ������ 
			conf.set("fs.hdfs.impl",org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());
			FileSystem fs = FileSystem.get(URI.create(hdfs),conf);
			//out��Ӧ����Hadoop�ļ�ϵͳ�е�Ŀ¼
			OutputStream out = fs.create(new Path(hdfs));
			IOUtils.copyBytes(in, out, 4096,true);//4096��4k�ֽ�
			
		} catch (Exception e) {
			 e.printStackTrace();
		 
		}
	}
	public static void submitSparkDemo(){
		String[] arg0=new String[]{
				"--class","SparkApp",
				"--executor-memory","1G",
				"--jar","hdfs://192.168.157.134:8020/tmp/sparkinput/jar/SparkSubmit1.jar",
				"--arg","hdfs://192.168.157.134:8020/tmp/sparkinput/file/person.txt",
				"--arg","hdfs://192.168.157.134:8020/tmp/sparkinput/result"
		};
		Configuration conf = new Configuration();
		String os = System.getProperty("os.name");
		boolean cross_platform =false;
		if(os.contains("Windows")){
			cross_platform = true;
		}
		conf.setBoolean("mapreduce.app-submission.cross-platform", cross_platform);// ����ʹ�ÿ�ƽ̨�ύ����
		conf.set("fs.defaultFS", "hdfs://192.168.157.134:8020");// ָ��namenode
		conf.set("mapreduce.framework.name","yarn"); // ָ��ʹ��yarn���
		conf.set("yarn.resourcemanager.address","192.168.157.134:8032");
		conf.set("yarn.resourcemanager.scheduler.address", "192.168.157.134:8030");// ָ����Դ������
		conf.set("mapreduce.jobhistory.address","192.168.157.134:10020");
		 System.setProperty("SPARK_YARN_MODE", "true");
		 SparkConf sparkConf = new SparkConf();
		 ClientArguments cArgs = new ClientArguments(arg0, sparkConf);
		
		new Client(cArgs,conf,sparkConf).run();
	}
}
