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
@Table(name = "version_state_machine_connections", schema = "version_manager_test")
public class VersionStateMachineConnections implements Serializable {

    private static final long serialVersionUID = -207275667;

    private Long stateMachineDetailId;
    private Long source;
    private Long destination;

    public VersionStateMachineConnections() {}

    public VersionStateMachineConnections(VersionStateMachineConnections value) {
        this.stateMachineDetailId = value.stateMachineDetailId;
        this.source = value.source;
        this.destination = value.destination;
    }

    public VersionStateMachineConnections(
        Long stateMachineDetailId,
        Long source,
        Long destination
    ) {
        this.stateMachineDetailId = stateMachineDetailId;
        this.source = source;
        this.destination = destination;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "state_machine_detail_id", unique = true, nullable = false, precision = 19)
    @NotNull
    public Long getStateMachineDetailId() {
        return this.stateMachineDetailId;
    }

    public void setStateMachineDetailId(Long stateMachineDetailId) {
        this.stateMachineDetailId = stateMachineDetailId;
    }

    @Column(name = "source", nullable = false, precision = 19)
    @NotNull
    public Long getSource() {
        return this.source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    @Column(name = "destination", nullable = false, precision = 19)
    @NotNull
    public Long getDestination() {
        return this.destination;
    }

    public void setDestination(Long destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("VersionStateMachineConnections (");

        sb.append(stateMachineDetailId);
        sb.append(", ").append(source);
        sb.append(", ").append(destination);

        sb.append(")");
        return sb.toString();
    }
}
