package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.ConversionUtility;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.OrderItem;
import com.syclo.sap.jco.JCO;

/*
 * This method is to set the custom values to the IV fields in SAP and get the 
 * returned values from ET_RECEIPT and ET_LTEXT to Agentry
 * */

public class Z_MaterialFetchBAPI extends BAPI{
	protected String returnUpdateDT = "";
	protected GregorianCalendar _callTime = null;
	
	public Z_MaterialFetchBAPI(User user, GregorianCalendar callTime) throws Exception {
		super(user, new GregorianCalendar());
		this._callTime = callTime;
	}
	
	/*
	 * custom setParameters() method for setting the custom values to the IV fields
	 */
	
	public void setParameters(SAPObject obj) throws Exception {
		this._log.entry();
		try {
			super.setParameters(obj);
			//Getting the fetch properties from Agentry
			String material = this._user.getString("fetch.Material").trim();
			String plant = this._user.getString("fetch.Plant").trim();
			setValue(_imports,"IV_MATNR", material);
			setValue(_imports,"IV_WERKS", plant);
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}
	
	/*
	 * custom processResults() method for fetching the custom values for Z_OrderItem
	 */
	
	public ArrayList<SAPObject> processResults() throws AgentryException {
		this._log.entry();
		//Reading the returned in export tables from SAP
		ArrayList<SAPObject> materials = new ArrayList<SAPObject>();
		try {
			this.returnUpdateDT = ConversionUtility.getTimeStampAsString(this._callTime, "MM/dd/yyyy HH:mm:ss");
			//The export table is looped over and the values are stored in Material POJO class as a collection
			JCO.Table _materialtbl = this._tables.getTable("ET_RECEIPT");
			this._log.debug("ET_RECEIPT rows returned: {}", new Object[] { Integer.valueOf(_materialtbl.getNumRows()) });
			JCO.Table _txttbl = this._tables.getTable("ET_LTEXT");
			this._log.debug("ET_LTEXT rows returned: {}", new Object[] { Integer.valueOf(_txttbl.getNumRows()) });
			JCO.Table _storagetbl = this._tables.getTable("ET_STOCK");
			this._log.debug("ET_STOCK rows returned: {}", new Object[] { Integer.valueOf(_storagetbl.getNumRows()) });

			materials.clear();
			int numHdrRecs = _materialtbl.getNumRows();
			for (int i = 0; i < numHdrRecs; ++i) {
				_materialtbl.setRow(i);
				OrderItem obj = (OrderItem) createSAPObject("Z_OrderItem");
				obj.setProperties(_materialtbl, _txttbl, _storagetbl, this);
				this._log.info("StorageInfo Count: " + String.valueOf(obj.getZ_MaterialStockInformations().length));
				materials.add(obj);
			}
		}catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit(Integer.valueOf(materials.size()));
		return materials;
	}
	
	/*
	 * This method will be creating the SAP Object - Z_OrderItem
	 */
	
	public SAPObject createSAPObject(String name) throws AgentryException, Exception {
		return SAPObjectFactory.create(this._user.getSAPObject(name), new Class[] { User.class },
				new Object[] { this._user });
	}

}

