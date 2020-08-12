package com.syclo.sap.component.zaperam.steplet;

import com.syclo.agentry.AgentryException;
import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.StepHandlerFactory;
import com.syclo.sap.Steplet;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.stephandler.Z_MaterialNewAdhocCountingPostStepHandler;

/*
 * This class is invoked from the Agentry steplet that post data to backend. It creates the
 * session and Invokes the Stephandler method.
 * */

public class Z_MaterialNewAdhocCountingPostSteplet extends Steplet{
	public Z_MaterialNewAdhocCountingPostSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public Z_MaterialNewAdhocCountingPostSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	public Z_MaterialNewAdhocCountingPostSteplet(PushSession session) throws AgentryException {
		super(session);
	}

	/*
	 * This Execute method will be called from Agentry step and passes the control to 
	 * Z_MaterialNewAdhocCountingPostStepHandler class.This method is overridden from the Steplet class.
	 * */

	public boolean execute() throws AgentryException {
		try {
			Z_MaterialNewAdhocCountingPostStepHandler handler = (Z_MaterialNewAdhocCountingPostStepHandler) StepHandlerFactory.create(
					StepHandlerFactory.getClassName("Z_MaterialNewAdhocCountingPostStepHandler"), new Class[] { User.class },
					new Object[] { this._user });

			handler.count();
			return true;
		} catch (Throwable e) {
			throwExceptionToClient(e);
		}
		return false;
	}

}
