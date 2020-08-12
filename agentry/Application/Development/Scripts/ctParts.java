package com.syclo.sap.mm.table;

//java classes
import java.util.GregorianCalendar;

//Agentry Classes
import com.syclo.agentry.ComplexTableSession;
import com.syclo.agentry.AgentryException;


//Agentry SAP Classes
import com.syclo.sap.mm.object.Part;
import com.syclo.sap.mm.stephandler.CTPartsStepHandler;


public class CTWarehouseParts extends com.syclo.sap.ComplexTable 
{
	public CTWarehouseParts(ComplexTableSession session, GregorianCalendar clientLastUpdate) throws Exception
	{
		super(session, clientLastUpdate);
		Part obj = new Part();
		String bapiName = "/SYCLO/MM_CTMATPLANT_GET";
		_stepHandler = new CTPartsStepHandler(this, _shortClassName, bapiName, obj,  _clientLastDataUpdateTime, _rebuilding);
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