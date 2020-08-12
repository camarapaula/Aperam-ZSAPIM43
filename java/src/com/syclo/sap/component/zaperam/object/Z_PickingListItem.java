package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectI;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.stephandler.Z_PostPickingListsStepHandler;
import com.syclo.sap.jco.JCO.Table;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_PickingListsFetchBAPI,Z_PostPickingListsStepHandler and stored into this POJO class via
 * getter and setter methods.
 * */

public class Z_PickingListItem extends SAPObject implements SAPObjectI {
	
	/*
	* Default Constructor definition for the class
	* */
	
	public Z_PickingListItem() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public Z_PickingListItem(User user) {
		super(user);
	}
	
	public String ID = "";
	public String PickingListNumber = "";
	public String Material = "";
	public String MaterialDesc = "";
	public String StorageLocation = "";
	public String StorageBin = "";
	public String RemainingQty = "";
	public String UOM = "";
	public String ReservationNbr = "";
	public String ReservationItem = "";
	public String BatchNo = "";
	public String Vendor = "";
	public String Quantity = "";
	public String IsPicked = "";
	public String IsInThreshold = "";
	public String ThresholdCount = "";
	public String Plant = "";
	public String DestinationLoc = "";
    public String DestinationBin = "";
    public String LocationBin = "";
    public String MovementType = "";
    public String SrcBatchNo = "";
    public String IsManualComplete = "NO";
    public String CountDate = "";
    public String AvailStock = "";
    public String DeliveryStatus = "";
    public String DeliveryBin = "";
    public String Z_Consigned = "";
    public String IsDelivery = "";
    public String Z_IsThresholdCheck = "";
    public String Z_UnloadPoint = "";
    public String SortingKey = "";

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setNotes(Table arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/*
	 * Method to set the custom values Z_PickingListItem from the Z_PickingListsFetchBAPI to the respective variables in java
	 * */
	
	@Override
	public void setProperties(Table tbl) throws Exception {
		// TODO Auto-generated method stub
		setPickingListNumber(tbl.getString("PICK_LIST_NO"));
		setMaterial(tbl.getString("MATNR"));
		setReservationNbr(tbl.getString("RSNUM"));
		setReservationItem(tbl.getString("RSPOS"));
		setRemainingQty(tbl.getString("BDMNG"));
		setUOM(tbl.getString("MEINS"));
		setStorageLocation(tbl.getString("LGORT"));
		setStorageBin(tbl.getString("SRC_LGPBE"));
		setDestinationLoc(tbl.getString("ABLAD"));
		setMovementType(tbl.getString("BWART"));
		setSrcBatchNo(tbl.getString("SRC_CHARG"));
		setAvailStock(tbl.getString("COUNT_QUAN"));
		setZ_UnloadPoint(tbl.getString("ABLAD"));
		setDeliveryStatus(tbl.getString("DEL_STATUS"));
		setLocationBin(getStorageLocation()+getStorageBin());
		if(tbl.getString("SOBKZ").equalsIgnoreCase("X")){
			setZ_IsThresholdCheck("X");
		}
		
		setID(getMaterial()+getReservationNbr()+getReservationItem());
		setSortingKey(getStorageBin()+getMaterial());
	}
	
	public void setProperties(User u, int i) throws Exception {
		
		try {
			String prefix = "transaction.PickingListItems." + i + ".";
			setPickingListNumber(u.getString(prefix + "PickingListNumber"));
			setMaterial(u.getString(prefix + "Material"));
			setReservationNbr(u.getString(prefix + "ReservationNbr"));
			setReservationItem(u.getString(prefix + "ReservationItem"));
			setUOM(u.getString(prefix + "UOM"));
			setStorageLocation(u.getString(prefix + "StorageLocation"));
			setStorageBin(u.getString(prefix + "StorageBin"));
			setQuantity(u.getString(prefix + "Quantity"));
			setBatchNo(u.getString(prefix + "BatchNo"));
			setVendor(u.getString(prefix + "Vendor"));		
			setMovementType(u.getString(prefix + "MovementType"));
			setSrcBatchNo(u.getString(prefix + "SrcBatchNo"));
			if(u.getBoolean(prefix + "IsDelivery")){
				setIsDelivery("X");
			}
			if(u.getBoolean(prefix + "IsManualComplete")){
				setIsManualComplete("YES");
			}if(u.getBoolean(prefix + "IsInThreshold")){
				setIsInThreshold("X");
				setThresholdCount(u.getString(prefix + "ThresholdCount"));
				setCountDate(this._user.eval("<<" + prefix + "CountDate format=\"%Y%m%d\">>"));
			}if((u.getBoolean(prefix + "Z_Consigned"))){
				setZ_Consigned("X");
			}
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		
	}
	
	/*
	 * Method to set the custom values PickingListItem from the Z_PostPickingListsStepHandler to the respective variables in java
	 * */
	
	public void setProperties(User u, Z_PostPickingListsStepHandler handler) throws Exception {

		try {
			String prefix = "transaction.";
			setPickingListNumber(u.getString(prefix + "PickingListNumber"));
			setMaterial(u.getString(prefix + "Material"));
			setReservationNbr(u.getString(prefix + "ReservationNbr"));
			setReservationItem(u.getString(prefix + "ReservationItem"));
			setUOM(u.getString(prefix + "UOM"));
			setStorageLocation(u.getString(prefix + "StorageLocation"));
			setStorageBin(u.getString(prefix + "StorageBin"));
			setQuantity(u.getString(prefix + "Quantity"));
			setBatchNo(u.getString(prefix + "BatchNo"));
			setVendor(u.getString(prefix + "Vendor"));
			setMovementType(u.getString(prefix + "MovementType"));
			setSrcBatchNo(u.getString(prefix + "SrcBatchNo"));
			setDeliveryBin(u.getString(prefix + "DeliveryBin"));
			setPlant(u.getString(prefix + "Plant"));
			if(u.getBoolean(prefix + "IsDelivery") || u.getBoolean(prefix + "IsManualComplete") && Double.parseDouble(u.getString(prefix + "Quantity"))==0){
				setIsDelivery("X");
			}
			if (u.getBoolean(prefix + "IsManualComplete")) {
				setIsManualComplete("YES");
			}
			if (u.getBoolean(prefix + "IsInThreshold")) {
				setIsInThreshold("X");
				setThresholdCount(u.getString(prefix + "ThresholdCount"));
				setCountDate(this._user.eval("<<" + prefix + "CountDate format=\"%Y%m%d\">>"));
			}
			if((u.getBoolean(prefix + "Z_Consigned"))){
				setZ_Consigned("X");
			}
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}

	}

	public String getPickingListNumber() {
		return PickingListNumber;
	}

	public void setPickingListNumber(String pickingListNumber) {
		PickingListNumber = pickingListNumber;
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

	public String getStorageLocation() {
		return StorageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		StorageLocation = storageLocation;
	}

	public String getRemainingQty() {
		return RemainingQty;
	}

	public void setRemainingQty(String remainingQty) {
		RemainingQty = remainingQty;
	}

	public String getUOM() {
		return UOM;
	}

	public void setUOM(String uOM) {
		UOM = uOM;
	}

	public String getReservationNbr() {
		return ReservationNbr;
	}
	
	public void setReservationNbr(String reservationNbr) {
		ReservationNbr = reservationNbr;
	}
	
	public String getReservationItem() {
		return ReservationItem;
	}

	public void setReservationItem(String reservationItem) {
		ReservationItem = reservationItem;
	}

	public String getBatchNo() {
		return BatchNo;
	}

	public void setBatchNo(String batchNo) {
		BatchNo = batchNo;
	}

	public String getVendor() {
		return Vendor;
	}

	public void setVendor(String vendor) {
		Vendor = vendor;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public String getIsPicked() {
		return IsPicked;
	}

	public void setIsPicked(String isPicked) {
		IsPicked = isPicked;
	}

	public String getIsInThreshold() {
		return IsInThreshold;
	}

	public void setIsInThreshold(String isInThreshold) {
		IsInThreshold = isInThreshold;
	}

	public String getThresholdCount() {
		return ThresholdCount;
	}

	public void setThresholdCount(String thresholdCount) {
		ThresholdCount = thresholdCount;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPlant() {
		return Plant;
	}

	public void setPlant(String plant) {
		Plant = plant;
	}

	public String getStorageBin() {
		return StorageBin;
	}

	public void setStorageBin(String storageBin) {
		StorageBin = storageBin;
	}

	public String getDestinationLoc() {
		return DestinationLoc;
	}

	public void setDestinationLoc(String destinationLoc) {
		DestinationLoc = destinationLoc;
	}

	public String getDestinationBin() {
		return DestinationBin;
	}

	public void setDestinationBin(String destinationBin) {
		DestinationBin = destinationBin;
	}

	public String getLocationBin() {
		return LocationBin;
	}

	public void setLocationBin(String locationBin) {
		LocationBin = locationBin;
	}

	public String getMovementType() {
		return MovementType;
	}

	public void setMovementType(String movementType) {
		MovementType = movementType;
	}

	public String getSrcBatchNo() {
		return SrcBatchNo;
	}

	public void setSrcBatchNo(String srcBatchNo) {
		SrcBatchNo = srcBatchNo;
	}

	public String getIsManualComplete() {
		return IsManualComplete;
	}

	public void setIsManualComplete(String isManualComplete) {
		IsManualComplete = isManualComplete;
	}

	public String getCountDate() {
		return CountDate;
	}

	public void setCountDate(String countDate) {
		CountDate = countDate;
	}

	public String getAvailStock() {
		return AvailStock;
	}

	public void setAvailStock(String availStock) {
		AvailStock = availStock;
	}

	public String getDeliveryStatus() {
		return DeliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		DeliveryStatus = deliveryStatus;
	}

	public String getDeliveryBin() {
		return DeliveryBin;
	}

	public void setDeliveryBin(String deliveryBin) {
		DeliveryBin = deliveryBin;
	}

	public String getZ_Consigned() {
		return Z_Consigned;
	}

	public void setZ_Consigned(String z_Consigned) {
		Z_Consigned = z_Consigned;
	}

	public String getIsDelivery() {
		return IsDelivery;
	}

	public void setIsDelivery(String isDelivery) {
		IsDelivery = isDelivery;
	}

	public String getZ_IsThresholdCheck() {
		return Z_IsThresholdCheck;
	}

	public void setZ_IsThresholdCheck(String z_IsThresholdCheck) {
		Z_IsThresholdCheck = z_IsThresholdCheck;
	}

	public String getZ_UnloadPoint() {
		return Z_UnloadPoint;
	}

	public void setZ_UnloadPoint(String z_UnloadPoint) {
		Z_UnloadPoint = z_UnloadPoint;
	}	
	
	public String getSortingKey() {
		return SortingKey;
	}
	
	public void setSortingKey(String sortingKey) {
		SortingKey = sortingKey;
	}

}
