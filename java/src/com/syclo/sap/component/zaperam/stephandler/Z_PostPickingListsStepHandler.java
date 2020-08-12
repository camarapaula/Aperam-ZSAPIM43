package com.syclo.sap.component.zaperam.stephandler;

import java.util.GregorianCalendar;

import com.syclo.agentry.AgentryException;
import com.syclo.sap.BAPIFactory;
import com.syclo.sap.SAPObject;
import com.syclo.sap.SAPObjectFactory;
import com.syclo.sap.StepHandler;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.bapi.Z_PostPickingListsBAPI;
import com.syclo.sap.component.zaperam.object.Z_PickingListItem;

/*
 * This method will be invoked from the Steplet class - Z_PostPickingListsSteplet and
 * will be used to invoke the BAPI class, Z_PostPickingListsBAPI,
 * BAPI instance is created here and the standard run method is called.
 */

public class Z_PostPickingListsStepHandler extends StepHandler{
	public Z_PostPickingListsStepHandler(User user) {
		super(user);
	}
	public String IsDeliveryFlag = "";

	/*
	 * This method will be invoked from the Steplet class - Z_PostPickingListsSteplet and
	 * will be used to invoke the BAPI class, Z_PostPickingListsBAPI,
	 * Creates the SAPObject instance for the Z_PickingListItem object.
	 */
	
	public void run() throws AgentryException {
		try {
			/*Z_PickingList _pla = (Z_PickingList) SAPObjectFactory.create(
					this._user.getSAPObject("Z_PickingList"), new Class[] { User.class },
					new Object[] { this._user });
			String prefix = "transaction.";
			if(_user.getBoolean(prefix + "IsDelivery"))
			{
			_pla.setIsDelivery("X");
			IsDeliveryFlag = "X";
			}
			//_pla.setProperties(_user, this);*/
			
			Z_PickingListItem _pl = (Z_PickingListItem) SAPObjectFactory.create(
					this._user.getSAPObject("Z_PickingListItem"), new Class[] { User.class },
					new Object[] { this._user });
			_pl.setProperties(_user, this);
			Z_PostPickingListsBAPI bapi = (Z_PostPickingListsBAPI) BAPIFactory.create(
					"Z_PostPickingListsBAPI", new Class[] { User.class, GregorianCalendar.class, SAPObject.class },
					new Object[] { this._user, new GregorianCalendar(), _pl });

			bapi.run(_pl);
			bapi.processResults();

		} catch (Exception e) {
			this._user.rethrowException(e, true);
		}

	}


}
