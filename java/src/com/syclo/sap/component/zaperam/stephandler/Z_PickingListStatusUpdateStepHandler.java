package com.syclo.sap.component.zaperam.stephandler;

import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_PickingListStatusUpdateBAPI;


/*This Step handler class is called by the Z_PickingListStatusUpdateSteplet class.
* This Step handler class is utilized to invoke the BAPI class,Z_PickingListStatusUpdateBAPI, 
* BAPI instance is created here and the standard run method is called.
* */

public class Z_PickingListStatusUpdateStepHandler extends StepHandler {
	public Z_PickingListStatusUpdateStepHandler(User user) {
		super(user);
	}

	public void run() throws AgentryException {
		try {
			Z_PickingListStatusUpdateBAPI bapi = (Z_PickingListStatusUpdateBAPI) BAPIFactory.create(
					"Z_PickingListStatusUpdateBAPI", new Class[] { User.class, GregorianCalendar.class },
					new Object[] { this._user, new GregorianCalendar() });

			bapi.run(null);
			bapi.processResults();

		} catch (Exception e) {
			this._user.rethrowException(e, true);			
		}

	}

}
