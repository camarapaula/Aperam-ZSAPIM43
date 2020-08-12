package com.syclo.sap.component.zaperam.bapi;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.SAPObject;
import com.syclo.sap.User;
import com.syclo.sap.bapi.BAPI;
import com.syclo.sap.jco.JCO;

/*
 * This method is to save the custom values to the IS structures in SAP 
 * */

public class Z_MaterialLabelPostBAPI extends BAPI{
	public Z_MaterialLabelPostBAPI(User user, GregorianCalendar lu)
			throws Exception {
		super(user, lu);
	}
	
	/*
	 * setParameters() method for Z_MaterialLabelPostBAPI to save the custom values to the IT Tables/IS structure. 
	 * */

	public void setParameters(SAPObject obj) throws AgentryException {
		this._log.entry();
		try {
			super.setParameters(obj);
			JCO.Structure materialLblStr = this._imports.getStructure("IS_LABEL_PRINT");
			String prefix = "transaction.";
			setValue(materialLblStr, "LDEST", _user.getString(prefix + "Z_Printer"));
			setValue(materialLblStr, "WERKS",_user.getString(prefix + "Plant").toUpperCase());
			setValue(materialLblStr, "NO_OF_PRINT", _user.getString(prefix + "Z_NoOfCopies"));
			setValue(materialLblStr, "VBELN",_user.getString(prefix + "Z_IBNumber"));
			setValue(materialLblStr, "MATNR", _user.getString(prefix + "Material").toUpperCase());
			setValue(materialLblStr, "CHARG",_user.getString(prefix + "Batch").toUpperCase());
			setValue(materialLblStr, "LIFNR",_user.getString(prefix + "Z_Vendor"));
			setValue(materialLblStr, "LGORT",_user.getString(prefix + "storageLocation").toUpperCase());
		    if(_user.getString(prefix + "Z_IBNumber").length()==0){
				setValue(materialLblStr, "VBELN",_user.getString(prefix + "Z_IBDelID"));
			} 
//			setValue(materialLblStr, "",_user.getString(prefix + "Z_IBDelID"));
//			setValue(materialLblStr, "",_user.getString(prefix + "Z_ExpirationDate"));
//			setValue(materialLblStr, "",_user.getString(prefix + "Z_ReceiveDate"));
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}
	
	/*This method is overridden from the standard BAPI class.
     * The method is not utilized as the scenario here is only posting material label and nothing retrieved back from sap .
	 */

	@Override
	public ArrayList<? extends SAPObject> processResults() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
