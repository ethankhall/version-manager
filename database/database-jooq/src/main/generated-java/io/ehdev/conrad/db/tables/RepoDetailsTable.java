/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables;


import io.ehdev.conrad.db.Keys;
import io.ehdev.conrad.db.Public;
import io.ehdev.conrad.db.tables.records.RepoDetailsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class RepoDetailsTable extends TableImpl<RepoDetailsRecord> {

	private static final long serialVersionUID = 42463061;

	/**
	 * The reference instance of <code>public.repo_details</code>
	 */
	public static final RepoDetailsTable REPO_DETAILS = new RepoDetailsTable();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<RepoDetailsRecord> getRecordType() {
		return RepoDetailsRecord.class;
	}

	/**
	 * The column <code>public.repo_details.uuid</code>.
	 */
	public final TableField<RepoDetailsRecord, UUID> UUID = createField("uuid", org.jooq.impl.SQLDataType.UUID.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.repo_details.project_name</code>.
	 */
	public final TableField<RepoDetailsRecord, String> PROJECT_NAME = createField("project_name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>public.repo_details.repo_name</code>.
	 */
	public final TableField<RepoDetailsRecord, String> REPO_NAME = createField("repo_name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>public.repo_details.project_uuid</code>.
	 */
	public final TableField<RepoDetailsRecord, UUID> PROJECT_UUID = createField("project_uuid", org.jooq.impl.SQLDataType.UUID, this, "");

	/**
	 * The column <code>public.repo_details.version_bumper_uuid</code>.
	 */
	public final TableField<RepoDetailsRecord, UUID> VERSION_BUMPER_UUID = createField("version_bumper_uuid", org.jooq.impl.SQLDataType.UUID, this, "");

	/**
	 * The column <code>public.repo_details.url</code>.
	 */
	public final TableField<RepoDetailsRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(1024).nullable(false), this, "");

	/**
	 * The column <code>public.repo_details.description</code>.
	 */
	public final TableField<RepoDetailsRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>public.repo_details.public</code>.
	 */
	public final TableField<RepoDetailsRecord, Boolean> PUBLIC = createField("public", org.jooq.impl.SQLDataType.BOOLEAN.defaulted(true), this, "");

	/**
	 * Create a <code>public.repo_details</code> table reference
	 */
	public RepoDetailsTable() {
		this("repo_details", null);
	}

	/**
	 * Create an aliased <code>public.repo_details</code> table reference
	 */
	public RepoDetailsTable(String alias) {
		this(alias, REPO_DETAILS);
	}

	private RepoDetailsTable(String alias, Table<RepoDetailsRecord> aliased) {
		this(alias, aliased, null);
	}

	private RepoDetailsTable(String alias, Table<RepoDetailsRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<RepoDetailsRecord> getPrimaryKey() {
		return Keys.REPO_DETAILS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<RepoDetailsRecord>> getKeys() {
		return Arrays.<UniqueKey<RepoDetailsRecord>>asList(Keys.REPO_DETAILS_PKEY, Keys.REPO_DETAILS_PROJECT_NAME_REPO_NAME_KEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<RepoDetailsRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<RepoDetailsRecord, ?>>asList(Keys.REPO_DETAILS__REPO_DETAILS_PROJECT_UUID_FKEY, Keys.REPO_DETAILS__REPO_DETAILS_VERSION_BUMPER_UUID_FKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepoDetailsTable as(String alias) {
		return new RepoDetailsTable(alias, this);
	}

	/**
	 * Rename this table
	 */
	public RepoDetailsTable rename(String name) {
		return new RepoDetailsTable(name, null);
	}
}