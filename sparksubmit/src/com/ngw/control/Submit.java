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

	private String filePath;    // �ļ����Ŀ¼  
    private String tempPath;    // ��ʱ�ļ�Ŀ¼  
 
    // ��ʼ��  
    public void init(ServletConfig config) throws ServletException  
    {  
        super.init(config);  
        // �������ļ��л�ó�ʼ������  
        filePath = config.getInitParameter("filepath");  
        tempPath = config.getInitParameter("temppath");  
 
        ServletContext context = getServletContext();  
 
        filePath = context.getRealPath(filePath);  
        tempPath = context.getRealPath(tempPath);  
        System.out.println("�ļ����Ŀ¼����ʱ�ļ�Ŀ¼׼����� ..."+filePath);  
    }  
      
    // doPost  
    public void doPost(HttpServletRequest req, HttpServletResponse res)  
        throws IOException, ServletException  
    {  
        res.setContentType("text/plain;charset=utf-8"); 
        PrintWriter pw = res.getWriter();  
        try{  
            DiskFileItemFactory diskFactory = new DiskFileItemFactory();  
            // threshold ���ޡ��ٽ�ֵ����Ӳ�̻��� 1M  
            diskFactory.setSizeThreshold(1024 * 1024*50);  
            // repository �����ң�����ʱ�ļ�Ŀ¼  
            diskFactory.setRepository(new File(tempPath));  
          
            ServletFileUpload upload = new ServletFileUpload(diskFactory);  
            // ���������ϴ�������ļ���С 4M  
            upload.setSizeMax(50 * 1024 * 1024);  
            // ����HTTP������Ϣͷ  
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
                    //System.out.println("��������� ..."); 
                   //System.out.println(item.getString("UTF-8"));
                    type=item.getString("UTF-8");
                     
                }else{ 
                    //System.out.println("�����ϴ����ļ� ...");  
                    fileName=item.getName(); 
                    //System.out.println(fileName);
                }  
            }
            
            al.add(fileName);
            al.add(type);
            Test.count(al);
            pw.close();
        }catch(Exception e){  
            System.out.println("ʹ�� fileupload ��ʱ�����쳣 ...");  
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