package com.syclo.sap.mm.table;

//java Classes
import java.util.GregorianCalendar;

import com.syclo.agentry.ComplexTableSession;
import com.syclo.agentry.AgentryException;

//Agentry SAP Classes
import com.syclo.sap.mm.object.Plant;
import com.syclo.sap.mm.stephandler.CTPlantStepHandler;

public class CTPlant extends com.syclo.sap.ComplexTable 
{

	public CTPlant(ComplexTableSession session, GregorianCalendar clientLastUpdate) throws Exception
	{
		super(session, clientLastUpdate);
		Plant obj = new Plant();
		String bapiName = "/SYCLO/MM_CTPLANT_GET";
		_stepHandler = new CTPlantStepHandler(this, _shortClassName, bapiName, obj, _clientLastDataUpdateTime, _rebuilding);
	}
	
	public void build() throws AgentryException
	{
		super.build();
		if (_tableCheck){
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