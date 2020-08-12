package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.component.zaperam.object.GoodsMovementDocument;
import com.syclo.sap.jco.JCO;

/*
 * This method is to save the custom values to the IT tables in SAP 
 * */
/*
 * This method is to create the SAPObject instance and call the set properties method for the GoodsMovementDocument object
 * */

public class Z_DeliveryDocumentsPostBAPI extends BAPI{
	
	public ArrayList<GoodsMovementDocument> _goodsDocs = new ArrayList<GoodsMovementDocument>();
	public Z_DeliveryDocumentsPostBAPI(User u, GregorianCalendar lu) throws Exception {
		super(u, lu);
		// TODO Auto-generated constructor stub
		int issueCount = Integer.parseInt(this._user.eval("<<size transaction.Z_DeliveryIssue>>"));
		for(int i = 0; i<issueCount;i++){
			if(_user.getBoolean("transaction.Z_DeliveryIssue." + i + ".isEdited")){
				GoodsMovementDocument _goodsDoc= (GoodsMovementDocument) SAPObjectFactory.create(
						this._user.getSAPObject("Z_GoodsMovementDocument"), new Class[] { User.class },
						new Object[] { this._user });
				_goodsDoc.setProperties(_user, this, i);
				_goodsDocs.add(_goodsDoc);
			}
		}
	}
	
	/*
	 * custom setParameters() method for Z_DeliveryDocumentsPostBAPI to save the custom values to the IT Tables. 
	 * */
	
	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		try {
			super.setParameters(obj);
			setValue(_imports, "IV_DELIVERY", "D");
			JCO.Table pickupTbl = this._tables.getTable("IT_PICKUP");
			int issueCount = _goodsDocs.size();
			for(int j=0; j<issueCount; j++){
				int itemCount = _goodsDocs.get(j).getItems().length;
				for(int i = 0; i<itemCount;i++){
				pickupTbl.appendRow();
				setValue(pickupTbl, "PICK_LIST_NO", _goodsDocs.get(j).getItems()[i].getPickingListNumber());
				setValue(pickupTbl, "MATNR",_goodsDocs.get(j).getItems()[i].getMaterial());
				setValue(pickupTbl, "RSNUM", _goodsDocs.get(j).getItems()[i].getReservation());
				setValue(pickupTbl, "RSPOS", _goodsDocs.get(j).getItems()[i].getZ_ReservationItem());
				setValue(pickupTbl, "ID", _goodsDocs.get(j).getItems()[i].getZ_ID());
				setValue(pickupTbl, "MEINS", _goodsDocs.get(j).getItems()[i].getUOM());
				setValue(pickupTbl, "LGORT", _goodsDocs.get(j).getItems()[i].getStorageLocation());
				//setValue(pickupTbl, "SRC_LGPBE", _goodsDocs.get(j).getItems()[i].getStorageBin());
				setValue(pickupTbl, "ENMNG", _goodsDocs.get(j).getItems()[i].getQuantity());
				setValue(pickupTbl, "SRC_CHARG", _goodsDocs.get(j).getItems()[i].getBatch());
				//setValue(pickupTbl, "LIFNR", _goodsDocs.get(j).getItems()[i].getVendor());
				setValue(pickupTbl, "WERKS", _user.getString("transaction.Plant"));
				setValue(pickupTbl, "DEL_BIN", _goodsDocs.get(j).getMaterialDocument());
				_log.info("Delivery Person :: " + _goodsDocs.get(j).getZ_DeliveryPersName());
				setValue(pickupTbl, "WEMPF", _goodsDocs.get(j).getZ_DeliveryPersName());
					if (_goodsDocs.get(j).getItems()[i].getMovementType().length() > 0) {
						setValue(pickupTbl, "BWART", _goodsDocs.get(j).getItems()[i].getMovementType());
					} else {
						setValue(pickupTbl, "BWART", "261");
					}
				}
			}
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}

	/*This method is overridden from the standard BAPI class.
	 * The method is not utilized as the scenario here is only posting delivery data and nothing retrieved back from sap .
     */
	
@Override
public ArrayList<? extends SAPObject> processResults() throws Exception {
	// TODO Auto-generated method stub
	return null;
}
}
