package com.jeesite.modules.utils;


import java.io.*;
import org.apache.tools.zip.*;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;
 
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
 
public class AntZip{
	 
	 public static List<String> searchFiles(File folder,  String keyword) {
		 
         List<String> result = new ArrayList<String>();
		/*
		 * if (folder.isFile()) result.add(folder.getPath());
		 */
  
         File[] subFolders = folder.listFiles(new FileFilter() {
             @Override
             public boolean accept(File file) {
                 if (file.isDirectory()) {
                     return true;
                 }
                 if (file.getName().toLowerCase().contains(keyword.toLowerCase())) {
                     return true;
                 }
                 return false;
             }
         }); 
         if (subFolders != null) {
             for (File file : subFolders) {
                 if (file.isFile()) {
                     // 如果是文件则将文件添加到结果列表中
                     result.add(file.getPath());
                 } else {
                     // 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                     result.addAll(searchFiles(file, keyword));
                 }
             }
         }
         return result;
     }
	 
	private static final Logger logger=Logger.getLogger(AntZip.class);
    /**
     * 将文件压缩为zip格式
     * @param sourcePath 文件路径
     * @param zipPath 压缩包保存路径
     * @param fileName 文件名称不带后缀
     */
   public static boolean createZipFile(String sourcePath,String zipPath,String fileName){
       String seperator=File.separator;
       logger.info("文件路径格式:"+seperator);
       File sourcefile=new File(sourcePath);
       File zipFile=new File(zipPath+seperator+fileName+".zip");
       if (!sourcefile.exists()){
           logger.info("文件不存在："+sourcefile.getName());
           return false;
       }
       ZipOutputStream zos=null;
       try {
           zos=new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
           //设置压缩包注释
//           zos.setComment(comment);
           //启用压缩
           zos.setMethod(ZipOutputStream.DEFLATED);
           //压缩级别为最强压缩，但时间要花得多一点
           zos.setLevel(Deflater.BEST_COMPRESSION);
           //设置压缩编码
           zos.setEncoding("GBK");
           //判断路径是否为目录
           if (sourcefile.isFile()){//如果路径是文件，直接压缩
               String zname=fileName+seperator+sourcefile.getName();
               return fileToZip(sourcefile,zname,zos);
           }else if(sourcefile.isDirectory()){//如果路径是文件夹，遍历并压缩
               String zname=fileName+seperator+sourcefile.getName()+seperator;
               logger.info("添加文件夹："+zname);
               zos.putNextEntry(new ZipEntry(zname));
               return isFileOrDirectory(sourcefile,fileName,zos);
           }
           zos.closeEntry();
       } catch (FileNotFoundException e) {
           logger.error("文件打包失败",e);
           e.printStackTrace();
       } catch (IOException e) {
           logger.error("zos.closeEntry()",e);
           e.printStackTrace();
       }finally {
           if (zos!=null){
               try {
                   zos.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
       return true;
   }
 
    /**
     * 递归目录
     * @param sourcefile
     * @param fileName
     * @param zos
     * @return
     */
   private static boolean isFileOrDirectory(File sourcefile,String fileName,ZipOutputStream zos) throws IOException {
       String seperator=File.separator;
       File[] files=sourcefile.listFiles();
       if (files!=null){
           for (File file:files){
               if (file.isFile()){
                   String zname=fileName+seperator+file.getName();
                   boolean flag= fileToZip(file,zname,zos);
                   if (!flag){
                       return false;
                   }
               }else if (file.isDirectory()){
                   String zname=fileName+seperator+file.getName()+seperator;
                   logger.info("压缩文件夹："+zname);
                   zos.putNextEntry(new ZipEntry(zname));
                   zos.closeEntry();
                   boolean flag=isFileOrDirectory(file,fileName+seperator+file.getName(),zos);
                   if (!flag){
                       return false;
                   }
               }
           }
       }
       return true;
   }
 
 
    /**
     * 单个文件压缩
     * @param sourcefile 源文件路径
     * @param name 文件名
     * @param zos ZipOutputStream
     * @return
     */
   public static boolean fileToZip(File sourcefile,String name,ZipOutputStream zos){
       if (!sourcefile.exists()){
           logger.error("被压缩文件不存在:"+sourcefile.getName());
           return false;
       }
       String source=sourcefile.getPath().replace("\\","/");
       File file=new File(source);
       BufferedInputStream bis=null;
       try {
           bis=new BufferedInputStream(new FileInputStream(file));
           zos.putNextEntry(new ZipEntry(name));
           byte[] bys=new byte[1024];
           int len=-1;
           while ((len=bis.read(bys))!=-1){
               zos.write(bys,0,len);
               zos.flush();
           }
           zos.closeEntry();
           bis.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return true;
   }
 
 
    /**
     * 解压文件夹
     * @param srcFile
     * @param destDirPath
     * @throws RuntimeException
     */
    public static boolean unZip(File srcFile, String destDirPath) throws RuntimeException {
        String seperator=File.separator;
        logger.info(seperator);
        long start = System.currentTimeMillis();
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            logger.error(srcFile.getPath() + "所指文件不存在");
            return false;
        }else {
            // 开始解压
            ZipFile zipFile = null;
            try {
                zipFile = new ZipFile(srcFile,"GBK");
                Enumeration entries = zipFile.getEntries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    String fileName=entry.getName().replace("\\","/");
                    // 如果是文件夹，就创建个文件夹
                    if (fileName.endsWith("/")) {
                        String dirPath = destDirPath + seperator + fileName;
                        logger.info("正在创建解压目录 - " + dirPath);
                        File decompressDirFile = new File(dirPath);
                        if (!decompressDirFile.exists()) {
                            decompressDirFile.mkdirs();
                        }
                    } else{
                        // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                        File targetFile = new File(destDirPath + seperator + fileName);
                        // 保证这个文件的父文件夹必须要存在
                        if(!new File(targetFile.getParent()).exists()){
                            new File(targetFile.getParent()).mkdirs();
                            logger.info("新建文件夹目录："+targetFile.getParentFile().getPath());
                        }
                        targetFile.createNewFile();
                        // 将压缩文件内容写入到这个文件中
                        InputStream is = zipFile.getInputStream(entry);
                        FileOutputStream fos = new FileOutputStream(targetFile);
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                        // 关流顺序，先打开的后关闭
                        fos.close();
                        is.close();
                    }
                }
                long end = System.currentTimeMillis();
                logger.info("解压完成，耗时：" + (end - start) +" ms");
            } catch (Exception e) {
                logger.error("文件解压失败!",e);
                return false;
            } finally {
                if(zipFile != null){
                    try {
                        zipFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
    }
 
    /**
     * 递归删除目录
     * @param sourceFile
     * @return
     */
    public static boolean deleteAllFile(File sourceFile){
        if (!sourceFile.exists()){
            return false;
        }
        File[] files=sourceFile.listFiles();
        if (files!=null){
            for (int i=0;i<files.length;i++){
                File file=files[i];
                if (file.isFile()){
                    file.delete();
                }else if (file.isDirectory()){
                    deleteAllFile(file);
                }
                if (i==(files.length-1)){
                    sourceFile.delete();
                }
            }
        }else {
            sourceFile.delete();
        }
        return true;
    } 
}
