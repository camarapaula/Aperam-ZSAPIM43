package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectI;
import com.syclo.sap.User;
import com.syclo.sap.jco.JCO.Table;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_InboundDeliveryDocumentsPostBAPI,Z_PurchaseOrderPostBAPI and stored into this POJO class via
 * getter and setter methods.
 * */

public class Z_DocumentLink extends SAPObject implements SAPObjectI{

	public String ID = "";
	public String FileName= "";
	public String FileType = "";
	public String FileSize = "";
	public String FileData = "";
	public String Picture = "";
	
	/*
	* Default Constructor definition for the class
	* */
		
	public Z_DocumentLink() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public Z_DocumentLink(User user) {
		super(user);
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setNotes(Table arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProperties(Table arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Method to set the custom values DocumentLink from the Z_InboundDeliveryDocumentsPostBAPI,Z_PurchaseOrderPostBAPI to the respective variables in java
	 * */
	
	public void setProperties(User u, String parentPrefix, int k) throws Exception {	
	String prefix = parentPrefix + "Z_DocumentLinks." + k +  ".";
	setID(u.getString(prefix + "ID"));
	setFileName(u.getString(prefix + "FileName"));
	setFileType(u.getString(prefix + "FileType"));
	setFileSize(u.getString(prefix + "FileSize"));
	setFileData(u.getString(prefix + "FileData"));
	int docCount = Integer.parseInt(this._user.eval("<<size " + "transaction.Z_TempDocObject.Z_DocumentAttachments" + ">>"));
		for (int j = 0; j < docCount; j++) {
			if(getID().equals(u.getString("transaction.Z_TempDocObject.Z_DocumentAttachments." + j + "." + "ID"))){
			setPicture(u.getString("transaction.Z_TempDocObject.Z_DocumentAttachments." + j + "." + "Picture.data"));
			break;
			}
		}
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getFileType() {
		return FileType;
	}

	public void setFileType(String fileType) {
		FileType = fileType;
	}

	public String getFileSize() {
		return FileSize;
	}

	public void setFileSize(String fileSize) {
		FileSize = fileSize;
	}

	public String getFileData() {
		return FileData;
	}

	public void setFileData(String fileData) {
		FileData = fileData;
	}

	public String getPicture() {
		return Picture;
	}

	public void setPicture(String picture) {
		Picture = picture;
	}

	public void setID(String iD) {
		ID = iD;
	}
}
