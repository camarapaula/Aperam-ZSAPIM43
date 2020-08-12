package com.syclo.sap.component.zaperam.stephandler;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.bapi.SystemInfoBAPI;
import com.syclo.sap.component.zaperam.bapi.Z_PurchaseOrderFetchBAPI;
import com.syclo.sap.component.zaperam.object.Order;

/*This Step handler class is called by the  Z_PurchaseOrderFetchSteplet class.
* This Step handler class is utilized to invoke the BAPI class. 
* BAPI instance is created here and the standard run method is called.
* */

public class Z_PurchaseOrderFetchStepHandler extends StepHandler {
	
	/*
	* Default Constructor definition for the class
	* */
	
	public Z_PurchaseOrderFetchStepHandler() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public Z_PurchaseOrderFetchStepHandler(User user) {
		super(user);
	}

	/*
	 * This method will be used to invoke the BAPI class, Z_PurchaseOrderFetchBAPI,
	 * Creates the SAPObject instance for the Order object.
	 */
	
	public Order[] getDocs() throws AgentryException {
		this._log.entry();
		Order[] array = new Order[0];
		try {
			SystemInfoBAPI sysBapi = (SystemInfoBAPI) BAPIFactory.create("SystemInfoBAPI", new Class[] { User.class },
					new Object[] { this._user });
			sysBapi.run();
			GregorianCalendar bapiCallTime = sysBapi.getSystemTimeStamp();
			Z_PurchaseOrderFetchBAPI bapi = (Z_PurchaseOrderFetchBAPI) BAPIFactory.create(
					"Z_PurchaseOrderFetchBAPI",
					new Class[] { User.class, GregorianCalendar.class},
					new Object[] { this._user,  bapiCallTime});
			bapi.run(null);
			ArrayList<SAPObject> docs = bapi.processResults();
			array = new Order[docs.size()];
			array = (Order[]) (Order[]) SAPObjectArrayFactory.createSAPObjectArray(docs,
					SAPObjectFactory.create(this._user, "Z_Order"));
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.safeExit(array);
		return array;
	}

}

