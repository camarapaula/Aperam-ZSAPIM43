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
 * This method is to save the custom values to the IT tables in SAP 
 * */

public class Z_PutAwayItemsPostBAPI  extends BAPI {

	public ArrayList<Z_PutAwayItem> _putAwayItems = new ArrayList<Z_PutAwayItem>();

	/*
	 * This method is to call the set properties method for the Z_PutAwayItem object
	 * */
	
	public Z_PutAwayItemsPostBAPI(User u, GregorianCalendar lu) throws Exception {
		super(u, lu);
		// TODO Auto-generated constructor stub
		//int itemCount = Integer.parseInt(this._user.eval("<<size transaction.Z_PutAwayItems>>"));
		//for (int i = 0; i < itemCount; i++) {
			//if(_user.getBoolean("transaction.Z_PutAwayItems." + i + ".isEdited")){
				Z_PutAwayItem _item = (Z_PutAwayItem) SAPObjectFactory.create(this._user.getSAPObject("Z_PutAwayItem"),
						new Class[] { User.class }, new Object[] { this._user });
				_item.setProperties(_user);
				_putAwayItems.add(_item);
			//}
		//}
	}
	
	/*
	 * custom setParameters() method for Z_PutAwayItemsPostBAPI to save the custom values to the IT Tables.
	 * */
	
	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		try {
			super.setParameters(obj);
			JCO.Table movementTbl = this._tables.getTable("IT_MOVEMENTS");
			int itemCount = _putAwayItems.size();
			for (int j = 0; j < itemCount; j++) {
				movementTbl.appendRow();
				setValue(movementTbl, "MATNR", _putAwayItems.get(j).getMaterial());
				if(_putAwayItems.get(j).getSBinChanged().equalsIgnoreCase("X")){
					setValue(movementTbl, "ST_LOC_TYPE", _putAwayItems.get(j).getMaterialIndicator());
				}
				setValue(movementTbl, "SRC_CHARG", _putAwayItems.get(j).getBatch());
				setValue(movementTbl, "LIFNR", _putAwayItems.get(j).getVendor());
				setValue(movementTbl, "MEINS", _putAwayItems.get(j).getUOM());
				setValue(movementTbl, "SRC_WERKS", _putAwayItems.get(j).getPlant());
				setValue(movementTbl, "DEST_WERKS", _putAwayItems.get(j).getPlant());
				setValue(movementTbl, "SRC_LGORT", _putAwayItems.get(j).getSLoc());
				//setValue(_imports,"IV_DEST", _putAwayItems.get(j).getZ_Printer());
				//setValue(movementTbl, "SRC_LGPBE", _putAwayItems.get(j).getSBin());
				//setValue(movementTbl, "", _putAwayItems.get(j).getQuantity());
				setValue(movementTbl, "MENGE", _putAwayItems.get(j).getPutAwayQty());
				setValue(movementTbl, "DEST_LGORT", _putAwayItems.get(j).getDestinationSloc());
				setValue(movementTbl, "DEST_LGPBE", _putAwayItems.get(j).getDestinationSBin());
				setValue(movementTbl, "LDEST", _putAwayItems.get(j).getIsPutAwayForm());
				//_log.info("Put Away Form :: " + _putAwayItems.get(j).getIsPutAwayForm());
				setValue(movementTbl, "BWART", "311");
				if(_putAwayItems.get(j).getVendor().length()>0){
					setValue(movementTbl, "SOBKZ", "K");
				}
			}

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}

	/*This method is overridden from the standard BAPI class.
	 * The method is not utilized as the scenario here is only posting Put away data and nothing retrieved back from sap .
	 */
	
	@Override
	public ArrayList<? extends SAPObject> processResults() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
