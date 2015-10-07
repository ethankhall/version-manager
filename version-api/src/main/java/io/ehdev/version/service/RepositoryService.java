package io.ehdev.version.service;

import io.ehdev.version.model.commit.model.ScmMetaDataModel;
import io.ehdev.version.model.commit.model.VersionBumperModel;
import io.ehdev.version.model.repository.ScmMetaDataRepository;
import io.ehdev.version.model.repository.VersionBumperRepository;
import io.ehdev.version.service.exception.RepoNameMustBeLongerThanFive;
import io.ehdev.version.service.exception.UnableToFindRepo;
import io.ehdev.version.service.exception.UnknownBumperException;
import io.ehdev.version.service.model.RepoCreation;
import io.ehdev.version.service.model.RepoResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

@Transactional
@RestController
@RequestMapping(value="/api/repo")
public class RepositoryService {

    private final Logger log = LoggerFactory.getLogger(RepositoryService.class);

    @Autowired
    ScmMetaDataRepository scmMetaDataRepository;

    @Autowired
    VersionBumperRepository versionBumperRepository;

    @RequestMapping(method = RequestMethod.POST)
    public RepoResponse registerNewRepo(@RequestBody RepoCreation repoCreation) {
        log.info("Repo Creation is: {}", repoCreation);
        VersionBumperModel bumperName = versionBumperRepository.findByBumperName(repoCreation.getStrategyName());
        if(null == bumperName) {
            throw new UnknownBumperException();
        }

        String repoName = StringUtils.trimToEmpty(repoCreation.getRepoName());
        if(StringUtils.length(repoName) < 5) {
            throw new RepoNameMustBeLongerThanFive();
        }

        ScmMetaDataModel scmMetaDataModel = new ScmMetaDataModel();
        scmMetaDataModel.setRepoName(repoName);
        scmMetaDataModel.setVersionBumperModel(bumperName);
        scmMetaDataModel.setUuid(UUID.randomUUID());
        scmMetaDataModel = scmMetaDataRepository.save(scmMetaDataModel);
        return RepoResponse.fromMetaData(scmMetaDataModel);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{repoId}")
    public RepoResponse getByRepoId(@PathVariable("repoId") String repoId) {
        ScmMetaDataModel metaDataModel = scmMetaDataRepository.findByUuid(repoId);
        if(null == metaDataModel) {
            throw new UnableToFindRepo();
        }
        return RepoResponse.fromMetaData(metaDataModel);
    }
}
