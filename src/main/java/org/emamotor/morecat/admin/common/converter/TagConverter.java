package org.emamotor.morecat.admin.common.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yoshimasa Tanabe
 */
@FacesConverter("tagConverter")
public class TagConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {

        Set<String> tags = new HashSet<>();
        for (String each : value.split(",")) {
            tags.add(each.trim().replace(' ', '_'));
        }
        return tags;

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {

        String bracketedTags = value.toString();
        return bracketedTags.substring(1, bracketedTags.length() - 1);  // remove bracket

    }

}
