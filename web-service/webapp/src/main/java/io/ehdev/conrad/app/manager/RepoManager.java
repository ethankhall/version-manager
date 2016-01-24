package io.ehdev.conrad.app.manager;

import io.ehdev.conrad.backend.version.commit.VersionFactory;
import io.ehdev.conrad.database.impl.CommitModel;
import io.ehdev.conrad.database.impl.VcsRepoModel;
import io.ehdev.conrad.database.repository.CommitModelRepository;
import io.ehdev.conrad.database.repository.VcsRepoRepository;
import io.ehdev.conrad.model.repo.RepoHistory;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepoManager {

    private final VcsRepoRepository vcsRepoRepository;
    private final CommitModelRepository commitModelRepository;

    private final BumperManager bumperManager;

    @Autowired
    public RepoManager(VcsRepoRepository vcsRepoRepository, BumperManager bumperManager, CommitModelRepository commitModelRepository) {
        this.vcsRepoRepository = vcsRepoRepository;
        this.bumperManager = bumperManager;
        this.commitModelRepository = commitModelRepository;
    }

    public VcsRepoModel createRepo(String repoName, String bumperName, String repoUrl) {
        VcsRepoModel vcsRepoModel = new VcsRepoModel();
        vcsRepoModel.setRepoName(repoName);
        vcsRepoModel.setUrl(repoUrl);
        vcsRepoModel.setToken(RandomStringUtils.randomAlphanumeric(60));

        vcsRepoModel.setVersionBumperModel(bumperManager.findByName(bumperName));
        return vcsRepoRepository.save(vcsRepoModel);
    }

    public List<VcsRepoModel> getAll() {
        return vcsRepoRepository.findAll();
    }

    public void setupHistory(VcsRepoModel repo, List<RepoHistory> repoHistoryList) {
        CommitModel parent = null;
        for(RepoHistory history : repoHistoryList) {
            parent = commitModelRepository.save(new CommitModel(history.getCommitId(), repo, VersionFactory.parse(history.getVersion()), parent));
        }
    }
}
