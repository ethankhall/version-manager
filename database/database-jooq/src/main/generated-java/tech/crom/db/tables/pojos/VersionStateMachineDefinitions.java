/*
 * This file is generated by jOOQ.
*/
package tech.crom.db.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "version_state_machine_definitions", schema = "version_manager_test")
public class VersionStateMachineDefinitions implements Serializable {

    private static final long serialVersionUID = -1091694660;

    private Long   versionStateMachineId;
    private String initialState;
    private Long   repoDetailId;

    public VersionStateMachineDefinitions() {}

    public VersionStateMachineDefinitions(VersionStateMachineDefinitions value) {
        this.versionStateMachineId = value.versionStateMachineId;
        this.initialState = value.initialState;
        this.repoDetailId = value.repoDetailId;
    }

    public VersionStateMachineDefinitions(
        Long   versionStateMachineId,
        String initialState,
        Long   repoDetailId
    ) {
        this.versionStateMachineId = versionStateMachineId;
        this.initialState = initialState;
        this.repoDetailId = repoDetailId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_state_machine_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getVersionStateMachineId() {
        return this.versionStateMachineId;
    }

    public void setVersionStateMachineId(Long versionStateMachineId) {
        this.versionStateMachineId = versionStateMachineId;
    }

    @Column(name = "initial_state", nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    public String getInitialState() {
        return this.initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    @Column(name = "repo_detail_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getRepoDetailId() {
        return this.repoDetailId;
    }

    public void setRepoDetailId(Long repoDetailId) {
        this.repoDetailId = repoDetailId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("VersionStateMachineDefinitions (");

        sb.append(versionStateMachineId);
        sb.append(", ").append(initialState);
        sb.append(", ").append(repoDetailId);

        sb.append(")");
        return sb.toString();
    }
}