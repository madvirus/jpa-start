package jpastart.common;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Converter
public class InetAddressConverter
        implements AttributeConverter<InetAddress, String> {
    @Override
    public String convertToDatabaseColumn(InetAddress attribute) {
        if (attribute == null)
            return null;
        else
            return attribute.getHostAddress();
    }

    @Override
    public InetAddress convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return null;
        try {
            return InetAddress.getByName(dbData);
        } catch (UnknownHostException e) {
            throw new RuntimeException(
                    String.format(
                            "InetAddressConverter fail to convert %s to InetAddress: %s",
                            dbData, e.getMessage()),
                    e);
        }
    }
}