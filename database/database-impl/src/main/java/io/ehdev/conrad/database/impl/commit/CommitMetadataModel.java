package io.ehdev.conrad.database.impl.commit;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "commit_metadata")
public class CommitMetadataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, unique = true, name = "uuid")
    private UUID id;

    @JoinColumn(name = "commit_uuid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CommitModel commitModel;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "text", length = 1024 * 1024)
    private String text;

    CommitMetadataModel(CommitModel model, String name, String text) {
        this.commitModel = model;
        this.name = name;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public CommitModel getCommitModel() {
        return commitModel;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
