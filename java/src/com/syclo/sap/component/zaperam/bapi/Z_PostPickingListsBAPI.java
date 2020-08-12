package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.Z_PickingListItem;
import com.syclo.sap.jco.JCO;

/*
 * This method is to save the custom values to the IT tables in SAP 
 * */

public class Z_PostPickingListsBAPI extends BAPI{
	
	protected Z_PickingListItem _pl = null;
	public Z_PostPickingListsBAPI(User user, GregorianCalendar lu)
			throws Exception {
		super(user, lu);
	}
	
	public Z_PostPickingListsBAPI(User user, GregorianCalendar lu, SAPObject obj)
			throws Exception {
		super(user, lu);
		_pl = (Z_PickingListItem)obj;
	}
	
	/*
	 * custom setParameters() method for Z_PostPickingListsBAPI to save the custom values to the IT Tables.
	 * */
	
	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		try {
			super.setParameters(obj);
			if(_pl.getIsDelivery().equalsIgnoreCase("X")){
				setValue(_imports, "IV_DELIVERY", "P");
			}
			JCO.Table pickupTbl = this._tables.getTable("IT_PICKUP");
			//int itemCount = _pl.getPickingListItems().length;
			//for(int i = 0; i<itemCount;i++){
			pickupTbl.appendRow();
			setValue(pickupTbl, "PICK_LIST_NO", _pl.getPickingListNumber());
			setValue(pickupTbl, "MATNR",_pl.getMaterial());
			setValue(pickupTbl, "RSNUM", _pl.getReservationNbr());
			setValue(pickupTbl, "RSPOS", _pl.getReservationItem());
			setValue(pickupTbl, "MEINS", _pl.getUOM());
			setValue(pickupTbl, "LGORT", _pl.getStorageLocation());
			setValue(pickupTbl, "SRC_LGPBE", _pl.getStorageBin());
			setValue(pickupTbl, "ENMNG", _pl.getQuantity());
			setValue(pickupTbl, "DEST_CHARG", _pl.getBatchNo());
			setValue(pickupTbl, "LIFNR", _pl.getVendor());
			setValue(pickupTbl, "PICK_COMP", _pl.getIsManualComplete());
			setValue(pickupTbl, "COUNT_STATUS", _pl.getIsInThreshold());
			setValue(pickupTbl, "COUNT_QUAN", _pl.getThresholdCount());
			setValue(pickupTbl, "WERKS", _pl.getPlant());
			setValue(pickupTbl, "DEL_BIN", _pl.getDeliveryBin());
			if (_pl.getIsDelivery().length() > 0) {
				if(_pl.Z_Consigned.length()>0){
					setValue(pickupTbl, "SOBKZ", "K");
				}
				if (_pl.getMovementType().length() > 0) {
					setValue(pickupTbl, "BWART", _pl.getMovementType());
				} else {
					setValue(pickupTbl, "BWART", "261");
				}
			}else if(_pl.Z_Consigned.length()>0){
				setValue(pickupTbl, "SOBKZ", "K");
				setValue(pickupTbl, "BWART","411");
			}else{
				setValue(pickupTbl, "BWART","311");
			}
		/*	if(_pl.Z_Consigned.length()>0){
				setValue(pickupTbl, "SOBKZ", "K");
				setValue(pickupTbl, "BWART","411");
			}else if(_pl.getMovementType().length()>0){
				setValue(pickupTbl, "BWART", _pl.getMovementType());
			}
			else{
				setValue(pickupTbl, "BWART","311");
			}*/
			//}
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}
	
	/*This method is overridden from the standard BAPI class.
	 * The method is not utilized as the scenario here is only posting picking data and nothing retrieved back from sap .
	 */
	
	@Override
	public ArrayList<? extends SAPObject> processResults() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
