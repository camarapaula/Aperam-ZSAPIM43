package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.ConversionUtility;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_AdhocMaterialCountPostBAPI;
import com.syclo.sap.jco.JCO;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_MaterialAdhocCountFetchBAPI,Z_AdhocMaterialCountPostBAPI and stored into this POJO class via
 * getter and setter methods.
 * */

public class Material extends com.syclo.sap.component.goodsmovement.object.Material{
	
	public String documentDate="";
	public String Z_LastCount="";
	public String Z_Vendor="";
	public String Z_StockType="";
	public String Z_CountedDate = "";
	public String MaterialDesc = "";
	public String StorageBin = "";
	public String Z_STLocSBin= "";
	
	/*
	* Default Constructor definition for the class
	* */

	public Material() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public Material(User user) {
		super(user);
	}
	
	/*method for calling the custom set properties method from Z_MaterialAdhocCountFetchBAPI*/
	
	public void init(User user, JCO.Table tbl ) throws Exception {
		setCustomProperties(tbl);
	}
	
	/*method to set the standard and custom properties for Material*/
	
	public void setCustomProperties(JCO.Table tbl) throws Exception {
		super.setProperties(tbl);
		String docID = tbl.getString("IBLNR");

		setPhysInventoryDocNum(docID);
		setMaterial(tbl.getString("MATNR"));
		setMaterialDesc(tbl.getString("MAKTX"));
		if (ConversionUtility.isNumber(getMaterial())) {
			setMaterial(ConversionUtility.removeLeadingZeros(getMaterial()));
		}
		//setItem(tbl.getString("ZEILI"));
		setQuantity(tbl.getString("MENGE"));
		setUOM(tbl.getString("MEINS"));
		setPlant(tbl.getString("WERKS"));
		setStorageLocation(tbl.getString("LGORT"));
		setBatch(tbl.getString("CHARG"));
		setID(docID + 1);
		
		//Custom properties for Adhoc counting
		setDocumentDate(tbl.getString("ZLDAT"));
		//setZ_LastCount(tbl.getString("XXXXX"));
		setZ_Vendor(tbl.getString("LIFNR"));
		//setZ_StockType(tbl.getString("XXXXX"));
	}
	
	/*
	 * Method to set the custom values Material from the Z_AdhocMaterialCountPostBAPI to the respective variables in java
	 * */
	
	public void setProperties(User u, Z_AdhocMaterialCountPostBAPI bapi) throws Exception {
		
		String prefix = "transaction.";
		setMaterial(this._user.getString(prefix + "material"));
		setBatch(this._user.getString(prefix + "batch"));
		setUOM(this._user.getString(prefix + "Z_NewUOM"));;
		setPlant(this._user.getString(prefix + "plant"));;
		setStorageLocation(this._user.getString(prefix + "storageLocation"));;
		setCounted(this._user.getString(prefix + "CountedQuantity"));;
		setZeroCount(this._user.getString(prefix + "zeroCount"));;
		setZ_CountedDate(this._user.eval("<<" + prefix + "Z_CountedDate format=\"%Y%m%d\">>"));
		
	}

	public String getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}

	public String getZ_LastCount() {
		return Z_LastCount;
	}

	public void setZ_LastCount(String z_LastCount) {
		Z_LastCount = z_LastCount;
	}

	public String getZ_Vendor() {
		return Z_Vendor;
	}

	public void setZ_Vendor(String z_Vendor) {
		Z_Vendor = z_Vendor;
	}

	public String getZ_StockType() {
		return Z_StockType;
	}

	public void setZ_StockType(String z_StockType) {
		Z_StockType = z_StockType;
	}

	public String getMaterialDesc() {
		return MaterialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		MaterialDesc = materialDesc;
	}

	public String getZ_CountedDate() {
		return Z_CountedDate;
	}

	public void setZ_CountedDate(String z_CountedDate) {
		Z_CountedDate = z_CountedDate;
	}

	public String getStorageBin() {
		return StorageBin;
	}

	public void setStorageBin(String storageBin) {
		StorageBin = storageBin;
	}

	public String getZ_STLocSBin() {
		return Z_STLocSBin;
	}

	public void setZ_STLocSBin(String z_STLocSBin) {
		Z_STLocSBin = z_STLocSBin;
	}
	
}
