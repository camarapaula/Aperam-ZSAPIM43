package com.syclo.sap.component.zaperam.stephandler;

import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_DeliveryDocumentsPostBAPI;

/*
 * This method will be invoked from the Steplet class - Z_DeliveryDocumentsPostSteplet and
 * will be used to invoke the BAPI class, Z_DeliveryDocumentsPostBAPI,
 * BAPI instance is created here and the standard run method is called.
 */

public class Z_DeliveryDocumentsPostStepHandler extends StepHandler{
	public Z_DeliveryDocumentsPostStepHandler(User user) {
		super(user);
	}

	public void run() throws AgentryException {
		try {
			Z_DeliveryDocumentsPostBAPI bapi = (Z_DeliveryDocumentsPostBAPI) BAPIFactory.create(
					"Z_DeliveryDocumentsPostBAPI", new Class[] { User.class, GregorianCalendar.class },
					new Object[] { this._user, new GregorianCalendar() });

			bapi.run(null);
			bapi.processResults();

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}

	}

}
