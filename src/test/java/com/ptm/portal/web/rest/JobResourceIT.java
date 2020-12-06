package com.ptm.portal.web.rest;

import com.ptm.portal.Ptm01App;
import com.ptm.portal.domain.Job;
import com.ptm.portal.repository.JobRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.ptm.portal.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link JobResource} REST controller.
 */
@SpringBootTest(classes = Ptm01App.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class JobResourceIT {

    private static final String DEFAULT_JOB_ID = "AAAAAAAAAA";
    private static final String UPDATED_JOB_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_COMPLETED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_COMPLETED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_REPORTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REPORTED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_REPORTED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_REPORTED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CLIENT_ORDER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ORDER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRIORITY = 1;
    private static final Integer UPDATED_PRIORITY = 2;

    private static final String DEFAULT_FAULT = "AAAAAAAAAA";
    private static final String UPDATED_FAULT = "BBBBBBBBBB";

    private static final String DEFAULT_ACCESS_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_ACCESS_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPIERS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPIERS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPIERS_HOME_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPIERS_HOME_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPIERS_WORK_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPIERS_WORK_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPIERS_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPIERS_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_OCCUPIERS_TENANT_SIGNATURE = "AAAAAAAAAA";
    private static final String UPDATED_OCCUPIERS_TENANT_SIGNATURE = "BBBBBBBBBB";

    @Autowired
    private JobRepository jobRepository;

    @Mock
    private JobRepository jobRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobMockMvc;

    private Job job;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Job createEntity(EntityManager em) {
        Job job = new Job()
            .jobId(DEFAULT_JOB_ID)
            .completed(DEFAULT_COMPLETED)
            .reportedBy(DEFAULT_REPORTED_BY)
            .reportedOn(DEFAULT_REPORTED_ON)
            .clientOrderId(DEFAULT_CLIENT_ORDER_ID)
            .priority(DEFAULT_PRIORITY)
            .fault(DEFAULT_FAULT)
            .accessInstructions(DEFAULT_ACCESS_INSTRUCTIONS)
            .address(DEFAULT_ADDRESS)
            .occupiersName(DEFAULT_OCCUPIERS_NAME)
            .occupiersHomePhone(DEFAULT_OCCUPIERS_HOME_PHONE)
            .occupiersWorkPhone(DEFAULT_OCCUPIERS_WORK_PHONE)
            .occupiersMobilePhone(DEFAULT_OCCUPIERS_MOBILE_PHONE)
            .occupiersTenantSignature(DEFAULT_OCCUPIERS_TENANT_SIGNATURE);
        return job;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Job createUpdatedEntity(EntityManager em) {
        Job job = new Job()
            .jobId(UPDATED_JOB_ID)
            .completed(UPDATED_COMPLETED)
            .reportedBy(UPDATED_REPORTED_BY)
            .reportedOn(UPDATED_REPORTED_ON)
            .clientOrderId(UPDATED_CLIENT_ORDER_ID)
            .priority(UPDATED_PRIORITY)
            .fault(UPDATED_FAULT)
            .accessInstructions(UPDATED_ACCESS_INSTRUCTIONS)
            .address(UPDATED_ADDRESS)
            .occupiersName(UPDATED_OCCUPIERS_NAME)
            .occupiersHomePhone(UPDATED_OCCUPIERS_HOME_PHONE)
            .occupiersWorkPhone(UPDATED_OCCUPIERS_WORK_PHONE)
            .occupiersMobilePhone(UPDATED_OCCUPIERS_MOBILE_PHONE)
            .occupiersTenantSignature(UPDATED_OCCUPIERS_TENANT_SIGNATURE);
        return job;
    }

    @BeforeEach
    public void initTest() {
        job = createEntity(em);
    }

    @Test
    @Transactional
    public void createJob() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();
        // Create the Job
        restJobMockMvc.perform(post("/api/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isCreated());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate + 1);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getJobId()).isEqualTo(DEFAULT_JOB_ID);
        assertThat(testJob.getCompleted()).isEqualTo(DEFAULT_COMPLETED);
        assertThat(testJob.getReportedBy()).isEqualTo(DEFAULT_REPORTED_BY);
        assertThat(testJob.getReportedOn()).isEqualTo(DEFAULT_REPORTED_ON);
        assertThat(testJob.getClientOrderId()).isEqualTo(DEFAULT_CLIENT_ORDER_ID);
        assertThat(testJob.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testJob.getFault()).isEqualTo(DEFAULT_FAULT);
        assertThat(testJob.getAccessInstructions()).isEqualTo(DEFAULT_ACCESS_INSTRUCTIONS);
        assertThat(testJob.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testJob.getOccupiersName()).isEqualTo(DEFAULT_OCCUPIERS_NAME);
        assertThat(testJob.getOccupiersHomePhone()).isEqualTo(DEFAULT_OCCUPIERS_HOME_PHONE);
        assertThat(testJob.getOccupiersWorkPhone()).isEqualTo(DEFAULT_OCCUPIERS_WORK_PHONE);
        assertThat(testJob.getOccupiersMobilePhone()).isEqualTo(DEFAULT_OCCUPIERS_MOBILE_PHONE);
        assertThat(testJob.getOccupiersTenantSignature()).isEqualTo(DEFAULT_OCCUPIERS_TENANT_SIGNATURE);
    }

    @Test
    @Transactional
    public void createJobWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobRepository.findAll().size();

        // Create the Job with an existing ID
        job.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobMockMvc.perform(post("/api/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllJobs() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get all the jobList
        restJobMockMvc.perform(get("/api/jobs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(job.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobId").value(hasItem(DEFAULT_JOB_ID)))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(sameInstant(DEFAULT_COMPLETED))))
            .andExpect(jsonPath("$.[*].reportedBy").value(hasItem(DEFAULT_REPORTED_BY)))
            .andExpect(jsonPath("$.[*].reportedOn").value(hasItem(sameInstant(DEFAULT_REPORTED_ON))))
            .andExpect(jsonPath("$.[*].clientOrderId").value(hasItem(DEFAULT_CLIENT_ORDER_ID)))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].fault").value(hasItem(DEFAULT_FAULT)))
            .andExpect(jsonPath("$.[*].accessInstructions").value(hasItem(DEFAULT_ACCESS_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].occupiersName").value(hasItem(DEFAULT_OCCUPIERS_NAME)))
            .andExpect(jsonPath("$.[*].occupiersHomePhone").value(hasItem(DEFAULT_OCCUPIERS_HOME_PHONE)))
            .andExpect(jsonPath("$.[*].occupiersWorkPhone").value(hasItem(DEFAULT_OCCUPIERS_WORK_PHONE)))
            .andExpect(jsonPath("$.[*].occupiersMobilePhone").value(hasItem(DEFAULT_OCCUPIERS_MOBILE_PHONE)))
            .andExpect(jsonPath("$.[*].occupiersTenantSignature").value(hasItem(DEFAULT_OCCUPIERS_TENANT_SIGNATURE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllJobsWithEagerRelationshipsIsEnabled() throws Exception {
        when(jobRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobMockMvc.perform(get("/api/jobs?eagerload=true"))
            .andExpect(status().isOk());

        verify(jobRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllJobsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(jobRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restJobMockMvc.perform(get("/api/jobs?eagerload=true"))
            .andExpect(status().isOk());

        verify(jobRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", job.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(job.getId().intValue()))
            .andExpect(jsonPath("$.jobId").value(DEFAULT_JOB_ID))
            .andExpect(jsonPath("$.completed").value(sameInstant(DEFAULT_COMPLETED)))
            .andExpect(jsonPath("$.reportedBy").value(DEFAULT_REPORTED_BY))
            .andExpect(jsonPath("$.reportedOn").value(sameInstant(DEFAULT_REPORTED_ON)))
            .andExpect(jsonPath("$.clientOrderId").value(DEFAULT_CLIENT_ORDER_ID))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.fault").value(DEFAULT_FAULT))
            .andExpect(jsonPath("$.accessInstructions").value(DEFAULT_ACCESS_INSTRUCTIONS))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.occupiersName").value(DEFAULT_OCCUPIERS_NAME))
            .andExpect(jsonPath("$.occupiersHomePhone").value(DEFAULT_OCCUPIERS_HOME_PHONE))
            .andExpect(jsonPath("$.occupiersWorkPhone").value(DEFAULT_OCCUPIERS_WORK_PHONE))
            .andExpect(jsonPath("$.occupiersMobilePhone").value(DEFAULT_OCCUPIERS_MOBILE_PHONE))
            .andExpect(jsonPath("$.occupiersTenantSignature").value(DEFAULT_OCCUPIERS_TENANT_SIGNATURE));
    }
    @Test
    @Transactional
    public void getNonExistingJob() throws Exception {
        // Get the job
        restJobMockMvc.perform(get("/api/jobs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // Update the job
        Job updatedJob = jobRepository.findById(job.getId()).get();
        // Disconnect from session so that the updates on updatedJob are not directly saved in db
        em.detach(updatedJob);
        updatedJob
            .jobId(UPDATED_JOB_ID)
            .completed(UPDATED_COMPLETED)
            .reportedBy(UPDATED_REPORTED_BY)
            .reportedOn(UPDATED_REPORTED_ON)
            .clientOrderId(UPDATED_CLIENT_ORDER_ID)
            .priority(UPDATED_PRIORITY)
            .fault(UPDATED_FAULT)
            .accessInstructions(UPDATED_ACCESS_INSTRUCTIONS)
            .address(UPDATED_ADDRESS)
            .occupiersName(UPDATED_OCCUPIERS_NAME)
            .occupiersHomePhone(UPDATED_OCCUPIERS_HOME_PHONE)
            .occupiersWorkPhone(UPDATED_OCCUPIERS_WORK_PHONE)
            .occupiersMobilePhone(UPDATED_OCCUPIERS_MOBILE_PHONE)
            .occupiersTenantSignature(UPDATED_OCCUPIERS_TENANT_SIGNATURE);

        restJobMockMvc.perform(put("/api/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedJob)))
            .andExpect(status().isOk());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
        Job testJob = jobList.get(jobList.size() - 1);
        assertThat(testJob.getJobId()).isEqualTo(UPDATED_JOB_ID);
        assertThat(testJob.getCompleted()).isEqualTo(UPDATED_COMPLETED);
        assertThat(testJob.getReportedBy()).isEqualTo(UPDATED_REPORTED_BY);
        assertThat(testJob.getReportedOn()).isEqualTo(UPDATED_REPORTED_ON);
        assertThat(testJob.getClientOrderId()).isEqualTo(UPDATED_CLIENT_ORDER_ID);
        assertThat(testJob.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testJob.getFault()).isEqualTo(UPDATED_FAULT);
        assertThat(testJob.getAccessInstructions()).isEqualTo(UPDATED_ACCESS_INSTRUCTIONS);
        assertThat(testJob.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testJob.getOccupiersName()).isEqualTo(UPDATED_OCCUPIERS_NAME);
        assertThat(testJob.getOccupiersHomePhone()).isEqualTo(UPDATED_OCCUPIERS_HOME_PHONE);
        assertThat(testJob.getOccupiersWorkPhone()).isEqualTo(UPDATED_OCCUPIERS_WORK_PHONE);
        assertThat(testJob.getOccupiersMobilePhone()).isEqualTo(UPDATED_OCCUPIERS_MOBILE_PHONE);
        assertThat(testJob.getOccupiersTenantSignature()).isEqualTo(UPDATED_OCCUPIERS_TENANT_SIGNATURE);
    }

    @Test
    @Transactional
    public void updateNonExistingJob() throws Exception {
        int databaseSizeBeforeUpdate = jobRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobMockMvc.perform(put("/api/jobs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(job)))
            .andExpect(status().isBadRequest());

        // Validate the Job in the database
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteJob() throws Exception {
        // Initialize the database
        jobRepository.saveAndFlush(job);

        int databaseSizeBeforeDelete = jobRepository.findAll().size();

        // Delete the job
        restJobMockMvc.perform(delete("/api/jobs/{id}", job.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Job> jobList = jobRepository.findAll();
        assertThat(jobList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
