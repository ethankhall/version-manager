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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "resource_detail_lookup", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"project_name", "repo_name"}),
    @UniqueConstraint(columnNames = {"project_uuid", "repo_uuid"})
})
public class ResourceDetailLookup implements Serializable {

    private static final long serialVersionUID = 790408325;

    private UUID   uuid;
    private String projectName;
    private String repoName;
    private UUID   projectUuid;
    private UUID   repoUuid;

    public ResourceDetailLookup() {}

    public ResourceDetailLookup(ResourceDetailLookup value) {
        this.uuid = value.uuid;
        this.projectName = value.projectName;
        this.repoName = value.repoName;
        this.projectUuid = value.projectUuid;
        this.repoUuid = value.repoUuid;
    }

    public ResourceDetailLookup(
        UUID   uuid,
        String projectName,
        String repoName,
        UUID   projectUuid,
        UUID   repoUuid
    ) {
        this.uuid = uuid;
        this.projectName = projectName;
        this.repoName = repoName;
        this.projectUuid = projectUuid;
        this.repoUuid = repoUuid;
    }

    @Id
    @Column(name = "uuid", unique = true, nullable = false)
    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Column(name = "project_name", nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "repo_name", length = 255)
    @Size(max = 255)
    public String getRepoName() {
        return this.repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    @Column(name = "project_uuid", nullable = false)
    @NotNull
    public UUID getProjectUuid() {
        return this.projectUuid;
    }

    public void setProjectUuid(UUID projectUuid) {
        this.projectUuid = projectUuid;
    }

    @Column(name = "repo_uuid", unique = true)
    public UUID getRepoUuid() {
        return this.repoUuid;
    }

    public void setRepoUuid(UUID repoUuid) {
        this.repoUuid = repoUuid;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ResourceDetailLookup (");

        sb.append(uuid);
        sb.append(", ").append(projectName);
        sb.append(", ").append(repoName);
        sb.append(", ").append(projectUuid);
        sb.append(", ").append(repoUuid);

        sb.append(")");
        return sb.toString();
    }
}