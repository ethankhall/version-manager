INSERT INTO version_bumpers (bumper_name, class_name, description) VALUES
    ('atomic', 'tech.crom.version.bumper.impl.atomic.AtomicVersionBumper', 'Atomic bumper always gives you an increasing number.'),
    ('semver', 'tech.crom.version.bumper.impl.semver.SemanticVersionBumper', 'Default semantic versions bumper');
