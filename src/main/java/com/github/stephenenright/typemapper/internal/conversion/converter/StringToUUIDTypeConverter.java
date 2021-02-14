package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.util.UUID;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.internal.util.StringUtils;

public class StringToUUIDTypeConverter implements TypeConverter<String, UUID> {

	public static final StringToUUIDTypeConverter INSTANCE = new StringToUUIDTypeConverter();
	
	@Override
	public UUID convert(TypeMappingContext<String, UUID> context) {
	    String value = context.getSource();

        if (value == null) {
            return null;
        }
	    
	    
	    if(StringUtils.isNullOrEmpty(value)) {
			return null;
		}
		
		return UUID.fromString(value.trim());
	}
}
