package com.hw.tobcore.filter;

import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.data.util.ParsingUtils;

import java.util.Iterator;
import java.util.List;

public class UpperCaseWithUnderscoreFieldNamingStrategy implements FieldNamingStrategy {
    @Override
    public String getFieldName(PersistentProperty<?> property) {
        //有现成的工具，何乐不为
        List<String> parts = ParsingUtils.splitCamelCaseToLower(property.getName());
        StringBuilder sb = new StringBuilder();
        Iterator it = parts.iterator();
        if (it.hasNext()) {
            sb.append(it.next().toString().toLowerCase());//按需要，转成大写。
            while (it.hasNext()) {
                sb.append("_");
                sb.append(it.next().toString().toLowerCase());//按需要，转成大写。
            }
        }
        return sb.toString();
    }

}
