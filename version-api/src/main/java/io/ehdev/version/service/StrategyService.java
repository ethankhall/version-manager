package io.ehdev.version.service;

import io.ehdev.version.model.commit.model.VersionBumperModel;
import io.ehdev.version.model.repository.VersionBumperRepository;
import io.ehdev.version.service.model.StrategyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@RestController
@RequestMapping("/api/strategy")
public class StrategyService {

    @Autowired
    VersionBumperRepository versionBumperRepository;

    @RequestMapping(method = RequestMethod.GET)
    public StrategyResponse getAllVersionStrategies() {
        List<VersionBumperModel> all = versionBumperRepository.findAll();
        Map<String, StrategyResponse.Strategies> strategiesMap = new HashMap<>();
        all.forEach(bumper ->
            strategiesMap.put(bumper.getBumperName(),
                new StrategyResponse.Strategies(bumper.getBumperName(), bumper.getDescription())));
        StrategyResponse strategyResponse = new StrategyResponse();
        strategyResponse.setStrategies(strategiesMap);
        return strategyResponse;
    }
}
