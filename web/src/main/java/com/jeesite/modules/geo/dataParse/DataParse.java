package com.jeesite.modules.geo.dataParse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.junit.Test;

import com.alibaba.druid.util.Utils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.codec.Md5Utils;
import com.jeesite.common.config.Global;
import com.jeesite.common.idgen.IdGenerate;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.geo.entity.McGeofolder;
import com.jeesite.modules.geo.service.McGeofolderService;
import com.jeesite.modules.mc.entity.McTextfiledata;
import com.jeesite.modules.sys.entity.Area;
import com.jeesite.modules.sys.service.AreaService;

public class DataParse {
	
	static SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
	static SimpleDateFormat mm = new SimpleDateFormat("MM");
	static SimpleDateFormat day = new SimpleDateFormat("dd");
	static SimpleDateFormat now = new SimpleDateFormat("yyyyMMdd");
	static String areaInfo = null;
	static List<Area> area; 
	
	
	public static String getAreaCodeByName(String name) {
 
		if(null == area) {
			AreaService areaService = SpringUtils.getBean("areaService");
			area = areaService.findList(new Area());
		}
		
		
		//JSONArray jsa = JSONObject.parseArray(areaInfo);
		for(int i=0;i<area.size();i++) {
			if(name.replaceAll("镇", "乡").endsWith(area.get(i).getAreaName().replaceAll("镇", "乡"))) {
				return area.get(i).getAreaCode();
			}else if(name.replaceAll("回族", "").replaceAll("镇", "乡").endsWith(area.get(i).getAreaName().replaceAll("镇", "乡"))) {
				return area.get(i).getAreaCode();
			}
		}
		return "";//name;
	}

	public static void main(String[] args) throws Exception {
		 
		parseExcel("C:\\Users\\lijie\\Documents\\WeChat Files\\tanlijie695856669\\FileStorage\\File\\2020-09\\2019(1).xls",new McGeofolder());
	
	}
	
	/***
	 * 根据规则，获取所有符合条件的目录
	 * */
	public static List<String> getPathList(McGeofolder folder){
		List<String> ret = new ArrayList();
		
		Date dt = Calendar.getInstance().getTime();
		String path = getBasePath() + folder.getTreeNames();
		if(path.indexOf("{") > 0) {
			path = path.substring(0,path.indexOf("{") - 1);
		}else {
			
		}
		
		if(File.separatorChar == '\\') {//if win
			path = path.replaceAll("/", "\\\\"); 
		 	
			path = path.replaceAll("\\\\\\\\" ,"\\\\" );
			
			//absPath = absPath.replaceAll("\\\\","\\\\\\\\");
			
		}else {//if linux
			path = path.replaceAll("\\\\","/");
		}
		System.out.println(path);
		func(new File(path),ret,true);
		
		String absPath = getBasePath() + folder.getTreeNames().replaceAll("/", "\\\\") +"."+ folder.getFileType()  ;
		if(File.separatorChar == '\\') {//if win
		 	absPath = absPath.replaceAll("/", "\\\\"); 
		 	
			absPath = absPath.replaceAll("\\\\\\\\" ,"\\\\" );
			
			//absPath = absPath.replaceAll("\\\\","\\\\\\\\");
			
		}else {//if linux
			absPath = absPath.replaceAll("\\\\","/");
		}
		
		if(folder.getTreeNames().contains("{yyyy}")){
			String reg ="((19|20)[0-9]{2})";
			absPath = replaceAll("{yyyy}", reg,absPath);
		}
		
		if(folder.getTreeNames().contains("{MM}")) {
			String reg = "(0+[1-9]|1[0-2])";
			absPath = replaceAll("{MM}", reg,absPath);
		} 
		for(int i=0;i<ret.size();i++) {

			String []a = getDiff(absPath, ret.get(i));
			if(a[0].contains(")\\(")) {
				a[0] = a[0].replace(")\\(", ")\\\\(");
			}
			try {
				if(!Pattern.matches(a[0],a[1])) {
					ret.remove(i);
					i--;
				}
			}catch(Exception err) {
				err.printStackTrace();
			}
		}
		
		return ret;  
	}
	private static String[] getDiff(String arg1,String arg2) {
		String []ret = new String[]{"",""};
		
		for(int i=0;i<arg1.toCharArray().length;i++) {
			if(arg1.charAt(i) == arg2.charAt(i)) {
				
			}else {
				ret[0] = arg1.substring(i);
				ret[1] = arg2.substring(i);
				break;
			}
		}
		
		
		return ret;
	}
	private static String replaceAll(String target,String reg,String con) {
		while(con.contains(target)) {
			con = con.replace(target, reg);
		}
		return con;
	}
	
	private static void func(File file,List<String> list ,boolean isFolder){
//	file = new File("Y:\\software\\temp\\蒙草数据目录\\Excel数据\\工程修复\\退牧还草\\盖度");
		//file = new File("Y:\\software\\temp\\蒙草数据目录\\Excel数据\\林地\\林地面积");
		File[] fs = file.listFiles();
		if(fs == null) {
			System.out.println();
			try {
				Thread.sleep(1000);
			}catch(Exception err) {
				
			}
			file = new File(file.getAbsolutePath());
			fs = file.listFiles();
		}
		for(File f:fs){
			if(f.isDirectory())
				func(f,list,isFolder);
			else// if(f.isFile())
				list.add(f.getPath());
				//System.out.println(f);
		}
	} 
	
	
	
	
	public static String getBasePath() {
		return Global.getConfig("mc.geo.folder") +File.separatorChar;
	}
	
	public static String pathBuilder(String path,Date dt) {
		path = getBasePath() + path;

		return path
				.replaceAll("\\{yyyy\\}",yyyy.format(dt))
				.replaceAll("\\{MM\\}", mm.format(dt))		
				.replaceAll("\\{day\\}", day.format(dt))	
				.replaceAll("\\{now\\}", now.format(dt));
	}
	
	public static String pathBuilder(String path) {
		Date dt = Calendar.getInstance().getTime();
		return pathBuilder(path,dt);
	}
		
	public static List<McTextfiledata> parseExcel(String file,McGeofolder folder) throws Exception {
 		File excel = new File(file);
		
		String md5 = Md5Utils.md5File(excel);
		
        String[] split = excel.getName().split("\\.");   
        Workbook wb;
    
        if ( "xls".equals(split[1]) || "csv".equals(split[1])){
            FileInputStream fiStream = new FileInputStream(excel);   //文件流对象
            wb = new HSSFWorkbook(fiStream);
        }else{
            wb = new XSSFWorkbook(new FileInputStream(excel));
        }

      //  EncodeUtils.encodeXml(xml)
        //EncodeUtils.encodeUrl(part)
        //开始解析
        Sheet sheet = wb.getSheetAt(0);     //读取sheet 0
        List<ArrayList<String>> lines = new ArrayList<ArrayList<String>>();
        int last = sheet.getLastRowNum();
        
        for(int i = sheet.getFirstRowNum();i<=last;i++) {
        	ArrayList<String> line = new ArrayList();
        	Row row = sheet.getRow(i);
        	int maxCellNum = row.getLastCellNum();
        	if(lines.size()>0) {
        		maxCellNum = lines.get(0).size();
        	}
        	int  num = 0;
        	while (num < row.getFirstCellNum()) {
        		line.add("");
        		num++;
        	}
        	
        	
        	//如果只有1列，跳过
        	if(maxCellNum - maxCellNum == 1) {
        		continue;
        	}
        	
        	for(int j=row.getFirstCellNum();j<maxCellNum ;j++) {
        		try {
					/*
				row.getCell(j).setCellType(HSSFCell.CELL_TYPE_STRING);
					 */
        			if(row.getCell(j) == null) {
        				line.add(null);
        			}else {
	         			String str = getCellValue(row.getCell(j));
						line.add(str/* row.getCell(j).getStringCellValue().trim() */);
        			}
        		}catch(Exception err) {
        		//	err.printStackTrace();
        			line.add(null);
        		}
        	}
        	lines.add(line);
        }
        
       List<ArrayList<String>> dest  = new ArrayList<ArrayList<String>>();
       lines.forEach(new Consumer<List<String>>() {
			@Override
			public void accept(List<String> t) {
				ArrayList list = new ArrayList<String>();
				list.addAll(t);
				dest.add(list);
			} 
       });
       
		dest.forEach(new Consumer<List<String>>() {
			@Override
			public void accept(List<String> t) {
				while(t.remove("")) {}
			}
		});
       
       //如果是标题，移除掉
		if(dest.get(0).size() == 1) {
			lines.remove(0);
		}
		
		   
        List<McTextfiledata> list = new ArrayList();
        List<String> titleCols = lines.get(0);
        for(int i=1;i<lines.size();i++) {
        	List<String> line = lines.get(i);
        	for(int j=2;j<titleCols.size();j++) {
        		McTextfiledata mtf = new McTextfiledata();
    //    		mtf.setId(IdGenerate.nextId());
        		if(!StringUtils.isEmpty(line.get(1).trim())) {
        			mtf.setRowtime(DateUtils.parseDate(line.get(1)));	
        		}else {
        			String fName = excel.getName();
        			fName = fName.substring(0,fName.indexOf("."));
        			if(fName.length() == 4) {
        				mtf.setRowtime(DateUtils.parseDate(fName));	
        			}
        		}

        		
        		mtf.setAreacode(getAreaCodeByName(titleCols.get(j)));
        		mtf.setAreaname(titleCols.get(j));
        		mtf.setRowtitle(line.get(0));
        		try {
        			mtf.setValue(line.get(j));
        		}catch(Exception err) {
        			err.printStackTrace();
        		}
            	mtf.setRuleid(folder.getId());
            	mtf.setFilemd5(md5);
            	mtf.setFilepath(file);
            	mtf.setDatetype(folder.getFileType());
            
            	list.add(mtf);
        	} 
        }
       
       
       return list;
	}
	
	
	public static String getCellValue(Cell cell) {
        String cellValue = "";
        // 以下是判断数据的类型
  //      System.out.println(cell);
        if(null == cell ) {
        	System.out.println();
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                } else {
                    DataFormatter dataFormatter = new DataFormatter();
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = cell.getBooleanCellValue() + "";
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = cell.getCellFormula() + "";
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }
	
	
	public static void insertArea(AreaService areaService) throws IOException {
	
		areaService.delete(new Area());
		
		//IOUtils.re(new DataParse().getClass().getResourceAsStream("region.json"));
		String str = Utils.readFromResource("region.json");
		JSONArray jsonArray = JSONObject.parseObject(str).getJSONArray("features");
		
		for(int i=0;i<jsonArray.size();i++) {
			String code = jsonArray.getJSONObject(i).getJSONObject("properties").getString("行政区");
		
			for(int j=i+1;j<jsonArray.size();j++) {
				String code2 =  jsonArray.getJSONObject(j).getJSONObject("properties").getString("行政区");
				if( code2.equals(code)) {
					
					jsonArray.remove(j);
					j--;
					i--;
					break;
					
				}
				
			}
			
		}
		
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			String code = obj.getJSONObject("properties").getString("行政区");
			if(code.length() < 10){
				for(int j=0;j<jsonArray.size();j++) {
					JSONObject obj2 = jsonArray.getJSONObject(j);
					String code2 = obj2.getJSONObject("properties").getString("行政区");
					if(code2.startsWith(code) && !code2.equals(code)) {
						JSONObject obj3 = obj.getJSONObject("properties");
						ArrayList child = null; 
						if(obj3.containsKey("child")) {
							child = (ArrayList)obj3.get("child");
						}else {
							child = new ArrayList();
							obj3.put("child", child);
						}
						child.add(obj2);
						jsonArray.remove(j);
						j--;
					//	i--;
					}
				}	
			}else {
			}
		}
		
		
		
		Area b = new Area();
		b.setAreaCode("620000");
		b.setAreaType("1");
		b.setAreaName("甘肃省");
		b.setIsNewRecord(true);
		areaService.save(b);
		
		Area a = new Area();
		a.setParent(b);
		a.setAreaCode("623000");
		a.setAreaType("2");				
		a.setAreaName("甘南州");
		a.setIsNewRecord(true);
		areaService.save(a);
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			Area area = new Area();
			area.setIsNewRecord(true); 
			area.setParentCode(a.getId());
			area.setAreaName(obj.getJSONObject("properties").getString("乡镇名"));
			area.setAreaCode(obj.getJSONObject("properties").getString("行政区"));
			area.setAreaType("3");
			areaService.save(area);
			
			JSONArray  childs = obj.getJSONObject("properties").getJSONArray("child");
			for(int j=0;j<childs.size();j++) {
				JSONObject item = childs.getJSONObject(j);
				Area area2 = new Area();
				area2.setIsNewRecord(true);
				area2.setParent(area);
				area2.setAreaType("4");
				area2.setAreaName(item.getJSONObject("properties").getString("乡镇名"));
				area2.setAreaCode(item.getJSONObject("properties").getString("行政区"));
				areaService.save(area2);
				/*
				 * JSONArray childs1 = item.getJSONArray("childs"); for(int
				 * k=0;k<childs1.size();k++) { item = childs1.getJSONObject(k); Area area3 = new
				 * Area(); area3.setParent(area2); area3.setAreaType("3");
				 * area3.setAreaName(item.getString("name")); areaService.save(area3); }
				 */
			}
			
			//
			
		}
		
		
	}
}
