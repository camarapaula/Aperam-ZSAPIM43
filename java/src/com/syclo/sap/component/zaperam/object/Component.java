package com.syclo.sap.component.zaperam.object;

import com.syclo.agentry.BusinessLogicException;
import com.syclo.sap.User;
import com.syclo.sap.jco.JCO.Table;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_DeliveryDocumentsPostBAPI, Z_DeliveryDocumentsConfirmUpdateBAPI and stored into this POJO class via
 * getter and setter methods.
 * */

public class Component extends com.syclo.sap.component.goodsmovement.object.Component{
	
	public String PickingListNumber = "";
	public String StorageBin = "";
	public String Z_DeliveryBin = "";
	public String Z_DestinationBin = "";
	public String Z_LocationBin = "";
	public String Z_isSelected = "";
	public String MaterialDescription = "";
	public String Z_ID = "";
	public String PurReqNo = "";
	public String MovementType = "";
	public String Z_ReservationItem = "";

	/*
	* Default Constructor definition for the class
	* */
	
	public Component() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public Component(User user) {
		super(user);
		this._imUser = user;
	}
	
	/*
	 * Method to set the custom values Component from the Z_DeliveryDocumentsFetchBAPI to the respective variables in java
	 * */
	
	public void setProperties(Table tbl) throws Exception {
		// TODO Auto-generated method stub
		setPickingListNumber(tbl.getString("PICK_LIST_NO"));
		setMaterial(tbl.getString("MATNR"));
		setReservation(tbl.getString("RSNUM"));
		setZ_ReservationItem(tbl.getString("RSPOS"));
		setPurReqNo(tbl.getString("BANFN"));
		setUOM(tbl.getString("MEINS"));
		setQuantity(tbl.getString("ENMNG"));
		setStorageLocation(tbl.getString("LGORT"));
		setStorageBin(tbl.getString("SRC_LGPBE"));
		setBatch(tbl.getString("SRC_CHARG"));
		setMaterialDescription(tbl.getString("TXZ01"));
		setMovementType(tbl.getString("BWART"));
		setZ_DeliveryBin(tbl.getString("DEL_BIN"));
		setZ_ID(tbl.getString("ID").trim());
		setZ_isSelected("True");
		setZ_LocationBin(getStorageLocation()+getStorageBin());
		setID(getMaterial()+getReservation()+getZ_ReservationItem()+getPickingListNumber());
		setComponentID(getMaterial()+getReservation()+getZ_ReservationItem()+getPickingListNumber());
	}
	
	/*
	 * Method to set the custom values component from the Z_DeliveryDocumentsPostBAPI,
	 * Z_DeliveryDocumentsConfirmUpdateBAPI to the respective variables in java
	 * */
	
	public void setProperties(User user, int i, int j) throws BusinessLogicException {
		try {
			String prefix = "transaction.Z_DeliveryIssue." + i + ".Items." + j + ".";
			setPickingListNumber(user.getString(prefix + "PickingListNumber"));
			setMaterial(user.getString(prefix + "Material"));
			setReservation(user.getString(prefix + "Reservation"));
			setZ_ReservationItem(user.getString(prefix + "Z_ReservationItem"));
			setPurReqNo(user.getString(prefix + "PurReqNo"));
			setUOM(user.getString(prefix + "UOM"));
			setStorageLocation(user.getString(prefix + "StorageLocation"));
			setStorageBin(user.getString(prefix + "StorageBin"));
			setQuantity(user.getString(prefix + "Quantity"));
     		setBatch(user.getString(prefix + "Batch"));
     		setMovementType(user.getString(prefix + "MovementType"));
     		if(user.getString(prefix + "Z_ID").trim().equalsIgnoreCase("")){
     			setZ_ID("1");
     		}else{
     			int id = Integer.parseInt(user.getString(prefix + "Z_ID").trim())+1;
     			setZ_ID(Integer.toString(id));
     		}
//			setVendor(user.getString(prefix + "Vendor"));	
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}

	public String getPickingListNumber() {
		return PickingListNumber;
	}

	public void setPickingListNumber(String pickingListNumber) {
		PickingListNumber = pickingListNumber;
	}

	public String getStorageBin() {
		return StorageBin;
	}

	public void setStorageBin(String storageBin) {
		StorageBin = storageBin;
	}

	public String getZ_DeliveryBin() {
		return Z_DeliveryBin;
	}

	public void setZ_DeliveryBin(String z_DeliveryBin) {
		Z_DeliveryBin = z_DeliveryBin;
	}

	public String getZ_DestinationBin() {
		return Z_DestinationBin;
	}

	public void setZ_DestinationBin(String z_DestinationBin) {
		Z_DestinationBin = z_DestinationBin;
	}

	public String getZ_LocationBin() {
		return Z_LocationBin;
	}

	public void setZ_LocationBin(String z_LocationBin) {
		Z_LocationBin = z_LocationBin;
	}

	public String getZ_isSelected() {
		return Z_isSelected;
	}

	public void setZ_isSelected(String z_isSelected) {
		Z_isSelected = z_isSelected;
	}

	public String getMaterialDescription() {
		return MaterialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		MaterialDescription = materialDescription;
	}

	public String getZ_ID() {
		return Z_ID;
	}

	public void setZ_ID(String z_ID) {
		Z_ID = z_ID;
	}
	
	
	public String getPurReqNo() {
		return PurReqNo;
	}

	public void setPurReqNo(String purReqNo) {
		PurReqNo = purReqNo;
	}

	public String getMovementType() {
		return MovementType;
	}

	public void setMovementType(String movementType) {
		MovementType = movementType;
	}

	public String getZ_ReservationItem() {
		return Z_ReservationItem;
	}

	public void setZ_ReservationItem(String z_ReservationItem) {
		Z_ReservationItem = z_ReservationItem;
	}

}
