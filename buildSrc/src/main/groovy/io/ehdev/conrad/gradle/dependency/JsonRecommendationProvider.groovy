package io.ehdev.conrad.gradle.dependency

import netflix.nebula.dependency.recommender.provider.AbstractRecommendationProvider

class JsonRecommendationProvider extends AbstractRecommendationProvider {

    private final Map<String, String> coordinateMap = [:]

    public JsonRecommendationProvider(List<Map<String, String>> input) {
        input.each { coordinateMap[it.coordinate] = it.version}
    }

    @Override
    String getVersion(String org, String name) throws Exception {
        return coordinateMap.get(org) ?: coordinateMap.get(org + ":" + name)
    }
}
