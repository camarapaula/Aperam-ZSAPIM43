package com.syclo.sap.component.zaperam.stephandler;

import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_MaterialLabelPostBAPI;

/*This Step handler class is called by the Z_MaterialLabelPostSteplet class.
* This Step handler class is utilized to invoke the BAPI class,Z_MaterialLabelPostBAPI,
* BAPI instance is created here and the standard run method is called.
* */

public class Z_MaterialLabelPostStepHandler extends StepHandler{
	public Z_MaterialLabelPostStepHandler(User user) {
		super(user);
	}

	public void run() throws AgentryException {
		try {
			Z_MaterialLabelPostBAPI bapi = (Z_MaterialLabelPostBAPI) BAPIFactory.create(
					"Z_MaterialLabelPostBAPI", new Class[] { User.class, GregorianCalendar.class },
					new Object[] { this._user, new GregorianCalendar() });

			bapi.run(null);
			bapi.processResults();

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}

	}

}
