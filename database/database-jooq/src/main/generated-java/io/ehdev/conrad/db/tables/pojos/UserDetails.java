/**
 * This class is generated by jOOQ
 */
package io.ehdev.conrad.db.tables.pojos;


import java.io.Serializable;
import java.util.UUID;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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
@Entity
@Table(name = "user_details", schema = "public")
public class UserDetails implements Serializable {

	private static final long serialVersionUID = -1878546587;

	private UUID   uuid;
	private String userName;
	private String name;
	private String emailAddress;

	public UserDetails() {}

	public UserDetails(UserDetails value) {
		this.uuid = value.uuid;
		this.userName = value.userName;
		this.name = value.name;
		this.emailAddress = value.emailAddress;
	}

	public UserDetails(
		UUID   uuid,
		String userName,
		String name,
		String emailAddress
	) {
		this.uuid = uuid;
		this.userName = userName;
		this.name = name;
		this.emailAddress = emailAddress;
	}

	@Id
	@Column(name = "uuid", unique = true, nullable = false)
	@NotNull
	public UUID getUuid() {
		return this.uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	@Column(name = "user_name", unique = true, nullable = false, length = 128)
	@NotNull
	@Size(max = 128)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "name", nullable = false, length = 255)
	@NotNull
	@Size(max = 255)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "email_address", nullable = false, length = 256)
	@NotNull
	@Size(max = 256)
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("UserDetails (");

		sb.append(uuid);
		sb.append(", ").append(userName);
		sb.append(", ").append(name);
		sb.append(", ").append(emailAddress);

		sb.append(")");
		return sb.toString();
	}
}