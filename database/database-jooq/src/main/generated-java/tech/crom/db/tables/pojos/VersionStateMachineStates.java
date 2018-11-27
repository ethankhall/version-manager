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
import javax.persistence.Index;
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
        "jOOQ version:3.11.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "version_state_machine_states", schema = "version_manager_test", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"version_state_machine_id", "state_name"})
}, indexes = {
    @Index(name = "PRIMARY", unique = true, columnList = "version_state_machine_state_id ASC"),
    @Index(name = "version_state_machine_id", unique = true, columnList = "version_state_machine_id ASC, state_name ASC")
})
public class VersionStateMachineStates implements Serializable {

    private static final long serialVersionUID = 410933711;

    private Long    versionStateMachineStateId;
    private Long    versionStateMachineId;
    private String  stateName;
    private Boolean autoTransition;
    private String  nextState;

    public VersionStateMachineStates() {}

    public VersionStateMachineStates(VersionStateMachineStates value) {
        this.versionStateMachineStateId = value.versionStateMachineStateId;
        this.versionStateMachineId = value.versionStateMachineId;
        this.stateName = value.stateName;
        this.autoTransition = value.autoTransition;
        this.nextState = value.nextState;
    }

    public VersionStateMachineStates(
        Long    versionStateMachineStateId,
        Long    versionStateMachineId,
        String  stateName,
        Boolean autoTransition,
        String  nextState
    ) {
        this.versionStateMachineStateId = versionStateMachineStateId;
        this.versionStateMachineId = versionStateMachineId;
        this.stateName = stateName;
        this.autoTransition = autoTransition;
        this.nextState = nextState;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_state_machine_state_id", unique = true, nullable = false, precision = 19)
    public Long getVersionStateMachineStateId() {
        return this.versionStateMachineStateId;
    }

    public void setVersionStateMachineStateId(Long versionStateMachineStateId) {
        this.versionStateMachineStateId = versionStateMachineStateId;
    }

    @Column(name = "version_state_machine_id", nullable = false, precision = 19)
    @NotNull
    public Long getVersionStateMachineId() {
        return this.versionStateMachineId;
    }

    public void setVersionStateMachineId(Long versionStateMachineId) {
        this.versionStateMachineId = versionStateMachineId;
    }

    @Column(name = "state_name", nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    public String getStateName() {
        return this.stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    @Column(name = "auto_transition")
    public Boolean getAutoTransition() {
        return this.autoTransition;
    }

    public void setAutoTransition(Boolean autoTransition) {
        this.autoTransition = autoTransition;
    }

    @Column(name = "next_state", length = 64)
    @Size(max = 64)
    public String getNextState() {
        return this.nextState;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("VersionStateMachineStates (");

        sb.append(versionStateMachineStateId);
        sb.append(", ").append(versionStateMachineId);
        sb.append(", ").append(stateName);
        sb.append(", ").append(autoTransition);
        sb.append(", ").append(nextState);

        sb.append(")");
        return sb.toString();
    }
}
