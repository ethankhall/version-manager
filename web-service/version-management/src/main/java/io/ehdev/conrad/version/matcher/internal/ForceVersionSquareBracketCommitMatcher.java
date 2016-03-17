package io.ehdev.conrad.version.matcher.internal;

import io.ehdev.conrad.version.commit.CommitVersion;
import io.ehdev.conrad.version.commit.CommitVersionBumper;
import io.ehdev.conrad.version.commit.CommitVersionFactory;
import io.ehdev.conrad.version.matcher.GlobalCommitMatcherProvider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForceVersionSquareBracketCommitMatcher<T extends CommitVersion> implements GlobalCommitMatcherProvider<T> {

    private final Pattern pattern = Pattern.compile(".*?\\[\\s*force version\\s*(.*?)\\s*\\].*?", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private final CommitVersionFactory<T> factory;
    private String version;

    public ForceVersionSquareBracketCommitMatcher(CommitVersionFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public CommitVersionBumper<T> getBumper() {
        return new ForcedVersionBumper(factory.parseVersion(version));
    }

    @Override
    public boolean matches(String string) {
        Matcher matcher = pattern.matcher(string);
        if(matcher.matches()) {
            version = matcher.group(1);
            return true;
        }
        return false;
    }

    private class ForcedVersionBumper implements CommitVersionBumper<T> {

        private final T version;

        ForcedVersionBumper(T version){
            this.version = version;
        }

        @Override
        public T bump(T parent) {
            return version;
        }
    }
}
