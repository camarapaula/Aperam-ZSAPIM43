package com.syclo.sap.component.zaperam.steplet;

/*
 * This class is invoked from the Agentry steplet that fetches data from backend. It creates the
 * session and Invokes the Stephandler method.
 * */

import com.syclo.agentry.AgentryException;
import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.sap.Steplet;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.StepHandlerFactory;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.stephandler.Z_GetPickingListsStepHandler;

public class Z_GetPickingListsSteplet extends Steplet{
	public Z_GetPickingListsSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public Z_GetPickingListsSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	public Z_GetPickingListsSteplet(PushSession session) throws AgentryException {
		super(session);
	}
	
	/*
	 * This Execute method will be called from Agentry step and passes the control to 
	 * Z_GetPickingListsStepHandler class.This method is overridden from the Steplet class.
	 * */

	public boolean execute() throws AgentryException {
		try {
			Z_GetPickingListsStepHandler handler = (Z_GetPickingListsStepHandler) StepHandlerFactory.create(
					StepHandlerFactory.getClassName("Z_GetPickingListsStepHandler"), new Class[] { User.class },
					new Object[] { this._user });

			this._returnData = handler.getLists();
			return true;
		} catch (Throwable e) {
			this._useNewErrorHandlingMethod = false;
			throwExceptionToClient(e);
		}
		return false;
	}

}
