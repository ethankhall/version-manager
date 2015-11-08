package io.ehdev.conrad.test

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

public class MockMvcDefaults {

    public static Object makeGetRequest(MockMvc mockMvc, String url) {
        def results = mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn()
        return new JsonSlurper().parseText(results.getResponse().getContentAsString())
    }

    public static Object makePostRequest(MockMvc mockMvc, String url, Object obj) {
        def jsonString = new JsonBuilder(obj).toPrettyString()
        def resultsAction = mockMvc.perform(post(url).content(jsonString).contentType(MediaType.APPLICATION_JSON))
        def results = resultsAction.andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn()
        return new JsonSlurper().parseText(results.getResponse().getContentAsString())
    }
}
