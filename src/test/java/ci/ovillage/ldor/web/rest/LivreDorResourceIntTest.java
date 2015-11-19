package ci.ovillage.ldor.web.rest;

import ci.ovillage.ldor.Application;
import ci.ovillage.ldor.domain.LivreDor;
import ci.ovillage.ldor.repository.LivreDorRepository;
import ci.ovillage.ldor.repository.search.LivreDorSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LivreDorResource REST controller.
 *
 * @see LivreDorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LivreDorResourceIntTest {

    private static final String DEFAULT_MESSAGE = "AAAAA";
    private static final String UPDATED_MESSAGE = "BBBBB";
    private static final String DEFAULT_NOM_VISITEUR = "AAAAA";
    private static final String UPDATED_NOM_VISITEUR = "BBBBB";
    private static final String DEFAULT_PRENOM_VISITEUR = "AAAAA";
    private static final String UPDATED_PRENOM_VISITEUR = "BBBBB";
    private static final String DEFAULT_CONTACT = "AAAAA";
    private static final String UPDATED_CONTACT = "BBBBB";
    private static final String DEFAULT_TWITTER = "AAAAA";
    private static final String UPDATED_TWITTER = "BBBBB";
    private static final String DEFAULT_MAIL = "AAAAA";
    private static final String UPDATED_MAIL = "BBBBB";

    private static final LocalDate DEFAULT_DATE_VISITE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VISITE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private LivreDorRepository livreDorRepository;

    @Inject
    private LivreDorSearchRepository livreDorSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLivreDorMockMvc;

    private LivreDor livreDor;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LivreDorResource livreDorResource = new LivreDorResource();
        ReflectionTestUtils.setField(livreDorResource, "livreDorRepository", livreDorRepository);
        ReflectionTestUtils.setField(livreDorResource, "livreDorSearchRepository", livreDorSearchRepository);
        this.restLivreDorMockMvc = MockMvcBuilders.standaloneSetup(livreDorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        livreDor = new LivreDor();
        livreDor.setMessage(DEFAULT_MESSAGE);
        livreDor.setNomVisiteur(DEFAULT_NOM_VISITEUR);
        livreDor.setPrenomVisiteur(DEFAULT_PRENOM_VISITEUR);
        livreDor.setContact(DEFAULT_CONTACT);
        livreDor.setTwitter(DEFAULT_TWITTER);
        livreDor.setMail(DEFAULT_MAIL);
        livreDor.setDateVisite(DEFAULT_DATE_VISITE);
    }

    @Test
    @Transactional
    public void createLivreDor() throws Exception {
        int databaseSizeBeforeCreate = livreDorRepository.findAll().size();

        // Create the LivreDor

        restLivreDorMockMvc.perform(post("/api/livreDors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livreDor)))
                .andExpect(status().isCreated());

        // Validate the LivreDor in the database
        List<LivreDor> livreDors = livreDorRepository.findAll();
        assertThat(livreDors).hasSize(databaseSizeBeforeCreate + 1);
        LivreDor testLivreDor = livreDors.get(livreDors.size() - 1);
        assertThat(testLivreDor.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testLivreDor.getNomVisiteur()).isEqualTo(DEFAULT_NOM_VISITEUR);
        assertThat(testLivreDor.getPrenomVisiteur()).isEqualTo(DEFAULT_PRENOM_VISITEUR);
        assertThat(testLivreDor.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testLivreDor.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testLivreDor.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testLivreDor.getDateVisite()).isEqualTo(DEFAULT_DATE_VISITE);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = livreDorRepository.findAll().size();
        // set the field null
        livreDor.setMessage(null);

        // Create the LivreDor, which fails.

        restLivreDorMockMvc.perform(post("/api/livreDors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livreDor)))
                .andExpect(status().isBadRequest());

        List<LivreDor> livreDors = livreDorRepository.findAll();
        assertThat(livreDors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomVisiteurIsRequired() throws Exception {
        int databaseSizeBeforeTest = livreDorRepository.findAll().size();
        // set the field null
        livreDor.setNomVisiteur(null);

        // Create the LivreDor, which fails.

        restLivreDorMockMvc.perform(post("/api/livreDors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livreDor)))
                .andExpect(status().isBadRequest());

        List<LivreDor> livreDors = livreDorRepository.findAll();
        assertThat(livreDors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomVisiteurIsRequired() throws Exception {
        int databaseSizeBeforeTest = livreDorRepository.findAll().size();
        // set the field null
        livreDor.setPrenomVisiteur(null);

        // Create the LivreDor, which fails.

        restLivreDorMockMvc.perform(post("/api/livreDors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livreDor)))
                .andExpect(status().isBadRequest());

        List<LivreDor> livreDors = livreDorRepository.findAll();
        assertThat(livreDors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkContactIsRequired() throws Exception {
        int databaseSizeBeforeTest = livreDorRepository.findAll().size();
        // set the field null
        livreDor.setContact(null);

        // Create the LivreDor, which fails.

        restLivreDorMockMvc.perform(post("/api/livreDors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livreDor)))
                .andExpect(status().isBadRequest());

        List<LivreDor> livreDors = livreDorRepository.findAll();
        assertThat(livreDors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateVisiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = livreDorRepository.findAll().size();
        // set the field null
        livreDor.setDateVisite(null);

        // Create the LivreDor, which fails.

        restLivreDorMockMvc.perform(post("/api/livreDors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livreDor)))
                .andExpect(status().isBadRequest());

        List<LivreDor> livreDors = livreDorRepository.findAll();
        assertThat(livreDors).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLivreDors() throws Exception {
        // Initialize the database
        livreDorRepository.saveAndFlush(livreDor);

        // Get all the livreDors
        restLivreDorMockMvc.perform(get("/api/livreDors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(livreDor.getId().intValue())))
                .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
                .andExpect(jsonPath("$.[*].nomVisiteur").value(hasItem(DEFAULT_NOM_VISITEUR.toString())))
                .andExpect(jsonPath("$.[*].prenomVisiteur").value(hasItem(DEFAULT_PRENOM_VISITEUR.toString())))
                .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT.toString())))
                .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.toString())))
                .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())))
                .andExpect(jsonPath("$.[*].dateVisite").value(hasItem(DEFAULT_DATE_VISITE.toString())));
    }

    @Test
    @Transactional
    public void getLivreDor() throws Exception {
        // Initialize the database
        livreDorRepository.saveAndFlush(livreDor);

        // Get the livreDor
        restLivreDorMockMvc.perform(get("/api/livreDors/{id}", livreDor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(livreDor.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.nomVisiteur").value(DEFAULT_NOM_VISITEUR.toString()))
            .andExpect(jsonPath("$.prenomVisiteur").value(DEFAULT_PRENOM_VISITEUR.toString()))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT.toString()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()))
            .andExpect(jsonPath("$.dateVisite").value(DEFAULT_DATE_VISITE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLivreDor() throws Exception {
        // Get the livreDor
        restLivreDorMockMvc.perform(get("/api/livreDors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLivreDor() throws Exception {
        // Initialize the database
        livreDorRepository.saveAndFlush(livreDor);

		int databaseSizeBeforeUpdate = livreDorRepository.findAll().size();

        // Update the livreDor
        livreDor.setMessage(UPDATED_MESSAGE);
        livreDor.setNomVisiteur(UPDATED_NOM_VISITEUR);
        livreDor.setPrenomVisiteur(UPDATED_PRENOM_VISITEUR);
        livreDor.setContact(UPDATED_CONTACT);
        livreDor.setTwitter(UPDATED_TWITTER);
        livreDor.setMail(UPDATED_MAIL);
        livreDor.setDateVisite(UPDATED_DATE_VISITE);

        restLivreDorMockMvc.perform(put("/api/livreDors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(livreDor)))
                .andExpect(status().isOk());

        // Validate the LivreDor in the database
        List<LivreDor> livreDors = livreDorRepository.findAll();
        assertThat(livreDors).hasSize(databaseSizeBeforeUpdate);
        LivreDor testLivreDor = livreDors.get(livreDors.size() - 1);
        assertThat(testLivreDor.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testLivreDor.getNomVisiteur()).isEqualTo(UPDATED_NOM_VISITEUR);
        assertThat(testLivreDor.getPrenomVisiteur()).isEqualTo(UPDATED_PRENOM_VISITEUR);
        assertThat(testLivreDor.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testLivreDor.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testLivreDor.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testLivreDor.getDateVisite()).isEqualTo(UPDATED_DATE_VISITE);
    }

    @Test
    @Transactional
    public void deleteLivreDor() throws Exception {
        // Initialize the database
        livreDorRepository.saveAndFlush(livreDor);

		int databaseSizeBeforeDelete = livreDorRepository.findAll().size();

        // Get the livreDor
        restLivreDorMockMvc.perform(delete("/api/livreDors/{id}", livreDor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<LivreDor> livreDors = livreDorRepository.findAll();
        assertThat(livreDors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
