package com.ptm.portal.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "job_id")
    private String jobId;

    @Column(name = "completed")
    private ZonedDateTime completed;

    @Column(name = "reported_by")
    private String reportedBy;

    @Column(name = "reported_on")
    private ZonedDateTime reportedOn;

    @Column(name = "client_order_id")
    private String clientOrderId;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "fault")
    private String fault;

    @Column(name = "access_instructions")
    private String accessInstructions;

    @Column(name = "address")
    private String address;

    @Column(name = "occupiers_name")
    private String occupiersName;

    @Column(name = "occupiers_home_phone")
    private String occupiersHomePhone;

    @Column(name = "occupiers_work_phone")
    private String occupiersWorkPhone;

    @Column(name = "occupiers_mobile_phone")
    private String occupiersMobilePhone;

    @Column(name = "occupiers_tenant_signature")
    private String occupiersTenantSignature;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "job_client",
               joinColumns = @JoinColumn(name = "job_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"))
    private Set<Client> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public Job jobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public ZonedDateTime getCompleted() {
        return completed;
    }

    public Job completed(ZonedDateTime completed) {
        this.completed = completed;
        return this;
    }

    public void setCompleted(ZonedDateTime completed) {
        this.completed = completed;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public Job reportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
        return this;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public ZonedDateTime getReportedOn() {
        return reportedOn;
    }

    public Job reportedOn(ZonedDateTime reportedOn) {
        this.reportedOn = reportedOn;
        return this;
    }

    public void setReportedOn(ZonedDateTime reportedOn) {
        this.reportedOn = reportedOn;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public Job clientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
        return this;
    }

    public void setClientOrderId(String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public Integer getPriority() {
        return priority;
    }

    public Job priority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getFault() {
        return fault;
    }

    public Job fault(String fault) {
        this.fault = fault;
        return this;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public String getAccessInstructions() {
        return accessInstructions;
    }

    public Job accessInstructions(String accessInstructions) {
        this.accessInstructions = accessInstructions;
        return this;
    }

    public void setAccessInstructions(String accessInstructions) {
        this.accessInstructions = accessInstructions;
    }

    public String getAddress() {
        return address;
    }

    public Job address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupiersName() {
        return occupiersName;
    }

    public Job occupiersName(String occupiersName) {
        this.occupiersName = occupiersName;
        return this;
    }

    public void setOccupiersName(String occupiersName) {
        this.occupiersName = occupiersName;
    }

    public String getOccupiersHomePhone() {
        return occupiersHomePhone;
    }

    public Job occupiersHomePhone(String occupiersHomePhone) {
        this.occupiersHomePhone = occupiersHomePhone;
        return this;
    }

    public void setOccupiersHomePhone(String occupiersHomePhone) {
        this.occupiersHomePhone = occupiersHomePhone;
    }

    public String getOccupiersWorkPhone() {
        return occupiersWorkPhone;
    }

    public Job occupiersWorkPhone(String occupiersWorkPhone) {
        this.occupiersWorkPhone = occupiersWorkPhone;
        return this;
    }

    public void setOccupiersWorkPhone(String occupiersWorkPhone) {
        this.occupiersWorkPhone = occupiersWorkPhone;
    }

    public String getOccupiersMobilePhone() {
        return occupiersMobilePhone;
    }

    public Job occupiersMobilePhone(String occupiersMobilePhone) {
        this.occupiersMobilePhone = occupiersMobilePhone;
        return this;
    }

    public void setOccupiersMobilePhone(String occupiersMobilePhone) {
        this.occupiersMobilePhone = occupiersMobilePhone;
    }

    public String getOccupiersTenantSignature() {
        return occupiersTenantSignature;
    }

    public Job occupiersTenantSignature(String occupiersTenantSignature) {
        this.occupiersTenantSignature = occupiersTenantSignature;
        return this;
    }

    public void setOccupiersTenantSignature(String occupiersTenantSignature) {
        this.occupiersTenantSignature = occupiersTenantSignature;
    }

    public Set<Client> getClients() {
        return clients;
    }

    public Job clients(Set<Client> clients) {
        this.clients = clients;
        return this;
    }

    public Job addClient(Client client) {
        this.clients.add(client);
        client.getNames().add(this);
        return this;
    }

    public Job removeClient(Client client) {
        this.clients.remove(client);
        client.getNames().remove(this);
        return this;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Job)) {
            return false;
        }
        return id != null && id.equals(((Job) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Job{" +
            "id=" + getId() +
            ", jobId='" + getJobId() + "'" +
            ", completed='" + getCompleted() + "'" +
            ", reportedBy='" + getReportedBy() + "'" +
            ", reportedOn='" + getReportedOn() + "'" +
            ", clientOrderId='" + getClientOrderId() + "'" +
            ", priority=" + getPriority() +
            ", fault='" + getFault() + "'" +
            ", accessInstructions='" + getAccessInstructions() + "'" +
            ", address='" + getAddress() + "'" +
            ", occupiersName='" + getOccupiersName() + "'" +
            ", occupiersHomePhone='" + getOccupiersHomePhone() + "'" +
            ", occupiersWorkPhone='" + getOccupiersWorkPhone() + "'" +
            ", occupiersMobilePhone='" + getOccupiersMobilePhone() + "'" +
            ", occupiersTenantSignature='" + getOccupiersTenantSignature() + "'" +
            "}";
    }
}
