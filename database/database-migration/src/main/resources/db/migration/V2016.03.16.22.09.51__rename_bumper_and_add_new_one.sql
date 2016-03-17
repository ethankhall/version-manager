UPDATE version_bumpers SET class_name = 'io.ehdev.conrad.version.bumper.semver.SemanticVersionBumper' WHERE bumper_name = 'semver';

INSERT INTO version_bumpers (bumper_name, class_name, description)
VALUES ('atomic', 'io.ehdev.conrad.version.bumper.atomic.AtomicNumberBumper', 'Atomic bumper always gives you an increasing number.');
