package org.onlinetaskforce.common.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * This is the abstract base class for all domain pojo's that contain audit information.

 */
@MappedSuperclass
public abstract class AbstractAuditPojo extends AbstractDomainPojo {

    /**
     * Audit information: the time at which this object is made persistent.
     */
    @Type(type = "org.onlinetaskforce.persistence.types.UtcTimestampType")
    @Column(name = "creatieTijdstipUtc", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creatieTijdstip;
    /**
     * Audit information: the time at which this object is last updated.
     */
    @Type(type = "org.onlinetaskforce.persistence.types.UtcTimestampType")
    @Column(name = "wijzigingsTijdstipUtc", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date wijzigingsTijdstip;
    /**
     * Audit information: the application user that created this persistent object.
     */
    @Column(name="creatieGebruiker_id", nullable=true)
    private String creatieGebruikerId;
    /**
     * Audit information: the application user that last updated this persistent object.
     */
    @Column(name="wijzigingsGebruiker_id", nullable=true)
    private String wijzigingsGebruikerId;

    public Date getCreatieTijdstip() {
        return creatieTijdstip;
    }

    public void setCreatieTijdstip(Date creatieTijdstip) {
        this.creatieTijdstip = creatieTijdstip;
    }

    public Date getWijzigingsTijdstip() {
        return wijzigingsTijdstip;
    }

    public void setWijzigingsTijdstip(Date wijzigingsTijdstip) {
        this.wijzigingsTijdstip = wijzigingsTijdstip;
    }

    public String getCreatieGebruikerId() {
        return creatieGebruikerId;
    }

    public void setCreatieGebruikerId(String creatieGebruikerId) {
        this.creatieGebruikerId = creatieGebruikerId;
    }

    public String getWijzigingsGebruikerId() {
        return wijzigingsGebruikerId;
    }

    public void setWijzigingsGebruikerId(String wijzigingsGebruikerId) {
        this.wijzigingsGebruikerId = wijzigingsGebruikerId;
    }
}
