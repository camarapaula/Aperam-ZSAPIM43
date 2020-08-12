package com.syclo.sap.component.zaperam.object;

import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectI;
import com.syclo.sap.User;
import com.syclo.sap.jco.JCO.Table;

public class Z_MaterialStockInformation extends SAPObject implements SAPObjectI {

	public String Z_ID = "";
	public String Z_Material = "";
	public String Z_Plant = "";
	public String Z_StorageLocation = "";
	public String Z_StorageBin = "";
	public String Z_UnrestrictedStock = "";
	public String Z_ConsignedStock = "";
	public String Z_BlockedStock = "";
	public String Z_UOM = "";
	public String Z_Repairable = "";
	
	public Z_MaterialStockInformation() {
		super();
	}
	
	public Z_MaterialStockInformation(String z_ID, String z_Material, String z_Plant, String z_StorageLocation,
			String z_StorageBin, String z_UnrestrictedStock, String z_ConsignedStock, String z_BlockedStock,
			String z_UOM, String z_Repairable) {
		super();
		Z_ID = z_ID;
		Z_Material = z_Material;
		Z_Plant = z_Plant;
		Z_StorageLocation = z_StorageLocation;
		Z_StorageBin = z_StorageBin;
		Z_UnrestrictedStock = z_UnrestrictedStock;
		Z_ConsignedStock = z_ConsignedStock;
		Z_BlockedStock = z_BlockedStock;
		Z_UOM = z_UOM;
		Z_Repairable = z_Repairable;
	}

	public Z_MaterialStockInformation(User user) {
		super(user);
	}

	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNotes(Table arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProperties(Table tbl) throws Exception {
		setZ_Material(tbl.getString("MATNR"));
		setZ_Plant(tbl.getString("WERKS"));
		setZ_StorageLocation(tbl.getString("LGORT"));
		setZ_StorageBin(tbl.getString("LGPBE"));
		setZ_UnrestrictedStock(tbl.getString("LABST"));
		setZ_ConsignedStock(tbl.getString("KLABS"));
		setZ_BlockedStock(tbl.getString("SPEME"));
		setZ_UOM(tbl.getString("MEINS"));		
		if (tbl.getString("ZZID_REPA").equals("1")) {
			setZ_Repairable("Yes");
		} else {
			setZ_Repairable("No");
		}
		setZ_ID(getZ_Material() + getZ_Plant() + getZ_StorageLocation());
	}
	
	public void setProperties(User user) throws Exception {
		try {

			String prefix = "transaction.";
			setZ_Material(user.getString(prefix + "Z_Material"));
			setZ_Plant(user.getString(prefix + "Z_Plant"));
			setZ_StorageLocation(user.getString(prefix + "Z_StorageLocation"));
			setZ_StorageBin(user.getString(prefix + "Z_StorageBin"));
			setZ_UnrestrictedStock(user.getString(prefix + "Z_UnrestrictedStock"));
			setZ_ConsignedStock(user.getString(prefix + "Z_ConsignedStock"));
			setZ_BlockedStock(user.getString(prefix + "Z_BlockedStock"));
			setZ_UOM(user.getString(prefix + "Z_UOM"));
			setZ_Repairable(user.getString(prefix + "Z_Repairable"));
			setZ_ID(user.getString(prefix + "Z_ID"));
			
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
	}

	public String getZ_ID() {
		return Z_ID;
	}

	public void setZ_ID(String z_ID) {
		Z_ID = z_ID;
	}

	public String getZ_Material() {
		return Z_Material;
	}

	public void setZ_Material(String z_Material) {
		Z_Material = z_Material;
	}

	public String getZ_Plant() {
		return Z_Plant;
	}

	public void setZ_Plant(String z_Plant) {
		Z_Plant = z_Plant;
	}

	public String getZ_StorageLocation() {
		return Z_StorageLocation;
	}

	public void setZ_StorageLocation(String z_StorageLocation) {
		Z_StorageLocation = z_StorageLocation;
	}

	public String getZ_StorageBin() {
		return Z_StorageBin;
	}

	public void setZ_StorageBin(String z_StorageBin) {
		Z_StorageBin = z_StorageBin;
	}

	public String getZ_UnrestrictedStock() {
		return Z_UnrestrictedStock;
	}

	public void setZ_UnrestrictedStock(String z_UnrestrictedStock) {
		Z_UnrestrictedStock = z_UnrestrictedStock;
	}

	public String getZ_ConsignedStock() {
		return Z_ConsignedStock;
	}

	public void setZ_ConsignedStock(String z_ConsignedStock) {
		Z_ConsignedStock = z_ConsignedStock;
	}

	public String getZ_BlockedStock() {
		return Z_BlockedStock;
	}

	public void setZ_BlockedStock(String z_BlockedStock) {
		Z_BlockedStock = z_BlockedStock;
	}

	public String getZ_UOM() {
		return Z_UOM;
	}

	public void setZ_UOM(String z_UOM) {
		Z_UOM = z_UOM;
	}

	public String getZ_Repairable() {
		return Z_Repairable;
	}

	public void setZ_Repairable(String z_Repairable) {
		Z_Repairable = z_Repairable;
	}

}
