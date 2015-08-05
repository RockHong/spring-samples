package hello;

import java.util.Calendar;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.joda.time.DateTime;

/*
 * eclipselink has another interface called 'Converter', here we
 * use the 'standard' one
 */
@Converter
public class JodaTimeConverter implements AttributeConverter{

    public Object convertToDatabaseColumn(Object attribute) {
        if (attribute == null) {
            return null;
        }
        
        if (attribute instanceof DateTime) {
            DateTime joda = (DateTime) attribute;
            Calendar date = Calendar.getInstance();
            date.set(joda.getYear(), joda.getMonthOfYear(), joda.getDayOfMonth(), 
                    joda.getHourOfDay(), joda.getMinuteOfHour(), joda.getSecondOfMinute());
            date.set(Calendar.MILLISECOND, joda.getMillisOfSecond());
            return date;
        }
        throw new IllegalArgumentException("the object to be converted is not org.joda.time.DateTime");
    }

    public Object convertToEntityAttribute(Object dbData) {
        if (dbData == null) {
            return null;
        }
        
        if (dbData instanceof Calendar) {
            Calendar date = (Calendar) dbData;
            return new DateTime(date.get(Calendar.YEAR), date.get(Calendar.MONTH) + 1,
                    date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY),
                    date.get(Calendar.MINUTE), date.get(Calendar.SECOND), date.get(Calendar.MILLISECOND));
        }
        throw new IllegalArgumentException("the object to be converted is not java.util.Calendar");
    }

}
