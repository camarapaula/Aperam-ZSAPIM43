package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.jco.JCO;

/*
 * This method is to save the custom values to the IT tables in SAP 
 * */

public class Z_PickingListStatusUpdateBAPI extends BAPI{

	public Z_PickingListStatusUpdateBAPI(User user, GregorianCalendar lu)
			throws Exception {
		super(user, lu);
	}
	
	/*
	 * custom setParameters() method for Z_PickingListStatusUpdateBAPI to save the custom values to the IT Tables/IS structure. 
	 * */
	
	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		try {
			super.setParameters(obj);
			JCO.Table pickupTbl = this._tables.getTable("IT_PICKUP");
			String prefix = "transaction.PickingListItems.";
			//setFilterRange("IT_PICKING_RA", "I", "EQ", _user.getString(prefix + "PickingListNumber"), "");
			int itemCount = Integer.parseInt(this._user.eval("<<size transaction.PickingListItems>>"));
			this._log.debug(_user, "Item Count :: " + itemCount);
			if(_user.getString("transaction.DocumentStatus").length()>0){
				setValue(_imports, "IV_LOCK", "X");
			}
			if(_user.getString("transaction.IsCheck").equals("X")){
				setValue(_imports, "IV_FLAG", "X");
			}
			setValue(_imports, "IV_PICK_NUM",_user.getString("transaction.PickingListNumber"));
			for(int i = 0; i<itemCount;i++){
				pickupTbl.appendRow();
				setValue(pickupTbl, "MATNR", _user.getString(prefix + i + ".Material"));
				setValue(pickupTbl, "RSNUM", _user.getString(prefix + i + ".ReservationNbr"));
				setValue(pickupTbl, "RSPOS", _user.getString(prefix + i + ".ReservationItem"));
			}
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}

	/*This method is overridden from the standard BAPI class.
	   * The method is not utilized as the scenario here is only posting picking list status data and nothing retrieved back from sap .
	   */
	
	@Override
	public ArrayList<? extends SAPObject> processResults() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
