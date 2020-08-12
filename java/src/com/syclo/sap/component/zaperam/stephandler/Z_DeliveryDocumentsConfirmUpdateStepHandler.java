package com.syclo.sap.component.zaperam.stephandler;

import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_DeliveryDocumentsConfirmUpdateBAPI;

/*
 * This method will be invoked from the Steplet class - Z_DeliveryDocumentsConfirmUpdateSteplet and
 * will be used to invoke the BAPI class, Z_DeliveryDocumentsConfirmUpdateBAPI,
 * BAPI instance is created here and the standard run method is called.
 */

public class Z_DeliveryDocumentsConfirmUpdateStepHandler extends StepHandler{
	public Z_DeliveryDocumentsConfirmUpdateStepHandler(User user) {
		super(user);
	}

	public void run() throws AgentryException {
		try {
			Z_DeliveryDocumentsConfirmUpdateBAPI bapi = (Z_DeliveryDocumentsConfirmUpdateBAPI) BAPIFactory.create(
					"Z_DeliveryDocumentsConfirmUpdateBAPI", new Class[] { User.class, GregorianCalendar.class },
					new Object[] { this._user, new GregorianCalendar() });

			bapi.run(null);
			bapi.processResults();

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}

	}

}

