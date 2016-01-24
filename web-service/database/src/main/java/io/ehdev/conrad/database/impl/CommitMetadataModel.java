package io.ehdev.conrad.database.impl;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "commit_metadata")
public class CommitMetadataModel implements UniqueModel {

    @Id
    @Type(type="pg-uuid")
    @Column(name = "uuid", unique = true)
    @GeneratedValue(generator = "uuid-gen")
    @GenericGenerator(name = "uuid-gen", strategy = "uuid2")
    UUID id;

    @JoinColumn(name = "commit_uuid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    CommitModel commitModel;

    @Column(name = "name", length = 255)
    String name;

    @Column(name = "text", length = 1024 * 1024)
    String text;

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID uuid) {
        this.id = uuid;
    }
}
