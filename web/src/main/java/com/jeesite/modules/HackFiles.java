package com.jeesite.modules;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.hyperic.sigar.ProcExe;
import org.hyperic.sigar.ThreadCpu;
import org.hyperic.sigar.win32.EventLogRecord;

import com.jeesite.modules.job.service.JobService; 

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.util.HotSwapper;


/**
 * 4.1.8
 * 2020-05-12
 * */
public class HackFiles {
	
	public static void main(String[] args) throws Exception {
	 
 
		String path = HackFiles.class.getProtectionDomain().getCodeSource().getLocation().getFile(); 
		path = URLDecoder.decode(path,"UTF-8");
		System.out.println(path);
		args=new String[]{path.substring(1)};
		System.out.println(args[0]);
		 
		
		String jarWholePath = JobService.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		try{
			jarWholePath = java.net.URLDecoder.decode(jarWholePath, "UTF-8");
		} catch (Exception e) { System.out.println(e.toString()); }

		List<String> fileList = ZipHp.getHackFiles(jarWholePath);
	System.out.println("we got " + fileList.size() + " files");	 
		clsList = new String[fileList.size()];

		clsList = fileList.toArray(clsList);
		
		  ClassPool pool = new ClassPool();
 
		  for (String cName : clsList) {
			  pool.insertClassPath(new ClassClassPath(Class.forName(cName)));
			  CtClass cls = pool.get(cName); 
			  String str = new String(cls.toBytecode()); 
			try {	  
				if(cls.isFrozen()){
					cls.defrost();
	            }
				
				  cls.addMethod(CtMethod.make("public Object get(Object key){return com.jeesite.modules.Application.get(key,super.get(key));}", cls));
			}catch(Exception err) {
				System.out.println("err "+err.getMessage());
			}
		 
			cls.writeFile(args[0]); 
			
			System.out.println("write hacked file succ!");
		  }
		  
	}
	
	public static String []clsList = {  
	};

	static class ZipHp{
		private static boolean readInputStream( final InputStream inputStream ) throws IOException {
			/*
			 * final byte[] buf = new byte[ 8192 ]; int read = 0; int cntRead; while ( (
			 * cntRead = is.read( buf, 0, buf.length ) ) >=0 ) { read += cntRead; } return
			 * read;
			 */
			String str = new BufferedReader(new InputStreamReader(inputStream))
					.lines().parallel().collect(Collectors.joining(System.lineSeparator()));
			
			 if(str.contains("java/util/Map$Entry") && 
					  str.contains("java/util/HashMap<Ljava/lang/String;Ljava/lang/String;>")&&
						str.contains("putAll") /*
												 * && str.contains("EventLogRecord")
												 */
				) {
				 return true;
			 }
			 
			 return false;
		} 
		
		private static List<String> getHackFiles(String jarFile) throws IOException {
			List<String> ret = new ArrayList<String>();
			InputStream is=null;
			 
			/*
			 * ZipInputStream zip = new ZipInputStream(is); zip.getNextEntry()
			 */
			//ZipOutputStream zip = new ZipOutputStream(is);
			final ZipFile file = new ZipFile( jarFile );
			try
			{
			    final Enumeration<? extends ZipEntry> entries = file.entries();
			    while ( entries.hasMoreElements() )
			    {
			        final ZipEntry entry = entries.nextElement();
			        
			        //use entry input stream:
			       if( ZipHp.readInputStream( file.getInputStream( entry ) )) {
			    	   ret.add(entry.getName().replaceAll("/", ".").replace(".class", ""));
			    	   System.out.println( entry.getName() );
			       }
			    }
			}
			finally
			{
			    file.close();
			} 
			return ret;
		}
		
		
	}

}
