package org.beetl.ext.fn;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.druid.util.Utils;

public class CnText implements Function{
	static Map<String, HashMap<String, String>> map = null;
	public Object call(Object[] paras, Context ctx)	{
		String type = (String)paras[0];
		if(paras.length == 1)return paras[0];
		String key = (String)paras[1];
	    //return ctx.globalVar.get(key);
		
		//InputStream is= this.getClass().getResourceAsStream("dict.xlsx");
		

		try {
			if(map == null)map = parseExcel();
			
			if(map.containsKey(type) && map.get(type).containsKey(key)) {
				key = map.get(type).get(key);
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.context = request.getSession().getServletContext();
		
	    return key;
	}
	
	
	private Map<String, HashMap<String, String>> parseExcel() throws IOException{
		Map<String, HashMap<String, String>> ret = new HashMap<String,HashMap<String,String>>();

		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dict.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(is);
		for(int i=0;i<workbook.getNumberOfSheets();i++) {
			XSSFSheet st = workbook.getSheetAt(i);
			HashMap<String,String> map = new HashMap();
			ret.put(st.getSheetName(),map);
			
			for(int j=0;j<=st.getLastRowNum();j++) {
				map.put(st.getRow(j).getCell(0).getStringCellValue(), st.getRow(j).getCell(1).getStringCellValue());
			}
		}
		workbook.close();
		
		//request.getres
		//String str = Utils.readFromResource("region.json");

		//ctx.
	//	  InputStream is = ctx.getServletContext().getResourceAsStream("/WEB-INF/log4j.properties");
		
		return ret;
	}
}
