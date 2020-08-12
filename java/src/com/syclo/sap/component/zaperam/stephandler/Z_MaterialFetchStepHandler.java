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
import com.syclo.sap.component.zaperam.bapi.Z_MaterialFetchBAPI;
import com.syclo.sap.component.zaperam.object.OrderItem;

/*This Step handler class is called by the Z_MaterialFetchSteplet class.
* This Step handler class is utilized to invoke the BAPI class. 
* BAPI instance is created here and the standard run method is called.
* */

public class Z_MaterialFetchStepHandler extends StepHandler {
	public Z_MaterialFetchStepHandler() {
	}

	public Z_MaterialFetchStepHandler(User user) {
		super(user);
	}

	/*
	 * This method will be used to invoke the BAPI class, Z_MaterialFetchBAPI,
	 * Creates the SAPObject instance for the Z_OrderItem object.
	 */
	
	public OrderItem[] getMaterials() throws AgentryException {
		this._log.entry();
		OrderItem[] array = new OrderItem[0];
		try {
			SystemInfoBAPI sysBapi = (SystemInfoBAPI) BAPIFactory.create("SystemInfoBAPI", new Class[] { User.class },
					new Object[] { this._user });
			sysBapi.run();
			GregorianCalendar bapiCallTime = sysBapi.getSystemTimeStamp();
			Z_MaterialFetchBAPI bapi = (Z_MaterialFetchBAPI) BAPIFactory.create(
					"Z_MaterialFetchBAPI",
					new Class[] { User.class, GregorianCalendar.class},
					new Object[] { this._user,  bapiCallTime});

			bapi.run(null);
			ArrayList<SAPObject> materials = bapi.processResults();
			array = new OrderItem[materials.size()];
			array = (OrderItem[]) (OrderItem[]) SAPObjectArrayFactory.createSAPObjectArray(materials,
					SAPObjectFactory.create(this._user, "Z_OrderItem"));
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.safeExit(array);
		return array;
	}

}
