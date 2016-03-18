package io.ehdev.conrad.version.bumper.atomic;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionGroup;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

class AtomicCommitVersion implements CommitVersion<Integer> {

    private final int version;

    AtomicCommitVersion(int version) {
        this.version = version;
    }

    public static AtomicCommitVersion parse(String version) {
        return new AtomicCommitVersion(Integer.parseInt(version.split("\\.")[0]));
    }

    public Integer getVersion() {
        return version;
    }

    @Override
    public String toVersionString() {
        return Integer.toString(version);
    }

    @Override
    public List<Integer> getVersionGroup() {
        return Collections.singletonList(version);
    }


    @Override
    public Integer getGroup(CommitVersionGroup versionGroup) {
        if(versionGroup.getGroup() != 1) {
            return null;
        } else {
            return version;
        }
    }

    @Override
    public String getPostFix() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AtomicCommitVersion that = (AtomicCommitVersion) o;
        return version == that.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version);
    }

    @Override
    public String toString() {
        return toVersionString();
    }

    @Override
    public int compareTo(@NotNull CommitVersion obj) {
        if(!(obj instanceof AtomicCommitVersion)) {
            return -1;
        }
        AtomicCommitVersion other = (AtomicCommitVersion) obj;
        return Integer.compare(version, other.version);
    }
}
