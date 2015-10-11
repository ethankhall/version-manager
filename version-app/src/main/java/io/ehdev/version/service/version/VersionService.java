package io.ehdev.version.service.version;

import com.fasterxml.jackson.annotation.JsonView;
import io.ehdev.version.manager.VersionManager;
import io.ehdev.version.model.CommitVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/api/version")
public class VersionService {

    @Autowired
    VersionManager versionManager;

    @JsonView(VersionView.UnreleasedVersionView.class)
    @RequestMapping(value = "/{repoId}/search", method = RequestMethod.POST)
    VersionResponse createUnreleasedVersion(@PathVariable("repoId") String repoId,
                                            @RequestBody VersionSearchModel versionSearchModel) {
        CommitVersion version = versionManager.findVersion(repoId, versionSearchModel);
        return new VersionResponse(null, DefaultCommitVersion.parse("1.2.3"));
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    @RequestMapping(value = "/{repoId}", method = RequestMethod.POST)
    VersionResponse claimVersion(@PathVariable("repoId") String repoId,
                                 @RequestBody VersionSearchModel versionSearchModel) {

        return new VersionResponse(null, DefaultCommitVersion.parse("1.2.3"));
    }

    @JsonView(VersionView.ReleasedVersionView.class)
    @RequestMapping(value = "/{repoId}", method = RequestMethod.GET)
    Map<String, VersionResponse> findVersionsForRepo(@PathVariable("repoId") String repoId) {

        Map<String, VersionResponse> versions = new HashMap<>();
        versions.put("things", new VersionResponse(null, DefaultCommitVersion.parse("1.2.3")));
        return versions;
    }

}
