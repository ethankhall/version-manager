package io.ehdev.version.commit.model;

import io.ehdev.version.commit.CommitVersion;
import io.ehdev.version.commit.internal.DefaultCommitVersion;
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
        return DefaultCommitVersion.parse(dbData);
    }
}
