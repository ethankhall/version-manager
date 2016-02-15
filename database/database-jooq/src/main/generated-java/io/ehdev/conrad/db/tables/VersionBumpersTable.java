/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables;


import io.ehdev.conrad.db.Keys;
import io.ehdev.conrad.db.Public;
import io.ehdev.conrad.db.tables.records.VersionBumpersRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class VersionBumpersTable extends TableImpl<VersionBumpersRecord> {

	private static final long serialVersionUID = 1663797691;

	/**
	 * The reference instance of <code>public.version_bumpers</code>
	 */
	public static final VersionBumpersTable VERSION_BUMPERS = new VersionBumpersTable();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<VersionBumpersRecord> getRecordType() {
		return VersionBumpersRecord.class;
	}

	/**
	 * The column <code>public.version_bumpers.uuid</code>.
	 */
	public final TableField<VersionBumpersRecord, UUID> UUID = createField("uuid", org.jooq.impl.SQLDataType.UUID.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.version_bumpers.bumper_name</code>.
	 */
	public final TableField<VersionBumpersRecord, String> BUMPER_NAME = createField("bumper_name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>public.version_bumpers.class_name</code>.
	 */
	public final TableField<VersionBumpersRecord, String> CLASS_NAME = createField("class_name", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>public.version_bumpers.description</code>.
	 */
	public final TableField<VersionBumpersRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * Create a <code>public.version_bumpers</code> table reference
	 */
	public VersionBumpersTable() {
		this("version_bumpers", null);
	}

	/**
	 * Create an aliased <code>public.version_bumpers</code> table reference
	 */
	public VersionBumpersTable(String alias) {
		this(alias, VERSION_BUMPERS);
	}

	private VersionBumpersTable(String alias, Table<VersionBumpersRecord> aliased) {
		this(alias, aliased, null);
	}

	private VersionBumpersTable(String alias, Table<VersionBumpersRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<VersionBumpersRecord> getPrimaryKey() {
		return Keys.VERSION_BUMPERS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<VersionBumpersRecord>> getKeys() {
		return Arrays.<UniqueKey<VersionBumpersRecord>>asList(Keys.VERSION_BUMPERS_PKEY, Keys.VERSION_BUMPERS_BUMPER_NAME_KEY, Keys.VERSION_BUMPERS_CLASS_NAME_KEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VersionBumpersTable as(String alias) {
		return new VersionBumpersTable(alias, this);
	}

	/**
	 * Rename this table
	 */
	public VersionBumpersTable rename(String name) {
		return new VersionBumpersTable(name, null);
	}
}