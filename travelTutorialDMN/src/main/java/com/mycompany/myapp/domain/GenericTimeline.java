package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GenericTimeline.
 */
@Entity
@Table(name = "generic_timeline")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GenericTimeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "need_task_h")
    private Boolean needTaskH;

    @Column(name = "loop_enter")
    private Boolean loopEnter;

    @Column(name = "choose_task")
    private String chooseTask;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenericTimeline id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getNeedTaskH() {
        return this.needTaskH;
    }

    public GenericTimeline needTaskH(Boolean needTaskH) {
        this.needTaskH = needTaskH;
        return this;
    }

    public void setNeedTaskH(Boolean needTaskH) {
        this.needTaskH = needTaskH;
    }

    public Boolean getLoopEnter() {
        return this.loopEnter;
    }

    public GenericTimeline loopEnter(Boolean loopEnter) {
        this.loopEnter = loopEnter;
        return this;
    }

    public void setLoopEnter(Boolean loopEnter) {
        this.loopEnter = loopEnter;
    }

    public String getChooseTask() {
        return this.chooseTask;
    }

    public GenericTimeline chooseTask(String chooseTask) {
        this.chooseTask = chooseTask;
        return this;
    }

    public void setChooseTask(String chooseTask) {
        this.chooseTask = chooseTask;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenericTimeline)) {
            return false;
        }
        return id != null && id.equals(((GenericTimeline) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenericTimeline{" +
            "id=" + getId() +
            ", needTaskH='" + getNeedTaskH() + "'" +
            ", loopEnter='" + getLoopEnter() + "'" +
            ", chooseTask='" + getChooseTask() + "'" +
            "}";
    }
}
