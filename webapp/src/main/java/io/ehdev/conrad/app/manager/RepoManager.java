package io.ehdev.conrad.app.manager;

import io.ehdev.conrad.database.model.VcsRepoModel;
import io.ehdev.conrad.database.repository.VcsRepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RepoManager {

    private final VcsRepoRepository vcsRepoRepository;

    private final BumperManager bumperManager;

    @Autowired
    public RepoManager(VcsRepoRepository vcsRepoRepository, BumperManager bumperManager) {
        this.vcsRepoRepository = vcsRepoRepository;
        this.bumperManager = bumperManager;
    }

    public VcsRepoModel createRepo(String repoName, String bumperName, String repoUrl) {
        VcsRepoModel vcsRepoModel = new VcsRepoModel();
        vcsRepoModel.setUuid(UUID.randomUUID());
        vcsRepoModel.setRepoName(repoName);
        vcsRepoModel.setUrl(repoUrl);

        vcsRepoModel.setVersionBumperModel(bumperManager.findByName(bumperName));
        return vcsRepoRepository.save(vcsRepoModel);
    }

    public List<VcsRepoModel> getAll() {
        return vcsRepoRepository.findAll();
    }
}
