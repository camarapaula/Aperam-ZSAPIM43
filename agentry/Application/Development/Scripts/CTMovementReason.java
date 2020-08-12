package com.syclo.sap.mm.table;


//java classes
import java.util.GregorianCalendar;

//Agentry Classes
import com.syclo.agentry.ComplexTableSession;
import com.syclo.agentry.AgentryException;

//Agentry SAP Classes
import com.syclo.sap.mm.object.MovementReason;
import com.syclo.sap.mm.stephandler.CTMovementReasonStepHandler;

public class CTMovementReason extends com.syclo.sap.ComplexTable 
{
	public CTMovementReason(ComplexTableSession session, GregorianCalendar clientLastUpdate) throws Exception
	{
		super(session, clientLastUpdate);	
		MovementReason obj = new MovementReason();
		String bapiName = "/SYCLO/MM_CTMOVEMENTREASON_GET";
		_stepHandler = new CTMovementReasonStepHandler(this, _shortClassName, bapiName, obj,  _clientLastDataUpdateTime, _rebuilding);
	}
	
	public void build() throws AgentryException
	{
		super.build();	
		if (_tableCheck) {
			try {
				_stepHandler.build();
				setNewDataUpdateTime(_newLastUpdate);
			}
			catch (Exception e) {
				_user.rethrowException(e, true);
			}		
		}
	}

	public boolean willRebuildTable() throws AgentryException
	{
		// override this if you want custom reload logic for this table
		boolean retVal = super.willRebuildTable();
		return retVal;
	}
}