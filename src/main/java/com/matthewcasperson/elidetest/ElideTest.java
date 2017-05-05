package com.matthewcasperson.elidetest;

import com.yahoo.elide.Elide;
import com.yahoo.elide.ElideResponse;
import org.apache.tomcat.util.net.jsse.openssl.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedHashMap;
import java.util.Map;

/**
 * The rest interface to DZone and other services
 */
@RestController
public class ElideTest {

  @Autowired
  private Elide elide;
  

  @CrossOrigin(origins = "*")
  @RequestMapping(
          method = RequestMethod.GET,
          produces = MediaType.APPLICATION_JSON_VALUE,
          value = {"/{entity}", "/{entity}/{id}/relationships/{entity2}", "/{entity}/{id}/{child}", "/{entity}/{id}"})
  @Transactional
  public String jsonApiGet(@RequestParam final Map<String, String> allRequestParams, final HttpServletRequest request, final Authentication authentication) {
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
