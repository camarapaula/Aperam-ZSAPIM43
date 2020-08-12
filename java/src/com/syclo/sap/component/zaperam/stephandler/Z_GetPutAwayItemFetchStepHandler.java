package com.syclo.sap.component.zaperam.stephandler;

import java.util.ArrayList;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_GetPutAwayItemFetchBAPI;
import com.syclo.sap.component.zaperam.object.Z_PutAwayItem;

/*This Step handler class is called by the  Z_GetPutAwayItemFetchSteplet class.
* This Step handler class is utilized to invoke the BAPI class. 
* BAPI instance is created here and the standard run method is called.
* */

public class Z_GetPutAwayItemFetchStepHandler extends StepHandler{
	public Z_GetPutAwayItemFetchStepHandler() {
	}

	public Z_GetPutAwayItemFetchStepHandler(User user) {
		super(user);
	}
	
/*
 * This method will be invoked from the Steplet class - Z_GetPutAwayItemFetchSteplet and
 * will be used to invoke the BAPI class, Z_GetPutAwayItemFetchBAPI,
 * Creates the SAPObject instance for the Z_PutAwayItem object.
 */
	
	public Z_PutAwayItem[] getLists() throws AgentryException {
		this._log.entry();
		Z_PutAwayItem[] array = new Z_PutAwayItem[0];
		try {
			Z_PutAwayItem obj = (Z_PutAwayItem) SAPObjectFactory.create(this._user.getSAPObject("Z_PutAwayItem"),
					new Class[] { User.class }, new Object[] { this._user });
			Z_GetPutAwayItemFetchBAPI bapi = (Z_GetPutAwayItemFetchBAPI) BAPIFactory.create(
					"Z_GetPutAwayItemFetchBAPI",
					new Class[] { User.class},
					new Object[] { this._user});

			bapi.run(obj);
			ArrayList<SAPObject> putawayitems = bapi.processResults();
			array = new Z_PutAwayItem[putawayitems.size()];
			array = (Z_PutAwayItem[]) (Z_PutAwayItem[]) SAPObjectArrayFactory.createSAPObjectArray(putawayitems,
					SAPObjectFactory.create(this._user, "Z_PutAwayItem"));
			this._log.debug("done");
			
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.safeExit(array);
		return array;
	}

}
