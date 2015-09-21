package io.ehdev.version.service;

import io.ehdev.version.commit.model.VersionBumperModel;
import io.ehdev.version.repository.VersionBumperRepository;
import io.ehdev.version.service.model.StrategyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api/strategy")
public class VersionStrategyService {

    @Autowired
    VersionBumperRepository versionBumperRepository;

    @RequestMapping(method = RequestMethod.GET)
    public StrategyResponse getAllVersionStrategies() {
        List<VersionBumperModel> all = versionBumperRepository.findAll();
        List<String> strategyNames = all.stream().map(VersionBumperModel::getBumperName).collect(Collectors.toList());
        StrategyResponse strategyResponse = new StrategyResponse();
        strategyResponse.setStratigies(strategyNames);
        return strategyResponse;
    }
}
