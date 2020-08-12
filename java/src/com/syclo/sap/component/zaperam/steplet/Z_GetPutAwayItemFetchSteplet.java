package com.syclo.sap.component.zaperam.steplet;

import com.syclo.agentry.AgentryException;
import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.StepHandlerFactory;
import com.syclo.sap.Steplet;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.stephandler.Z_GetPutAwayItemFetchStepHandler;


/*
 * This class is invoked from the Agentry steplet that fetches data from backend. It creates the
 * session and Invokes the Stephandler method.
 * */

public class Z_GetPutAwayItemFetchSteplet extends Steplet{
	public Z_GetPutAwayItemFetchSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public Z_GetPutAwayItemFetchSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	public Z_GetPutAwayItemFetchSteplet(PushSession session) throws AgentryException {
		super(session);
	}
	
/*
 * This Execute method will be called from Agentry step and passes the control to 
 * Z_GetPutAwayItemFetchStepHandler class.This method is overridden from the Steplet class.
 * */
	
	public boolean execute() throws AgentryException {
		try {
			Z_GetPutAwayItemFetchStepHandler handler = (Z_GetPutAwayItemFetchStepHandler) StepHandlerFactory.create(
					StepHandlerFactory.getClassName("Z_GetPutAwayItemFetchStepHandler"), new Class[] { User.class },
					new Object[] { this._user });

			this._returnData = handler.getLists();
			return true;
		} catch (Throwable e) {
			throwExceptionToClient(e);
		}
		return false;
	}

}
