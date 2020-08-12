package com.syclo.sap.component.zaperam.steplet;

import com.syclo.agentry.AgentryException;
import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.sap.StepHandlerFactory;
import com.syclo.sap.Steplet;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.stephandler.Z_MaterialAdhocCountFetchStepHandler;

/*
 * This class is invoked from the Agentry steplet that fetches data from backend. It creates the
 * session and Invokes the Stephandler method.
 * */

public class Z_MaterialAdhocCountFetchSteplet extends Steplet{
	public Z_MaterialAdhocCountFetchSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public Z_MaterialAdhocCountFetchSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	public Z_MaterialAdhocCountFetchSteplet(PushSession session) throws AgentryException {
		super(session);
	}
	
	/*
	 * This Execute method will be called from Agentry step and passes the control to 
	 * Z_MaterialAdhocCountFetchStepHandler class.This method is overridden from the Steplet class.
	 * */

	@Override
	public boolean execute() throws AgentryException {
		try {
			Z_MaterialAdhocCountFetchStepHandler handler = (Z_MaterialAdhocCountFetchStepHandler) StepHandlerFactory
					.create(StepHandlerFactory.getClassName("Z_MaterialAdhocCountFetchStepHandler"),
							new Class[] { User.class }, new Object[] { this._user });

			this._returnData = handler.getMaterials();
			return true;
		} catch (Throwable e) {
			throwExceptionToClient(e);
		}
		return false;
	}

}
