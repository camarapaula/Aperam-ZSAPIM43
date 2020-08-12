package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;

/*
 * This method is to save the custom values to the IV_FLAG in SAP 
 * */

public class Z_PhysicalInvDocStatusUpdateBAPI extends BAPI{

	public Z_PhysicalInvDocStatusUpdateBAPI(User user, GregorianCalendar lu)
			throws Exception {
		super(user, lu);
	}
	
	/*
	 * custom setParameters() method for Z_PhysicalInvDocStatusUpdateBAPI to save the custom values to the IV field. 
	 * */
	
	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		try {
			super.setParameters(obj);
			String prefix = "transaction.";
			setFilterRange("IT_PHYSINVDOC_RA", "I", "EQ", _user.getString(prefix + "PhysInventory"), "");
			if(_user.getString("transaction.IsCheck").equals("X")){
				setValue(_imports, "IV_FLAG", "X");
			}
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}

	@Override
	public ArrayList<? extends SAPObject> processResults() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
