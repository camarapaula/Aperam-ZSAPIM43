package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectI;
import com.syclo.sap.User;
import com.syclo.sap.jco.JCO.Table;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_GetPutAwayItemFetchBAPI and stored into this POJO class via
 * getter and setter methods.
 * */
 
public class Z_PutAwayItem extends SAPObject implements SAPObjectI {
	
	public String ID = "";
	public String Material = "";
	public String MaterialDesc = "";
	public String Batch = "";
	public String Vendor = "";
	public String UOM = "";
	public String Plant = "";
	public String SLoc = "";
	public String SBin = "";
	public String LocBin = "";
	public String Quantity = "";
	public String DestinationSloc = "";
	public String DestinationSBin = "";
	public String isEdited = "";
	public String IsHold = "";
	public String MaterialIndicator = "";
	public String SlocChanged = "";
	public String SBinChanged = "";
	public String PutAwayQty = "";
	public String IsPutAwayForm = "";
	public String Z_Printer = "";
	
/*
* Default Constructor definition for the class
* */
	
	public Z_PutAwayItem() {
	}

/*
* Parameterized Constructor definition for the class
* */
	
	public Z_PutAwayItem(User user) {
		super(user);
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNotes(Table tbl) throws Exception {
		// TODO Auto-generated method stub
		
	}

/*
 * Method to set the custom values Z_PutAwayItem from the Z_GetPutAwayItemFetchBAPI to the respective variables in java
 * */
	
	@Override
	public void setProperties(Table tbl) throws Exception {		
		setMaterial(tbl.getString("MATNR"));
		setMaterialDesc(tbl.getString("TXZ01"));
		setMaterialIndicator(tbl.getString("ST_LOC_TYPE"));
		setBatch(tbl.getString("SRC_CHARG"));
		setVendor(tbl.getString("LIFNR"));
		setUOM(tbl.getString("MEINS"));
		setPlant(tbl.getString("SRC_WERKS"));
		setSLoc(tbl.getString("SRC_LGORT"));
		setDestinationSloc(tbl.getString("DEST_LGORT"));
		setSBin(tbl.getString("SRC_LGPBE"));
		setLocBin(getSLoc()+getSBin());
		setQuantity(tbl.getString("MENGE"));
		setIsPutAwayForm(tbl.getString("LDEST"));
		//_log.info("Put Away Form set :: " + tbl.getString("LDEST"));
		setID(getMaterial()+getLocBin()+getBatch()+getVendor());
	}
	
	/*
	 * Method to set the custom values PutAwayItem from the Z_PutAwayItemsPostBAPI to the respective variables in java
	 * */
	
	public void setProperties(User user) throws Exception {
		try {
			//String prefix = "transaction.Z_PutAwayItems." + i + ".";
			String prefix = "transaction.";
			//if(_user.getBoolean("transaction.Z_PutAwayItems." + i + ".isEdited")){
				setMaterial(user.getString(prefix + "Material"));
				setMaterialIndicator(user.getString(prefix + "MaterialIndicator"));
				setBatch(user.getString(prefix + "Batch"));
				setVendor(user.getString(prefix + "Vendor"));
				setUOM(user.getString(prefix + "UOM"));
				setPlant(user.getString(prefix + "Plant"));;
				setSLoc(user.getString(prefix + "SLoc"));
				setSBin(user.getString(prefix + "SBin"));
				//setZ_Printer(user.getString(prefix + "Z_Printer"));
				setIsPutAwayForm(user.getString(prefix + "IsPutAwayForm"));
				setQuantity(user.getString(prefix + "Quantity"));
				setPutAwayQty(user.getString(prefix + "PutAwayQty"));
				setDestinationSBin(user.getString(prefix + "DestinationSBin"));
				setDestinationSloc(user.getString(prefix + "DestinationSloc"));
				if(user.getBoolean(prefix + "SBinChanged")){
					setSBinChanged("X");
				}
			//}
     							
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		
	}

	public String getMaterial() {
		return Material;
	}

	public void setMaterial(String material) {
		Material = material;
	}

	public String getMaterialDesc() {
		return MaterialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		MaterialDesc = materialDesc;
	}

	public String getBatch() {
		return Batch;
	}

	public void setBatch(String batch) {
		Batch = batch;
	}

	public String getVendor() {
		return Vendor;
	}

	public void setVendor(String vendor) {
		Vendor = vendor;
	}

	public String getUOM() {
		return UOM;
	}

	public void setUOM(String uOM) {
		UOM = uOM;
	}

	public String getPlant() {
		return Plant;
	}

	public void setPlant(String plant) {
		Plant = plant;
	}

	public String getSLoc() {
		return SLoc;
	}

	public void setSLoc(String sLoc) {
		SLoc = sLoc.toUpperCase();
	}

	public String getSBin() {
		return SBin;
	}

	public void setSBin(String sBin) {
		SBin = sBin.toUpperCase();
	}

	public String getLocBin() {
		return LocBin;
	}

	public void setLocBin(String locBin) {
		LocBin = locBin.toUpperCase();
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getDestinationSloc() {
		return DestinationSloc;
	}

	public void setDestinationSloc(String destinationSloc) {
		DestinationSloc = destinationSloc.toUpperCase();
	}

	public String getIsEdited() {
		return isEdited;
	}

	public void setIsEdited(String isEdited) {
		this.isEdited = isEdited;
	}

	public String getIsHold() {
		return IsHold;
	}

	public void setIsHold(String isHold) {
		IsHold = isHold;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getDestinationSBin() {
		return DestinationSBin;
	}

	public void setDestinationSBin(String destinationSBin) {
		DestinationSBin = destinationSBin.toUpperCase();
	}

	public String getMaterialIndicator() {
		return MaterialIndicator;
	}

	public void setMaterialIndicator(String materialIndicator) {
		MaterialIndicator = materialIndicator;
	}

	public String getSlocChanged() {
		return SlocChanged;
	}

	public void setSlocChanged(String slocChanged) {
		SlocChanged = slocChanged.toUpperCase();
	}

	public String getSBinChanged() {
		return SBinChanged;
	}

	public void setSBinChanged(String sBinChanged) {
		SBinChanged = sBinChanged;
	}

	public String getPutAwayQty() {
		return PutAwayQty;
	}

	public void setPutAwayQty(String putAwayQty) {
		PutAwayQty = putAwayQty;
	}

	public String getIsPutAwayForm() {
		return IsPutAwayForm;
	}

	public void setIsPutAwayForm(String isPutAwayForm) {
		IsPutAwayForm = isPutAwayForm;
	}

	public String getZ_Printer() {
		return Z_Printer;
	}

	public void setZ_Printer(String z_Printer) {
		Z_Printer = z_Printer;
	}

}
