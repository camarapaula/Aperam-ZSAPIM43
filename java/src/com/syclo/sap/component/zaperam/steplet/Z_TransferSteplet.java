package com.syclo.sap.component.zaperam.steplet;

import com.syclo.agentry.AgentryException;
import com.syclo.agentry.FetchSession;
import com.syclo.agentry.PushSession;
import com.syclo.agentry.TransactionSession;
import com.syclo.sap.StepHandlerFactory;
import com.syclo.sap.User;
import com.syclo.sap.component.goodsmovement.steplet.TransferSteplet;
import com.syclo.sap.component.zaperam.stephandler.Z_TransferStepHandler;

/*
 * This class is invoked from the Agentry steplet that post data to backend. It creates the
 * session and Invokes the Stephandler method.
 * */

public class Z_TransferSteplet extends TransferSteplet{
	public Z_TransferSteplet(TransactionSession session) throws AgentryException {
		super(session);
	}

	public Z_TransferSteplet(FetchSession session) throws AgentryException {
		super(session);
	}

	public Z_TransferSteplet(PushSession session) throws AgentryException {
		super(session);
	}

	/*
	 * This Execute method will be called from Agentry step and passes the control to 
	 * Z_TransferStepHandler class.This method is overridden from the Steplet class.
	 * */
	
	public boolean execute() throws AgentryException {
		try {
			Z_TransferStepHandler handler = (Z_TransferStepHandler) StepHandlerFactory.create(
					StepHandlerFactory.getClassName("Z_TransferStepHandler"), new Class[] { User.class },
					new Object[] { this._user });

			handler.transferItem();
			return true;
		} catch (Throwable e) {
			this._useNewErrorHandlingMethod = true;
			throwExceptionToClient(e);
		}
		return false;
	}

}
