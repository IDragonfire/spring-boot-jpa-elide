package com.matthewcasperson;

import com.matthewcasperson.elidetest.ParentRepository;
import com.matthewcasperson.elidetest.jpa.Parent;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TransactionalTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private WebApplicationContext context;
  @Autowired
  private ParentRepository parentRepositor;

  @Before
  public void setUp() {
    mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build();
  }

  @org.junit.Test
  public void cannotKickLeaderFromClan() throws Exception {
    assertEquals(0, parentRepositor.count());
    Parent myEntity = new Parent();
    myEntity.setName("Dragonfire");
    parentRepositor.save(myEntity);
    assertEquals(1, parentRepositor.count());
    this.mvc.perform(get("/parent"))
            .andExpect(content().string("{\"data\":[{\"type\":\"parent\",\"id\":\"1\",\"attributes\":{\"name\":\"Dragonfire\"}}]}"))
            .andExpect(status().is(200));
    assertEquals(1, parentRepositor.count());
  }

}
