package com.syclo.sap.component.zaperam.stephandler;

import java.util.ArrayList;
import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_PickingListsFetchBAPI;
import com.syclo.sap.component.zaperam.object.Z_PickingList;

/*This Step handler class is called by the  Z_GetPickingListsSteplet class.
* This Step handler class is utilized to invoke the BAPI class. 
* BAPI instance is created here and the standard run method is called.
* */

public class Z_GetPickingListsStepHandler extends StepHandler{
	
	/*
	* Default Constructor definition for the class
	* */
	
	public Z_GetPickingListsStepHandler() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public Z_GetPickingListsStepHandler(User user) {
		super(user);
	}
	
	/*
	 * This method will be used to invoke the BAPI class, Z_PickingListsFetchBAPI,
	 * Creates the SAPObject instance for the Z_PickingList object.
	 */

	public Z_PickingList[] getLists() throws AgentryException {
		this._log.entry();
		Z_PickingList[] array = new Z_PickingList[0];
		try {
			Z_PickingList obj = (Z_PickingList) SAPObjectFactory.create(this._user.getSAPObject("Z_PickingList"),
					new Class[] { User.class }, new Object[] { this._user });
			Z_PickingListsFetchBAPI bapi = (Z_PickingListsFetchBAPI) BAPIFactory.create(
					"Z_PickingListsFetchBAPI",
					new Class[] { User.class},
					new Object[] { this._user});

			bapi.run(obj);
			ArrayList<SAPObject> pickinglists = bapi.processResults();
			array = new Z_PickingList[pickinglists.size()];
			array = (Z_PickingList[]) (Z_PickingList[]) SAPObjectArrayFactory.createSAPObjectArray(pickinglists,
					SAPObjectFactory.create(this._user, "Z_PickingList"));
			this._log.debug("done");
			
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.safeExit(array);
		return array;
	}

}
