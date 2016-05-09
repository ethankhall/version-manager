package io.ehdev.conrad.version.bumper.custom;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionGroup;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;


public class CustomCommitVersion implements CommitVersion<String> {

    private final String version;
    private final ZonedDateTime createdAt;

    public CustomCommitVersion(String version) {
        this.version = version;
        this.createdAt = ZonedDateTime.now(ZoneOffset.UTC);
    }

    @Override
    public String toVersionString() {
        return version;
    }

    @Override
    public List getVersionGroup() {
        return Collections.singletonList(version);
    }

    @Override
    public String getPostFix() {
        return null;
    }

    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public String getGroup(CommitVersionGroup versionGroup) {
        return null;
    }

    @Override
    public int compareTo(@NotNull CommitVersion obj) {
        if(!(obj instanceof CustomCommitVersion)) {
            return -1;
        }
        CustomCommitVersion other = (CustomCommitVersion) obj;
        return version.compareTo(other.toVersionString());
    }
}
