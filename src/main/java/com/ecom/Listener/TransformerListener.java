package com.ecom.Listener;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.ITestAnnotation;

import com.ecom.Constants.Constants;

public class TransformerListener implements org.testng.IAnnotationTransformer {

	public static Workbook book;
	public static Sheet sheet;

	@Override
	public void transform(ITestAnnotation annotation, @SuppressWarnings("rawtypes") Class testClass,
			@SuppressWarnings("rawtypes") Constructor testConstructor, Method testMethod) {

		System.out.println("In transafor function");
		FileInputStream file = null;
		try {
			file = new FileInputStream(Constants.TEST_DATA_SHEET_PATH);
			book = WorkbookFactory.create(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		book.sheetIterator().forEachRemaining(sheet -> {
			System.out.println("test");
			for (int i = 1; i < sheet.getLastRowNum(); i++) {
				if (testMethod.getName().equals(sheet.getRow(i).getCell(1).toString())) {
					if (sheet.getRow(i).getCell(3).toString().equals("Yes")) {
						annotation.setEnabled(true);
						System.out.println("enabling test case : " + sheet.getRow(i).getCell(1).toString());
					} else {
						annotation.setEnabled(false);
						System.out.println("Disabling test case : " + sheet.getRow(i).getCell(1).toString());
					}
				}
			}
		});
	}
}
