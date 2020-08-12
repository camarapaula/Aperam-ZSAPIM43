package com.syclo.sap.component.zaperam.object;

import com.syclo.agentry.BusinessLogicException;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_MaterialDocumentCreateBAPI;

/* All the custom agentry properties are created as variables here. The values are
 * retrieved from Z_MaterialDocumentCreateBAPI and stored into this POJO class via
 * getter and setter methods.
 * */

public class MaterialDocumentItem extends com.syclo.sap.component.goodsmovement.object.MaterialDocumentItem{
	
	public String Z_StorageBin = "";
	public String Z_MoveStorageBin = "";
	public String Z_MovementReason = "";
	public String Z_MovementCategory = "";
	public String Z_Consigned = "";
	
	/*
	* Default Constructor definition for the class
	* */
	
	public MaterialDocumentItem() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public MaterialDocumentItem(User user) {
		super(user);
	}
	
	/*
	 * Method to set the custom values Material from the Z_MaterialDocumentCreateBAPI to the respective variables in java
	 * */
	
	public void setProperties(User user, Z_MaterialDocumentCreateBAPI bapi) throws BusinessLogicException {
		try {
			//String prefix = "transaction.Items." + i + ".";
			String prefix = "transaction.";
			setVendor(_user.getString(prefix + "vendor"));
			setCostCenter(_user.getString(prefix + "costCenter"));
			setOrderNumber(_user.getString(prefix + "orderNumber"));
			setMaterial(_user.getString(prefix + "material"));
			setMovementType(_user.getString(prefix + "movementType"));
			setMovementIndicator(_user.getString(prefix + "movementIndicator"));
			setMovePlant(_user.getString(prefix + "movePlant"));
			setMoveStorageLocation(_user.getString(prefix + "moveStorageLocation"));
			setPlant(_user.getString(prefix + "plant"));
			setBatch(_user.getString(prefix + "batch"));
			setStorageLocation(_user.getString(prefix + "storageLocation"));
			setPostingDate(this._user.eval("<<" + prefix + "postingDate format=\"%Y%m%d\">>"));
			setQuantity(_user.getString(prefix + "quantity"));
			setText(_user.getString(prefix + "Text"));
			setReservationNumber(_user.getString(prefix + "reservationNumber"));
			setZ_MovementCategory(_user.getString(prefix + "Z_MovementCategory"));
			setZ_MovementReason(_user.getString(prefix + "Z_MovementReason"));
			setZ_MoveStorageBin(_user.getString(prefix + "Z_MoveStorageBin"));
			setZ_StorageBin(_user.getString(prefix + "Z_StorageBin"));
			if(_user.getBoolean(prefix + "Z_Consigned")){
				setZ_Consigned("X");				
			}
			
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}

	public String getZ_StorageBin() {
		return Z_StorageBin;
	}

	public void setZ_StorageBin(String z_StorageBin) {
		Z_StorageBin = z_StorageBin;
	}

	public String getZ_MoveStorageBin() {
		return Z_MoveStorageBin;
	}

	public void setZ_MoveStorageBin(String z_MoveStorageBin) {
		Z_MoveStorageBin = z_MoveStorageBin;
	}

	public String getZ_MovementReason() {
		return Z_MovementReason;
	}

	public void setZ_MovementReason(String z_MovementReason) {
		Z_MovementReason = z_MovementReason;
	}

	public String getZ_MovementCategory() {
		return Z_MovementCategory;
	}

	public void setZ_MovementCategory(String z_MovementCategory) {
		Z_MovementCategory = z_MovementCategory;
	}

	public String getZ_Consigned() {
		return Z_Consigned;
	}

	public void setZ_Consigned(String z_Consigned) {
		Z_Consigned = z_Consigned;
	}	

}
