package com.generic.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.generic.utils.MapperUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

//    public void main(){
//        Map<String,String> headersToPropertyMap = new HashMap<String,String>();
//        //The header column name in excel-First, the property you wish to assign the value-firstName
//        headersToPropertyMap.put("First", "firstName");
//        headersToPropertyMap.put("Last", "lastName");
//        headersToPropertyMap.put("Email", "email");
//        headersToPropertyMap.put("orgNodeId", "companyname");
//        headersToPropertyMap.put("Company Name", "companynameString");
//        headersToPropertyMap.put("EULA", "eula");
//        headersToPropertyMap.put("Email Notification", "emailNotification");
//        System.out.println("Object: "+ MapperUtil.objectToJson(ExcelUtil.read("path to excel file",Map.class,headersToPropertyMap));
//    }

    public static <T>  List<T> read(String filePath,Class<T> objClass, Map<String,String> headersToPropertyMap){
        try {
            FileInputStream file = new FileInputStream(new File(filePath));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            List<T> retList = new LinkedList<T>();
            Constructor<T> constructor =objClass.getConstructor();
            Map<Integer,String> columnIndexToProperty = null;
            if(rowIterator.hasNext()){
                Row row = rowIterator.next();
                columnIndexToProperty = getCorrespondingColumnIndex(headersToPropertyMap,row);
            }

            while (rowIterator.hasNext())
            {
                T obj = constructor.newInstance();
                Row row = rowIterator.next();
                setObjectFromRow(obj,row,columnIndexToProperty);
                retList.add(obj);
            }
            file.close();
            return retList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new LinkedList<T>();
    }
    private static <T> void setObjectFromRow(T obj, Row row, Map<Integer,String> columnIndexToProperty){
        int numColumns = row.getPhysicalNumberOfCells();
        for(int i=0;i<numColumns;i++){
            Object value = getCellValue(row.getCell(i));
            ReflectUtil.set(obj, columnIndexToProperty.get(i), value);
        }
    }
    private static Map<Integer,String> getCorrespondingColumnIndex(Map<String,String> headersToPropertyMap,Row row){
        int numColumns = row.getPhysicalNumberOfCells();
        Map<Integer,String> columnIndexToProperty = new HashMap<Integer,String>();
        for(int i=0;i<numColumns;i++){
            Cell cell =row.getCell(i);
            String header = cell.getStringCellValue();
            String property = headersToPropertyMap.get(header);
            if(property==null)
                System.out.println("Warning: not able to find property with header: "+header);
            columnIndexToProperty.put(i, property);
        }
        return columnIndexToProperty;
    }

    private static Object getCellValue(Cell cell ){
        switch (cell.getCellType())
        {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
        }
        return null;
    }
}

