/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db;


import io.ehdev.conrad.db.tables.CommitDetailsTable;
import io.ehdev.conrad.db.tables.CommitMetadataTable;
import io.ehdev.conrad.db.tables.ProjectDetailsTable;
import io.ehdev.conrad.db.tables.RepoDetailsTable;
import io.ehdev.conrad.db.tables.UserDetailsTable;
import io.ehdev.conrad.db.tables.UserSecurityClientProfileTable;
import io.ehdev.conrad.db.tables.UserTokensTable;
import io.ehdev.conrad.db.tables.VersionBumpersTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Public extends SchemaImpl {

	private static final long serialVersionUID = 31930380;

	/**
	 * The reference instance of <code>public</code>
	 */
	public static final Public PUBLIC = new Public();

	/**
	 * No further instances allowed
	 */
	private Public() {
		super("public");
	}

	@Override
	public final List<Table<?>> getTables() {
		List result = new ArrayList();
		result.addAll(getTables0());
		return result;
	}

	private final List<Table<?>> getTables0() {
		return Arrays.<Table<?>>asList(
			CommitDetailsTable.COMMIT_DETAILS,
			CommitMetadataTable.COMMIT_METADATA,
			ProjectDetailsTable.PROJECT_DETAILS,
			RepoDetailsTable.REPO_DETAILS,
			UserDetailsTable.USER_DETAILS,
			UserSecurityClientProfileTable.USER_SECURITY_CLIENT_PROFILE,
			UserTokensTable.USER_TOKENS,
			VersionBumpersTable.VERSION_BUMPERS);
	}
}
