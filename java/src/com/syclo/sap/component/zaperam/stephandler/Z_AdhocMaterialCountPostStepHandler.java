package com.syclo.sap.component.zaperam.stephandler;

import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_AdhocMaterialCountPostBAPI;
import com.syclo.sap.component.zaperam.object.Material;

/*This Step handler class is called by the Z_AdhocMaterialCountPostSteplet class.
* This Step handler class is utilized to invoke the BAPI class. 
* BAPI instance is created here and the standard run method is called.
* */

/*
 * This method will be invoked from the Steplet class - Z_AdhocMaterialCountPostSteplet and
 * will be used to invoke the BAPI class, Z_AdhocMaterialCountPostBAPI,
 * Creates the SAPObject instance for the Material object.
 */
public class Z_AdhocMaterialCountPostStepHandler extends StepHandler {
	public Z_AdhocMaterialCountPostStepHandler(User user) {
		super(user);
	}

	public void count() throws AgentryException {
		try {
			Material material = (Material) SAPObjectFactory.create(
					this._user.getSAPObject("Material"), new Class[] { User.class },
					new Object[] { this._user });

			Z_AdhocMaterialCountPostBAPI bapi = (Z_AdhocMaterialCountPostBAPI) BAPIFactory.create(
						"Z_AdhocMaterialCountPostBAPI",
						new Class[] { User.class, GregorianCalendar.class, Material.class },
						new Object[] { this._user, new GregorianCalendar(), material });

				bapi.run(material);
				bapi.processResults();

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}

	}
}
