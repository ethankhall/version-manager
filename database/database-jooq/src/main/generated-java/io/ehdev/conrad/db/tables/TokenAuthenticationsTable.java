/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables;


import io.ehdev.conrad.db.Keys;
import io.ehdev.conrad.db.Public;
import io.ehdev.conrad.db.converter.TimestampConverter;
import io.ehdev.conrad.db.enums.TokenType;
import io.ehdev.conrad.db.tables.records.TokenAuthenticationsRecord;

import java.time.Instant;
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
public class TokenAuthenticationsTable extends TableImpl<TokenAuthenticationsRecord> {

	private static final long serialVersionUID = 1584061243;

	/**
	 * The reference instance of <code>public.token_authentications</code>
	 */
	public static final TokenAuthenticationsTable TOKEN_AUTHENTICATIONS = new TokenAuthenticationsTable();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<TokenAuthenticationsRecord> getRecordType() {
		return TokenAuthenticationsRecord.class;
	}

	/**
	 * The column <code>public.token_authentications.uuid</code>.
	 */
	public final TableField<TokenAuthenticationsRecord, UUID> UUID = createField("uuid", org.jooq.impl.SQLDataType.UUID.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>public.token_authentications.created_at</code>.
	 */
	public final TableField<TokenAuthenticationsRecord, Instant> CREATED_AT = createField("created_at", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "", new TimestampConverter());

	/**
	 * The column <code>public.token_authentications.expires_at</code>.
	 */
	public final TableField<TokenAuthenticationsRecord, Instant> EXPIRES_AT = createField("expires_at", org.jooq.impl.SQLDataType.TIMESTAMP, this, "", new TimestampConverter());

	/**
	 * The column <code>public.token_authentications.valid</code>.
	 */
	public final TableField<TokenAuthenticationsRecord, Boolean> VALID = createField("valid", org.jooq.impl.SQLDataType.BOOLEAN.defaulted(true), this, "");

	/**
	 * The column <code>public.token_authentications.token_type</code>.
	 */
	public final TableField<TokenAuthenticationsRecord, TokenType> TOKEN_TYPE = createField("token_type", org.jooq.util.postgres.PostgresDataType.VARCHAR.asEnumDataType(io.ehdev.conrad.db.enums.TokenType.class), this, "");

	/**
	 * Create a <code>public.token_authentications</code> table reference
	 */
	public TokenAuthenticationsTable() {
		this("token_authentications", null);
	}

	/**
	 * Create an aliased <code>public.token_authentications</code> table reference
	 */
	public TokenAuthenticationsTable(String alias) {
		this(alias, TOKEN_AUTHENTICATIONS);
	}

	private TokenAuthenticationsTable(String alias, Table<TokenAuthenticationsRecord> aliased) {
		this(alias, aliased, null);
	}

	private TokenAuthenticationsTable(String alias, Table<TokenAuthenticationsRecord> aliased, Field<?>[] parameters) {
		super(alias, Public.PUBLIC, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<TokenAuthenticationsRecord> getPrimaryKey() {
		return Keys.TOKEN_AUTHENTICATIONS_PKEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<TokenAuthenticationsRecord>> getKeys() {
		return Arrays.<UniqueKey<TokenAuthenticationsRecord>>asList(Keys.TOKEN_AUTHENTICATIONS_PKEY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TokenAuthenticationsTable as(String alias) {
		return new TokenAuthenticationsTable(alias, this);
	}

	/**
	 * Rename this table
	 */
	public TokenAuthenticationsTable rename(String name) {
		return new TokenAuthenticationsTable(name, null);
	}
}
