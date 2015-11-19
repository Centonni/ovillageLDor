package ci.ovillage.ldor.web.rest;

import com.codahale.metrics.annotation.Timed;
import ci.ovillage.ldor.domain.LivreDor;
import ci.ovillage.ldor.repository.LivreDorRepository;
import ci.ovillage.ldor.repository.search.LivreDorSearchRepository;
import ci.ovillage.ldor.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing LivreDor.
 */
@RestController
@RequestMapping("/api")
public class LivreDorResource {

    private final Logger log = LoggerFactory.getLogger(LivreDorResource.class);

    @Inject
    private LivreDorRepository livreDorRepository;

    @Inject
    private LivreDorSearchRepository livreDorSearchRepository;

    /**
     * POST  /livreDors -> Create a new livreDor.
     */
    @RequestMapping(value = "/livreDors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LivreDor> createLivreDor(@Valid @RequestBody LivreDor livreDor) throws URISyntaxException {
        log.debug("REST request to save LivreDor : {}", livreDor);
        if (livreDor.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new livreDor cannot already have an ID").body(null);
        }
        LivreDor result = livreDorRepository.save(livreDor);
        livreDorSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/livreDors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("livreDor", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /livreDors -> Updates an existing livreDor.
     */
    @RequestMapping(value = "/livreDors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LivreDor> updateLivreDor(@Valid @RequestBody LivreDor livreDor) throws URISyntaxException {
        log.debug("REST request to update LivreDor : {}", livreDor);
        if (livreDor.getId() == null) {
            return createLivreDor(livreDor);
        }
        LivreDor result = livreDorRepository.save(livreDor);
        livreDorSearchRepository.save(livreDor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("livreDor", livreDor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /livreDors -> get all the livreDors.
     */
    @RequestMapping(value = "/livreDors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LivreDor> getAllLivreDors() {
        log.debug("REST request to get all LivreDors");
        return livreDorRepository.findAll();
    }

    /**
     * GET  /livreDors/:id -> get the "id" livreDor.
     */
    @RequestMapping(value = "/livreDors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LivreDor> getLivreDor(@PathVariable Long id) {
        log.debug("REST request to get LivreDor : {}", id);
        return Optional.ofNullable(livreDorRepository.findOne(id))
            .map(livreDor -> new ResponseEntity<>(
                livreDor,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /livreDors/:id -> delete the "id" livreDor.
     */
    @RequestMapping(value = "/livreDors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLivreDor(@PathVariable Long id) {
        log.debug("REST request to delete LivreDor : {}", id);
        livreDorRepository.delete(id);
        livreDorSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("livreDor", id.toString())).build();
    }

    /**
     * SEARCH  /_search/livreDors/:query -> search for the livreDor corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/livreDors/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LivreDor> searchLivreDors(@PathVariable String query) {
        return StreamSupport
            .stream(livreDorSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
