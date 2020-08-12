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
import com.syclo.sap.component.zaperam.bapi.Z_DeliveryDocumentsFetchBAPI;
import com.syclo.sap.component.zaperam.object.GoodsMovementDocument;

/*
 * This method will be invoked from the Steplet class - Z_DeliveryDocumentsFetchSteplet and
 * will be used to invoke the BAPI class, Z_DeliveryDocumentsFetchBAPI,
 * Creates the SAPObject instance for the GoodsMovementDocument object.
 */

public class Z_DeliveryDocumentsFetchStepHandler extends StepHandler {
	public Z_DeliveryDocumentsFetchStepHandler() {
	}

   /*
	* Parameterized Constructor definition for the class
	* */
	
	public Z_DeliveryDocumentsFetchStepHandler(User user) {
		super(user);
	}

	/*
	 * This method will be used to invoke the BAPI class, Z_DeliveryDocumentsFetchBAPI,
	 * Creates the SAPObject instance for the GoodsMovementDocument object.
	 */
	
	public GoodsMovementDocument[] getDocs() throws AgentryException {
		this._log.entry();
		GoodsMovementDocument[] array = new GoodsMovementDocument[0];
		try {
			SystemInfoBAPI sysBapi = (SystemInfoBAPI) BAPIFactory.create("SystemInfoBAPI", new Class[] { User.class },
					new Object[] { this._user });
			sysBapi.run();
			GregorianCalendar bapiCallTime = sysBapi.getSystemTimeStamp();
			Z_DeliveryDocumentsFetchBAPI bapi = (Z_DeliveryDocumentsFetchBAPI) BAPIFactory.create(
					"Z_DeliveryDocumentsFetchBAPI",
					new Class[] { User.class, GregorianCalendar.class},
					new Object[] { this._user,  bapiCallTime});

			bapi.run(null);
			ArrayList<SAPObject> docs = bapi.processResults();
			array = new GoodsMovementDocument[docs.size()];
			array = (GoodsMovementDocument[]) (GoodsMovementDocument[]) SAPObjectArrayFactory.createSAPObjectArray(docs,
					SAPObjectFactory.create(this._user, "Z_GoodsMovementDocument"));
			this._log.debug("done");
		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}
		this._log.safeExit(array);
		this._log.info("BRL: Array Stephandler: " + String.valueOf(array.length));
		for (int i = 0; i < array.length; i++) {
			this._log.info("Values for entry (" + String.valueOf(i) + "):");
			for (int j = 0; j < array[i].getItems().length; j++) {
				this._log.info("BRL - ComponentID for (" + String.valueOf(j) + ") : " + array[i].getItems()[j].getComponentID());
				this._log.info("BRL - Z_ReservationItem for (" + String.valueOf(j) + ") : " + array[i].getItems()[j].getZ_ReservationItem());
			}
		}
		return array;
	}

}
