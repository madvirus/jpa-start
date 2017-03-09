package jpastart.common;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.math.BigDecimal;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, String> {
    @Override
    public String convertToDatabaseColumn(Money attribute) {
        if (attribute == null)
            return null;
        else
            return attribute.toString();
    }

    @Override
    public Money convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;
        else {
            String valueStr = dbData.substring(0, dbData.length() - 3);
            String currency = dbData.substring(dbData.length() - 3);
            BigDecimal value = BigDecimal.valueOf(Double.parseDouble(valueStr));
            return new Money(value, currency);
        }
    }
}
