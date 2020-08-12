package com.syclo.sap.component.zaperam.stephandler;

import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_MaterialDocumentCreateBAPI;
import com.syclo.sap.component.zaperam.object.MaterialDocumentItem;

/*This Step handler class is called by the Z_TransferSteplet class.
* This Step handler class is utilized to invoke the BAPI class. 
* BAPI instance is created here and the standard run method is called.
* */

public class Z_TransferStepHandler extends StepHandler{
	
	public Z_TransferStepHandler() {
	}

	public Z_TransferStepHandler(User user) {
		super(user);
	}

	/*
	 * This method will be used to invoke the BAPI class, Z_MaterialDocumentCreateBAPI,
	 * Creates the SAPObject instance for the MaterialDocumentItem object.
	 */
	
	public void transferItem() throws AgentryException {
		this._log.entry();
		try {
			MaterialDocumentItem obj = (MaterialDocumentItem) SAPObjectFactory.create(
					this._user.getSAPObject("Z_MaterialDocumentItem"), new Class[] { User.class },
					new Object[] { this._user });
			Z_MaterialDocumentCreateBAPI bapi = (Z_MaterialDocumentCreateBAPI) BAPIFactory.create(
					"Z_MaterialDocumentCreateBAPI",
					new Class[] { User.class, GregorianCalendar.class },
					new Object[] { this._user, new GregorianCalendar() });
			bapi.run(obj);
			bapi.processResults();
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.exit();
	}

}
