UPDATE version_bumpers SET class_name = 'tech.crom.version.bumper.impl.semver.SemanticVersionBumper' WHERE bumper_name = 'semver';
UPDATE version_bumpers SET class_name = 'tech.crom.version.bumper.impl.atomic.AtomicVersionBumper' WHERE bumper_name = 'atomic';
