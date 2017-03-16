package com.ngw.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.ngw.util.Test;





public class Submit extends HttpServlet {

	private String filePath;    // 文件存放目录  
    private String tempPath;    // 临时文件目录  
 
    // 初始化  
    public void init(ServletConfig config) throws ServletException  
    {  
        super.init(config);  
        // 从配置文件中获得初始化参数  
        filePath = config.getInitParameter("filepath");  
        tempPath = config.getInitParameter("temppath");  
 
        ServletContext context = getServletContext();  
 
        filePath = context.getRealPath(filePath);  
        tempPath = context.getRealPath(tempPath);  
        System.out.println("文件存放目录、临时文件目录准备完毕 ..."+filePath);  
    }  
      
    // doPost  
    public void doPost(HttpServletRequest req, HttpServletResponse res)  
        throws IOException, ServletException  
    {  
        res.setContentType("text/plain;charset=utf-8"); 
        PrintWriter pw = res.getWriter();  
        try{  
            DiskFileItemFactory diskFactory = new DiskFileItemFactory();  
            // threshold 极限、临界值，即硬盘缓存 1M  
            diskFactory.setSizeThreshold(1024 * 1024*50);  
            // repository 贮藏室，即临时文件目录  
            diskFactory.setRepository(new File(tempPath));  
          
            ServletFileUpload upload = new ServletFileUpload(diskFactory);  
            // 设置允许上传的最大文件大小 4M  
            upload.setSizeMax(50 * 1024 * 1024);  
            // 解析HTTP请求消息头  
            List fileItems = upload.parseRequest(req);  
            Iterator iter = fileItems.iterator();  
            String type="";
            String fileName="";
            ArrayList<String> al=new ArrayList<String>();
            
            while(iter.hasNext())  
            {  
                FileItem item = (FileItem)iter.next();  
                if(item.isFormField())  
                {  
                    //System.out.println("处理表单内容 ..."); 
                   //System.out.println(item.getString("UTF-8"));
                    type=item.getString("UTF-8");
                     
                }else{ 
                    //System.out.println("处理上传的文件 ...");  
                    fileName=item.getName(); 
                    //System.out.println(fileName);
                }  
            }
            
            al.add(fileName);
            al.add(type);
            Test.count(al);
            pw.close();
        }catch(Exception e){  
            System.out.println("使用 fileupload 包时发生异常 ...");  
            e.printStackTrace();  
        } 
        
    }  
      
    // doGet  
    public void doGet(HttpServletRequest req, HttpServletResponse res)  
        throws IOException, ServletException  
    {  
        doPost(req, res);  
    }  

}