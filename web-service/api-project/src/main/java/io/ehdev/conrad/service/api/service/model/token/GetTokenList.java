package io.ehdev.conrad.service.api.service.model.token;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class GetTokenList {
    @JsonProperty("tokens")
    private final List<GetTokenListEntry> entryList = new ArrayList<>();

    public GetTokenList(List<GetTokenListEntry> tokens) {
        entryList.addAll(tokens);
    }

    public List<GetTokenListEntry> getEntryList() {
        return entryList;
    }
}
