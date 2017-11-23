package io.pivotal.training.fortune;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(FortuneController.class)
public class FortuneControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private FortuneService fortuneService;

  private static final String ExpectedFortune = "my special fortune";

  @Before
  public void setup() {
    when(fortuneService.getFortune()).thenReturn(ExpectedFortune);
  }

  @Test
  public void shouldGetFortuneOverHttp() throws Exception {
    mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.fortune").value(ExpectedFortune));

    verify(fortuneService).getFortune();
  }
}
