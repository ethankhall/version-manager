package io.ehdev.conrad.version.matcher


import io.ehdev.conrad.version.bumper.semver.SemanticCommitVersion
import io.ehdev.conrad.version.commit.CommitVersion
import io.ehdev.conrad.version.commit.CommitVersionBumper
import io.ehdev.conrad.version.commit.CommitVersionFactory
import io.ehdev.conrad.version.commit.CommitVersionGroup
import io.ehdev.conrad.version.matcher.internal.ForceVersionSquareBracketCommitMatcher
import org.apache.commons.lang3.NotImplementedException
import spock.lang.Specification
import spock.lang.Unroll

class ForceVersionSquareBracketCommitMatcherTest extends Specification {

    def 'can match based on random string'() {
        expect:
        new ForceVersionSquareBracketCommitMatcher(createVersionFactory()).matches('asdfasdf[force version 123]asdfasd')
        new ForceVersionSquareBracketCommitMatcher(createVersionFactory()).matches('asdfasdf[ force version 2.3.4.4.5.6.7.8.9 ]asdfasd')
        new ForceVersionSquareBracketCommitMatcher(createVersionFactory()).matches('asdfasdf[set version 123]asdfasd')
        new ForceVersionSquareBracketCommitMatcher(createVersionFactory()).matches('asdfasdf[ set version 2.3.4.4.5.6.7.8.9 ]asdfasd')
        !new ForceVersionSquareBracketCommitMatcher(createVersionFactory()).matches('asdfasdf[bump 124]asdfasd')
    }

    @Unroll
    def 'gives correct version for #version'() {
        def matcher = new ForceVersionSquareBracketCommitMatcher()

        expect:
        matcher.matches("asdfasdf[force version ${version.toVersionString()}]asdfasd")

        where:
        version                                  | _
        SemanticCommitVersion.parse('1.2.3.4.5') | _
        SemanticCommitVersion.parse('1234')      | _
    }

    CommitVersionFactory createVersionFactory() {
        return new CommitVersionFactory() {
            @Override
            CommitVersionBumper dispenseBumper(int group) {
                throw new NotImplementedException('')
            }

            @Override
            CommitVersionBumper dispenseBumper(CommitVersionGroup group) {
                throw new NotImplementedException('')
            }

            @Override
            CommitVersion parseVersion(String version) {
                throw new NotImplementedException('')
            }
        }
    }
}
