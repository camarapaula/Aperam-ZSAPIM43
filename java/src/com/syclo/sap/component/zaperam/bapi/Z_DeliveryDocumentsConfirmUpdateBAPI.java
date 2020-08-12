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
//import com.syclo.sap.jco.JCO;

/*
 * This method is to save the custom values to the IT tables in SAP 
 * */
/*
 * This method is to create the SAPObject instance and call the set properties method for the GoodsMovementDocument object
 * */
public class Z_DeliveryDocumentsConfirmUpdateBAPI extends BAPI{
	
	public ArrayList<GoodsMovementDocument> _goodsDocs = new ArrayList<GoodsMovementDocument>();
	public Z_DeliveryDocumentsConfirmUpdateBAPI(User user, GregorianCalendar lu) throws Exception {
		super(user, lu);
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
	 * custom setParameters() method for Z_DeliveryDocumentsConfirmUpdateBAPI to save the custom values to the IT Tables. 
	 * */
	
	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		//Reading the returned in export tables from SAP
		
		try {
			super.setParameters(obj);
			JCO.Table deliveryTbl = this._tables.getTable("IT_DELIVERY");
			
			int issueCount = _goodsDocs.size();
			for(int j=0; j<issueCount; j++){
				int itemCount = _goodsDocs.get(j).getItems().length;
				for(int i = 0; i<itemCount;i++){
					deliveryTbl.appendRow();
					setValue(deliveryTbl, "MATNR", _goodsDocs.get(j).getItems()[i].getMaterial());
					setValue(deliveryTbl, "DEL_STATUS", _goodsDocs.get(j).getItems()[i].getPurReqNo());
					setValue(deliveryTbl, "ID", _goodsDocs.get(j).getItems()[i].getZ_ID());
				}
				
			}
			
		}
		catch(Exception e){
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
