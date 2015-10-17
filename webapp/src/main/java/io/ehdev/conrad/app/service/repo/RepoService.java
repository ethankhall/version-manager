package io.ehdev.conrad.app.service.repo;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.conrad.app.manager.RepoManager;
import io.ehdev.conrad.app.service.repo.model.RepoCreateModel;
import io.ehdev.conrad.app.service.repo.model.RepoResponseModel;
import io.ehdev.conrad.app.service.repo.model.ViewPermission;
import io.ehdev.conrad.database.model.VcsRepoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;
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

    @JsonView(ViewPermission.Private.class)
    @RequestMapping(method = RequestMethod.POST)
    public RepoResponseModel createRepo(@Valid @RequestBody RepoCreateModel repoCreateModel) {
        VcsRepoModel repo = repoManager.createRepo(repoCreateModel.getName(), repoCreateModel.getBumper(), repoCreateModel.getUrl());
        return new RepoResponseModel(repo);
    }

    @JsonView(ViewPermission.Public.class)
    @RequestMapping(method = RequestMethod.GET)
    public Map<String, RepoResponseModel> getAllRepos(){
        return repoManager.getAll().stream().collect(Collectors.toMap(VcsRepoModel::getUuid, RepoResponseModel::new));
    }
}
