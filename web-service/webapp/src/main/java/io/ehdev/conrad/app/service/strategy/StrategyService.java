package io.ehdev.conrad.app.service.strategy;

import io.ehdev.conrad.app.manager.BumperManager;
import io.ehdev.conrad.app.service.ApiFactory;
import io.ehdev.conrad.database.internal.VersionBumperModel;
import io.ehdev.conrad.model.strategy.StrategyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api/strategy")
public class StrategyService {

    final BumperManager bumperManager;

    @Autowired
    public StrategyService(BumperManager bumperManager) {
        this.bumperManager = bumperManager;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Map<String, StrategyModel> getAllVersionStrategies() {
        return bumperManager.findAllVersionBumpers().stream()
            .collect(Collectors.toMap(VersionBumperModel::getBumperName, ApiFactory.StrategyModelFactory::create));
    }
}
