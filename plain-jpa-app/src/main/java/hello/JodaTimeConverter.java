package hello;

import java.util.Calendar;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.joda.time.DateTime;

/*
 * eclipselink has another interface called 'Converter', here we
 * use the 'standard' one
 * 
 * maybe type arguments of AttributeConverter can be omitted.
 * but currently, if omit, hibernate raises an error
 */
@Converter
public class JodaTimeConverter implements AttributeConverter<DateTime, Calendar>{

    public Calendar convertToDatabaseColumn(DateTime attribute) {
        if (attribute == null) {
            return null;
        }
        
        DateTime joda = (DateTime) attribute;
        Calendar date = Calendar.getInstance();
        date.set(joda.getYear(), joda.getMonthOfYear(), joda.getDayOfMonth(), 
                joda.getHourOfDay(), joda.getMinuteOfHour(), joda.getSecondOfMinute());
        date.set(Calendar.MILLISECOND, joda.getMillisOfSecond());
        return date;
    }

    public DateTime convertToEntityAttribute(Calendar dbData) {
        if (dbData == null) {
            return null;
        }
        
        Calendar date = (Calendar) dbData;
        return new DateTime(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1,
                date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY),
                date.get(Calendar.MINUTE), date.get(Calendar.SECOND), date.get(Calendar.MILLISECOND));
    }

}
