package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.Material;
import com.syclo.sap.jco.JCO;

/*
 * This method is to save the custom values to the IT tables in SAP 
 * */

public class Z_AdhocMaterialCountPostBAPI extends BAPI{
	protected Material _material = null;

	public Z_AdhocMaterialCountPostBAPI(User user, GregorianCalendar lu, Material material)
			throws Exception {
		super(user, lu);
		setMaterial(material);
	}
	
	/*
	 * This method is to call the set properties method for the Material object
	 * */
	
	public void init() throws Exception {
		getMaterial().setProperties(this._user, this);
	}
	
	/*
	 * custom setParameters() method for Z_AdhocMaterialCountPostBAPI to save the custom values to the IT Tables and IS structure. 
	 * */
	
	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		try {
			super.setParameters(obj);
			JCO.Structure headerStr = this._imports.getStructure("IS_PHYSINV_HEADER");
			setValue(headerStr, "PLANT",_material.getPlant());
			setValue(headerStr, "STGE_LOC", _material.getStorageLocation());
			setValue(headerStr, "DOC_DATE",_material.getZ_CountedDate());
			setValue(headerStr, "PLAN_DATE", _material.getZ_CountedDate());
			
			setValue(_imports, "IV_COUNT_FLAG", "X");
			
			JCO.Table items = this._tables.getTable("IT_PHYSINV_ITEMS");
			items.appendRow();
			setValue(items, "MATERIAL", _material.getMaterial());
			setValue(items, "BATCH", _material.getBatch());
			
			JCO.Table countItems = this._tables.getTable("IT_COUNT_ITEMS");
			countItems.appendRow();
			setValue(countItems, "ITEM", "001");
			setValue(countItems, "MATERIAL", _material.getMaterial());
			setValue(countItems, "BATCH", _material.getBatch());
			setValue(countItems, "ENTRY_QNT", _material.getCounted());
			setValue(countItems, "ENTRY_UOM", _material.getUOM());
			setValue(countItems, "ZERO_COUNT", _material.getZeroCount());
			//_log.info("Zero Counting Value :: " + _material.getZeroCount());
			
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}
	
  /*This method is overridden from the standard BAPI class.
   * The method is not utilized as the scenario here is only posting Adhoc count data and nothing retrieved back from sap .
   */
	
	@Override
	public ArrayList<? extends SAPObject> processResults() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Material getMaterial() {
		return this._material;
	}

	public void setMaterial(Material material) {
		this._material = material;
	}

}
