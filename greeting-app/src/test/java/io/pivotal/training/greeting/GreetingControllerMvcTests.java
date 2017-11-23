package io.pivotal.training.greeting;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
public class GreetingControllerMvcTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private GreetingProperties greetingProperties;
  @MockBean private FortuneServiceClient fortuneServiceClient;

  private static final String ExpectedFortune = "some fortune";

  @Before
  public void setup() {
    when(greetingProperties.isDisplayFortune()).thenReturn(true);
    when(fortuneServiceClient.getFortune()).thenReturn(ExpectedFortune);
  }

  @Test
  public void shouldGetGreetingWithDisplayFortuneSet() throws Exception {
    mockMvc.perform(get("/").accept(MediaType.TEXT_HTML))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(content().string(containsString("Greetings")))
        .andExpect(content().string(containsString(ExpectedFortune)));
  }

}
