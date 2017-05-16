package com.matthewcasperson.elidetest;

import com.matthewcasperson.elidetest.jpa.Child;
import com.yahoo.elide.Elide;
import com.yahoo.elide.ElideResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedHashMap;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;

/**
 * The rest interface to DZone and other services
 */
@RestController
public class ElideTest {

  @Autowired
  private Elide elide;


  @Inject
  public ElideTest(ParentRepository parentRepository, ChildRepository childRepository) {
    Child alice = new Child();
    alice.setName("Alice");
    alice = childRepository.save(alice);

    Child bob = new Child();
    bob.setName("Bob");
    bob = childRepository.save(bob);

    com.matthewcasperson.elidetest.jpa.Parent women = new com.matthewcasperson.elidetest.jpa.Parent();
    women.setChildren(Arrays.asList(alice, bob));
    parentRepository.save(women);

    com.matthewcasperson.elidetest.jpa.Parent two = new com.matthewcasperson.elidetest.jpa.Parent();
    two.setChildren(Arrays.asList(bob));
    parentRepository.save(two);
  }

  @CrossOrigin(origins = "*")
  @RequestMapping(
          method = RequestMethod.GET,
          produces = MediaType.APPLICATION_JSON_VALUE,
          value = {"/{entity}", "/{entity}/{id}/relationships/{entity2}", "/{entity}/{id}/{child}", "/{entity}/{id}"})
  @Transactional
  public String jsonApiGet(@RequestParam final Map<String, String> allRequestParams, final HttpServletRequest request, final Principal authentication) {

    ElideResponse response = elide.get(
            getJsonApiPath(request),
            new MultivaluedHashMap<>(allRequestParams),
            authentication);
    return response.getBody().toString();
  }

  private ResponseEntity<String> wrapResponse(ElideResponse response) {
    return ResponseEntity.status(response.getResponseCode()).body(response.getBody());
  }

  private String getJsonApiPath(HttpServletRequest request) {
    return ((String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE));
  }
}
