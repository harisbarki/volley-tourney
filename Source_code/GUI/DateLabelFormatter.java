package GUI;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;
import java.text.ParseException;


public class DateLabelFormatter extends AbstractFormatter
{
    private String datePattern = "dd/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException 
    {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException
    {
        if (value != null)
        {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
    
	/**
	 * This method converts date into different format and return string
	 * 
	 * @param LocalDate
	 *            local date time 
	 * @return returns string of localdate time formatted
	 */
    public static String toStringFormat(LocalDate date) {
		return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date);
    }

}