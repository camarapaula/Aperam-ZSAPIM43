package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.Z_PutAwayItem;
import com.syclo.sap.jco.JCO;

/*
 * This method is to set the custom values to the IT_Movements table in SAP and get the 
 * returned values from ET_Movements to Agentry
 * */
public class Z_GetPutAwayItemFetchBAPI extends BAPI {

	public Z_GetPutAwayItemFetchBAPI(User u) throws AgentryException {
		super(u, new GregorianCalendar());
		// TODO Auto-generated constructor stub
	}
	
	/*
	 * custom setParameters() method for setting the custom values to the IT_Movements Table 
	 * */
	
	public void setParameters(SAPObject obj) throws Exception {
		this._log.entry();
		try {
			super.setParameters(obj);
			//Getting the fetch properties from Agentry
			String plant = this._user.getString("fetch.Plant").trim();
			String printer = this._user.getString("fetch.Z_Printer").trim();
			setValue(_imports,"IV_DEST", printer);
			//setValue(_imports,"IV_WERKS", plant);
			int sz = Integer.parseInt(this._user.eval("<<size fetch.Z_TempFilterObjects>>"));
			this._log.info("Fetch rows returned: {}", new Object[] { Integer.valueOf(sz) });
			String prefix = "fetch.Z_TempFilterObjects.";
			JCO.Table tbl = this._tables.getTable("IT_MOVEMENTS");
			for(int i = 0; i < sz; i++){
				tbl.appendRow();
				tbl.setRow(i);
				setValue(tbl, "SRC_LGORT", _user.getString(prefix + i + ".ISloc"));
				setValue(tbl, "MATNR", _user.getString(prefix + i + ".Material").toUpperCase());
				setValue(tbl, "SRC_LGPBE", _user.getString(prefix + i + ".DeliveryLocation"));
				setValue(tbl, "SRC_WERKS", plant);
				if(_user.getBoolean(prefix + i + ".NeedPutAwayForm")){
					setValue(tbl, "LDEST", "X");
				}
			}				
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}

	/*
	 * custom processResults() method for fetching the custom values for Z_PutAwayItem
	 * */
	
	@Override
	public ArrayList<SAPObject> processResults() throws Exception {
		ArrayList<SAPObject> putaways = new ArrayList<SAPObject>();
		try {
			JCO.Table _putawaytbl = this._tables.getTable("ET_MOVEMENTS");
			this._log.debug("rows returned: {}", new Object[] { Integer.valueOf(_putawaytbl.getNumRows()) });
			int numHdrRecs = _putawaytbl.getNumRows();
			for (int i = 0; i < numHdrRecs; ++i) {
				_putawaytbl.setRow(i);
				Z_PutAwayItem obj = (Z_PutAwayItem) createSAPObject("Z_PutAwayItem");
				obj.setProperties(_putawaytbl);
				putaways.add(obj);
			}

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit(Integer.valueOf(putaways.size()));
		return putaways;
	}
	
	/*
	 * This method will be creating the SAP Object - Z_PutAwayItem
	 * */
	
	public SAPObject createSAPObject(String name) throws AgentryException, Exception {
		return SAPObjectFactory.create(this._user.getSAPObject(name), new Class[] { User.class },
				new Object[] { this._user });
	}

}
