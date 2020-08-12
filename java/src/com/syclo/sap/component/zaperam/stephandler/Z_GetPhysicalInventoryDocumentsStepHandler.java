package com.syclo.sap.component.zaperam.stephandler;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.ConversionUtility;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectArrayFactory;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.User;
import com.syclo.sap.bapi.SystemInfoBAPI;
import com.syclo.sap.component.zaperam.bapi.Z_PhysicalInventoryFetchBAPI;
import com.syclo.sap.component.zaperam.object.PhysicalInventoryDocument;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Z_GetPhysicalInventoryDocumentsStepHandler extends com.syclo.sap.component.physicalinventory.stephandler.GetPhysicalInventoryDocumentsStepHandler{
	protected User _imUser = null;

	public Z_GetPhysicalInventoryDocumentsStepHandler() {
	}

	public Z_GetPhysicalInventoryDocumentsStepHandler(User user) {
		super(user);
		this._imUser = user;
	}

	public PhysicalInventoryDocument[] getPhysicalInventoryDocuments() throws AgentryException {
		this._log.entry();
		PhysicalInventoryDocument[] array = new PhysicalInventoryDocument[0];
		try {
			GregorianCalendar lastUpdate = ConversionUtility.getLastUpdatedDate(this._imUser);
			SystemInfoBAPI sysBapi = (SystemInfoBAPI) BAPIFactory.create("SystemInfoBAPI", new Class[] { User.class },
					new Object[] { this._imUser });
			sysBapi.run();
			GregorianCalendar bapiCallTime = sysBapi.getSystemTimeStamp();

			PhysicalInventoryDocument obj = (PhysicalInventoryDocument) SAPObjectFactory.create(
					this._imUser.getSAPObject("PhysicalInventoryDocument"), new Class[] { User.class },
					new Object[] { this._imUser });
			Z_PhysicalInventoryFetchBAPI bapi = (Z_PhysicalInventoryFetchBAPI) BAPIFactory.create(
					"PhysicalInventoryFetchBAPI",
					new Class[] { User.class, GregorianCalendar.class, GregorianCalendar.class },
					new Object[] { this._imUser, lastUpdate, bapiCallTime });

			bapi.run(obj);
			ArrayList<SAPObject> physicalinventorydocuments = bapi.processResults();
			array = new PhysicalInventoryDocument[physicalinventorydocuments.size()];
			array = (PhysicalInventoryDocument[]) (PhysicalInventoryDocument[]) SAPObjectArrayFactory
					.createSAPObjectArray(physicalinventorydocuments,
							SAPObjectFactory.create(this._imUser, "PhysicalInventoryDocument"));

			this._log.debug("done");
		} catch (Exception e) {
			this._imUser.rethrowException(e, true);
		}
		this._log.safeExit(array);
		return array;
	}

}
