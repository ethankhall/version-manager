package io.ehdev.conrad.database.internal;

import io.ehdev.conrad.backend.version.commit.CommitVersion;
import io.ehdev.conrad.backend.version.commit.VersionFactory;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;

public class CommitVersionConverter implements AttributeConverter<CommitVersion,String> {
    @Override
    public String convertToDatabaseColumn(CommitVersion attribute) {
        if(attribute == null) {
            return null;
        }
        return attribute.toVersionString();
    }

    @Override
    public CommitVersion convertToEntityAttribute(String dbData) {
        if(StringUtils.isBlank(dbData)) {
            return null;
        }
        return VersionFactory.parse(dbData);
    }
}
