package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.ConversionUtility;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.Material;
import com.syclo.sap.jco.JCO;

/*
 * This method is to set the custom values to the IT tables in SAP and get the 
 * returned values from ET tables to Agentry
 * */

public class Z_MaterialAdhocCountFetchBAPI extends BAPI{
	protected String returnUpdateDT = "";
	protected GregorianCalendar _callTime = null;
	
	public Z_MaterialAdhocCountFetchBAPI(User user, GregorianCalendar callTime) throws Exception {
		super(user, new GregorianCalendar());
		this._callTime = callTime;
	}
	
	/*
	 * custom setParameters() method for setting the custom values to the IT Tables. 
	 * */
	
	public void setParameters(SAPObject obj) throws Exception {
		this._log.entry();
		try {
			super.setParameters(obj);
			//Getting the fetch properties from Agentry
			String plant = this._user.getString("fetch.Plant").trim();
			String material = this._user.getString("fetch.Material").trim();
			String sloc = this._user.getString("fetch.SLoc").trim();
			String batch = this._user.getString("fetch.Batch").trim();
			setFilterRange("IT_PLANT_RA", "I", "EQ", plant, "");
			setFilterRange("IT_STORAGELOC_RA", "I", "EQ", sloc, "");
			setFilterRange("IT_MATERIAL_RA", "I", "EQ", material, "");
			setFilterRange("IT_BATCH_RA", "I", "EQ", batch, "");
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}
	
	/*
	 * custom processResults() method for fetching the custom values for Material from ET_ITEMS tables.
	 * */
	
	public ArrayList<SAPObject> processResults() throws AgentryException {
		this._log.entry();
		//Reading the returned in export tables from SAP
		ArrayList<SAPObject> materials = new ArrayList<SAPObject>();
		try {
			this.returnUpdateDT = ConversionUtility.getTimeStampAsString(this._callTime, "MM/dd/yyyy HH:mm:ss");
			//The export table is looped over and the values are stored in Material POJO class as a collection
			JCO.Table _materialtbl = this._tables.getTable("ET_ITEMS");
			this._log.debug("rows returned: {}", new Object[] { Integer.valueOf(_materialtbl.getNumRows()) });

			materials.clear();
			int numHdrRecs = _materialtbl.getNumRows();
			for (int i = 0; i < numHdrRecs; ++i) {
				_materialtbl.setRow(i);
				Material obj = (Material) createSAPObject(
						"Material");
				obj.init(this._user, _materialtbl);
				materials.add(obj);
			}
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit(Integer.valueOf(materials.size()));
		return materials;
	}
	
	/*
	 * This method will be creating the SAP Object - Material
	 * */
	
	public SAPObject createSAPObject(String name) throws AgentryException, Exception {
		return SAPObjectFactory.create(this._user.getSAPObject(name), new Class[] { User.class },
				new Object[] { this._user });
	}

}
