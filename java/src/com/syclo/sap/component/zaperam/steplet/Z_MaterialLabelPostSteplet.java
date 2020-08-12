package com.syclo.sap.component.zaperam.steplet;

import com.syclo.agentry.AgentryException;
import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.StepHandlerFactory;
import com.syclo.sap.Steplet;
import com.syclo.sap.User;
import com.syclo.sap.component.zaperam.stephandler.Z_MaterialLabelPostStepHandler;

/*
 * This class is invoked from the Agentry steplet that post data to backend. It creates the
 * session and Invokes the Stephandler method.
 * */

public class Z_MaterialLabelPostSteplet extends Steplet{
	public Z_MaterialLabelPostSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public Z_MaterialLabelPostSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	public Z_MaterialLabelPostSteplet(PushSession session) throws AgentryException {
		super(session);
	}

	/*
	 * This Execute method will be called from Agentry step and passes the control to 
	 * Z_MaterialLabelPostStepHandler class.This method is overridden from the Steplet class.
	 * */
	
	public boolean execute() throws AgentryException {
		try {
			Z_MaterialLabelPostStepHandler handler = (Z_MaterialLabelPostStepHandler) StepHandlerFactory.create(
					StepHandlerFactory.getClassName("Z_MaterialLabelPostStepHandler"), new Class[] { User.class },
					new Object[] { this._user });

			handler.run();
			return true;
		} catch (Throwable e) {
			this._useNewErrorHandlingMethod = true;
			throwExceptionToClient(e);
		}
		return false;
	}


}
