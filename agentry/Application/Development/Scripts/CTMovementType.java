package com.syclo.sap.mm.table;

//java classes
import java.util.GregorianCalendar;

//Agentry Classes
import com.syclo.agentry.ComplexTableSession;
import com.syclo.agentry.AgentryException;

//Agentry SAP Classes
import com.syclo.sap.mm.object.MovementType;
import com.syclo.sap.mm.stephandler.CTMovementTypeStepHandler;

public class CTMovementType extends com.syclo.sap.ComplexTable 
{	
	public CTMovementType(ComplexTableSession session, GregorianCalendar clientLastUpdate) throws Exception
	{
		super(session, clientLastUpdate);
		MovementType obj = new MovementType();
		String bapiName = "/SYCLO/MM_CTMOVEMENTTYPE_GET";
		_stepHandler = new CTMovementTypeStepHandler(this, _shortClassName, bapiName, obj, _clientLastDataUpdateTime, _rebuilding);
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