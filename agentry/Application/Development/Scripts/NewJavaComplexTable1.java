import java.util.GregorianCalendar;
import Syclo.Agentry.SessionData;
import Syclo.Agentry.ComplexTable;
import Syclo.Agentry.ComplexTableIterator;

public class NewJavaComplexTable1 extends ComplexTable 
{
	public NewJavaComplexTable1(SessionData sessionData, GregorianCalendar clientLastUpdate) 
	{
		super(sessionData, clientLastUpdate);

		// TODO: add your code here
	}

	// TODO: override initialize() if necessary
	// TODO: override reload() if necessary

	public ComplexTableIterator dataIterator()
	{
		// TODO: return your iterator type here
		return new ComplexTableIterator();
	}

	public ComplexTableIterator deleteIterator()
	{
		// TODO: return your iterator type here
		return new ComplexTableIterator();
	}
}
