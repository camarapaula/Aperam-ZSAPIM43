package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.MaterialDocumentItem;
import com.syclo.sap.jco.JCO;

/*
 * This method is to save the custom values to the IT tables in SAP 
 * */

public class Z_MaterialDocumentCreateBAPI extends BAPI{
	
	public ArrayList<MaterialDocumentItem> _matItems = new ArrayList<MaterialDocumentItem>();
	
	/*
	 * This method is to call the set properties method for the MaterialDocumentItem object
	 * */
	
	public Z_MaterialDocumentCreateBAPI(User user, GregorianCalendar lu) throws Exception {
		super(user, lu);
//		int itemCount = Integer.parseInt(this._user.eval("<<size transaction.Items>>"));
//		for(int i = 0; i<itemCount;i++){
			if(_user.getBoolean("transaction.IsEdited")){
				MaterialDocumentItem _matItem = (MaterialDocumentItem) SAPObjectFactory.create(
						this._user.getSAPObject("Z_MaterialDocumentItem"), new Class[] { User.class },
						new Object[] { this._user });
				_matItem.setProperties(_user, this);
				_matItems.add(_matItem);
			}
		//}
	}
	
	/*
	 * custom setParameters() method for Z_MaterialDocumentCreateBAPI to save the custom values to the IT Tables.
	 * */
	
	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		try {
			super.setParameters(obj);
			JCO.Table itemsTbl = this._tables.getTable("IT_INT_MOVEMENT");
			int sz = _matItems.size();
			for(int j = 0; j <sz ; j++){
			itemsTbl.appendRow();
			itemsTbl.setRow(itemsTbl.getNumRows() - 1);
			setValue(itemsTbl, "MATNR", _matItems.get(j).getMaterial());
			setValue(itemsTbl, "KOSTL", _matItems.get(j).getCostCenter());
			setValue(itemsTbl, "LIFNR", _matItems.get(j).getVendor());
			setValue(itemsTbl, "RSNUM", _matItems.get(j).getReservationNumber());
			setValue(itemsTbl, "DEST_CHARG", _matItems.get(j).getBatch());
			setValue(itemsTbl, "SRC_CHARG", _matItems.get(j).getBatch());
			setValue(itemsTbl, "SRC_WERKS", _matItems.get(j).getPlant());
			setValue(itemsTbl, "SRC_LGORT", _matItems.get(j).getStorageLocation());
			setValue(itemsTbl, "BWART", _matItems.get(j).getMovementType());
			setValue(itemsTbl, "DEST_LGORT", _matItems.get(j).getMoveStorageLocation());
			setValue(itemsTbl, "DEST_WERKS", _matItems.get(j).getMovePlant());
			setValue(itemsTbl, "BUDAT", _matItems.get(j).getPostingDate());
			setValue(itemsTbl, "AUFNR", _matItems.get(j).getOrderNumber());
			setValue(itemsTbl, "ST_LOC_TYPE", _matItems.get(j).getZ_MovementCategory());
			setValue(itemsTbl, "GRUND", _matItems.get(j).getZ_MovementReason());
			setValue(itemsTbl, "SRC_LGPBE", _matItems.get(j).getZ_StorageBin());
			setValue(itemsTbl, "DEST_LGPBE", _matItems.get(j).getZ_MoveStorageBin());
			setValue(itemsTbl, "MENGE", _matItems.get(j).getQuantity());
			if(_matItems.get(j).getZ_Consigned().length()>0){
				setValue(itemsTbl, "SOBKZ", "K");
				}
			}			
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}

	/*This method is overridden from the standard BAPI class.
	 * The method is not utilized as the scenario here is only posting Materials data and nothing retrieved back from sap .
	 */

	@Override
	public ArrayList<? extends SAPObject> processResults() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
