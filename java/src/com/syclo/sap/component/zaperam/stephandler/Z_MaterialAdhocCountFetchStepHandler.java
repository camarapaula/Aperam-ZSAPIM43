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
import com.syclo.sap.component.zaperam.object.Material;
import com.syclo.sap.component.zaperam.bapi.Z_MaterialAdhocCountFetchBAPI;

/*This Step handler class is called by the Z_MaterialAdhocCountFetchSteplet class.
* This Step handler class is utilized to invoke the BAPI class. 
* BAPI instance is created here and the standard run method is called.
* */

public class Z_MaterialAdhocCountFetchStepHandler extends StepHandler {
	
	/*
	* Default Constructor definition for the class
	* */
	
	public Z_MaterialAdhocCountFetchStepHandler() {
	}

	/*
	* Parameterized Constructor definition for the class
	* */
	
	public Z_MaterialAdhocCountFetchStepHandler(User user) {
		super(user);
	}

	/*
	 * This method will be used to invoke the BAPI class, Z_MaterialAdhocCountFetchBAPI,
	 * Creates the SAPObject instance for the Material object.
	 */

	public Material[] getMaterials() throws AgentryException {
		this._log.entry();
		Material[] array = new Material[0];
		try {
			SystemInfoBAPI sysBapi = (SystemInfoBAPI) BAPIFactory.create("SystemInfoBAPI", new Class[] { User.class },
					new Object[] { this._user });
			sysBapi.run();
			GregorianCalendar bapiCallTime = sysBapi.getSystemTimeStamp();
			Material obj = (Material) SAPObjectFactory.create(this._user.getSAPObject("Material"),
					new Class[] { User.class }, new Object[] { this._user });
			Z_MaterialAdhocCountFetchBAPI bapi = (Z_MaterialAdhocCountFetchBAPI) BAPIFactory.create(
					"Z_MaterialAdhocCountFetchBAPI",
					new Class[] { User.class, GregorianCalendar.class},
					new Object[] { this._user,  bapiCallTime});

			bapi.run(obj);
			ArrayList<SAPObject> materials = bapi.processResults();
			array = new Material[materials.size()];
			array = (Material[]) (Material[]) SAPObjectArrayFactory.createSAPObjectArray(materials,
					SAPObjectFactory.create(this._user, "Material"));
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.safeExit(array);
		return array;
	}

}
