package io.ehdev.conrad.app.service.repo;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.conrad.app.manager.RepoManager;
import io.ehdev.conrad.app.service.ApiFactory;
import io.ehdev.conrad.database.model.VcsRepoModel;
import io.ehdev.conrad.model.repo.RepoCreateModel;
import io.ehdev.conrad.model.repo.RepoResponseModel;
import io.ehdev.conrad.model.repo.RepoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api/repo")
public class RepoService {

    private final RepoManager repoManager;

    @Autowired
    public RepoService(RepoManager repoManager) {
        this.repoManager = repoManager;
    }

    @JsonView(RepoView.Private.class)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RepoResponseModel createRepo(@Valid @RequestBody RepoCreateModel repoCreateModel) {
        VcsRepoModel repo = repoManager.createRepo(repoCreateModel.getName(), repoCreateModel.getBumper(), repoCreateModel.getUrl());
        if(repoCreateModel.getHistory() != null) {
            repoManager.setupHistory(repo, repoCreateModel.getHistory());
        }
        return ApiFactory.RepoModelFactory.create(repo);
    }

    @JsonView(RepoView.Public.class)
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, RepoResponseModel> getAllRepos() {
        Collector<VcsRepoModel, ?, Map<String, RepoResponseModel>> collector = Collectors.toMap(VcsRepoModel::getIdAsString, ApiFactory.RepoModelFactory::create);
        return repoManager.getAll().stream().collect(collector);
    }
}
