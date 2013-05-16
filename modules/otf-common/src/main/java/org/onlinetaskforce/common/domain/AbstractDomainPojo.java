package org.onlinetaskforce.common.domain;

import org.onlinetaskforce.common.utils.IdGenerator;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * This is the abstract base class for all domain pojo's of the otf project.
 */
@MappedSuperclass
public abstract class AbstractDomainPojo implements Serializable {

    @Id
    private String id = IdGenerator.createId();

    /**
     * The current version of this persistent object. This version is managed by Hibernate.
     */
    @Version
    @Column(name = "versie", nullable = false)
    private Long versie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersie() {
        return versie;
    }

    public void setVersie(Long versie) {
        this.versie = versie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractDomainPojo)) {
            return false;
        }
        AbstractDomainPojo other = (AbstractDomainPojo)o;
        // if the id is missing, return false
        if (id == null) {
            return false;
        }

        // equivalence by id
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName()
            + "[id=" + id + "]";
    }
}
