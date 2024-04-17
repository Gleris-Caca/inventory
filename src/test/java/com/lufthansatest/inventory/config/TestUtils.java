package com.lufthansatest.inventory.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Component
@RequiredArgsConstructor
public class TestUtils {
    private MockMvc mvc;
    private final ObjectMapper objectMapper;

    public void setup(Object classToBuild){
        mvc = MockMvcBuilders
                .standaloneSetup(classToBuild)
                .build();

    }

    public MockHttpServletResponse get(String path)  {
        try {
            return mvc.perform(MockMvcRequestBuilders
                            .get(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();
        }catch (Exception exception){
            throw new RuntimeException();
        }
    }

    public int getStatus(String path) {
        try {
            return mvc.perform(MockMvcRequestBuilders.get(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse()
                    .getStatus();
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    public MockHttpServletResponse post(String path, String content) {
        try {
            String urlTemplate = path;
            ResultActions request = mvc.perform(MockMvcRequestBuilders.post(urlTemplate)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
                    .accept(MediaType.APPLICATION_JSON));
            return request.andReturn().getResponse();
        }
        catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    public MockHttpServletResponse put(String path) {
        try {
            return mvc
                    .perform(MockMvcRequestBuilders
                            .put(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn()
                    .getResponse();
        }
        catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    public MockHttpServletResponse delete(String path) {
        try {
            return mvc
                    .perform(MockMvcRequestBuilders
                            .delete(path)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andReturn().getResponse();
        }  catch (Exception exception) {
            throw new RuntimeException();
        }
    }

}
