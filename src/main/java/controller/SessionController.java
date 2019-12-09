package controller;

import Utils.Manager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SessionController {
    @RequestMapping(value = "/sites_number/{visitorId}", method = RequestMethod.GET)
    public ResponseEntity getSitesNumberByVisitorId(@PathVariable(value="visitorId") String visitorId) {
        Integer numOfSites = Manager.getNumSitesByVisitorId(visitorId);
        return numOfSites == null ?
                ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("visitor \"%s\" not found", visitorId)):
                ResponseEntity.ok(numOfSites);
    }

    @RequestMapping(value = "/sessions_number/{siteUrl}", method = RequestMethod.GET)
    public ResponseEntity getSessionsNumberBySiteUrl(@PathVariable(value="siteUrl") String siteUrl) {
        Integer numOfSessions = Manager.getSessionsNumberBySiteUrl(siteUrl);
        return numOfSessions == null ?
                ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("site_url \"%s\" not found", siteUrl)):
                ResponseEntity.ok(numOfSessions);
    }

    @RequestMapping(value = "/median/{siteUrl}", method = RequestMethod.GET)
    public ResponseEntity getMedianBySiteUrl(@PathVariable(value="siteUrl") String siteUrl) {
        Double medianValue = Manager.getMedianSessionsBySiteUrl(siteUrl);
        return medianValue == null ?
                ResponseEntity.status(HttpStatus.FORBIDDEN).body(String.format("site_url \"%s\" not found", siteUrl)):
                ResponseEntity.ok(medianValue);
    }

}
