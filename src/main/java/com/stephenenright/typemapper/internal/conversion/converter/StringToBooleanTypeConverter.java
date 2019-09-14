package com.stephenenright.typemapper.internal.conversion.converter;

import java.util.HashSet;
import java.util.Set;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.util.StringUtils;

public class StringToBooleanTypeConverter implements TypeConverter<String, Boolean> {

	public static final StringToBooleanTypeConverter INSTANCE = new StringToBooleanTypeConverter();

	private static final Set<String> trueValueSet = new HashSet<>(4);

	private static final Set<String> falseValueSet = new HashSet<>(4);

	static {
		trueValueSet.add("true");
		trueValueSet.add("yes");
		trueValueSet.add("on");
		trueValueSet.add("1");

		falseValueSet.add("false");
		falseValueSet.add("no");
		falseValueSet.add("off");
		falseValueSet.add("0");
	}

	@Override
	public Boolean convert(TypeMappingContext<String, Boolean> context) {
	    String value = context.getSource();
        if (value == null) {
            return null;
        }

	    if(StringUtils.isNullOrEmpty(value)) {
			return null;
		}
		
		final String sanitizedValue = value.trim().toLowerCase();

		if (sanitizedValue.isEmpty()) {
			return null;
		}
		
		if (falseValueSet.contains(sanitizedValue)) {
			return Boolean.FALSE;
		}
		else if (trueValueSet.contains(sanitizedValue)) {
			return Boolean.TRUE;
		}
		else {
			return null;
		}	
	}
		
}
