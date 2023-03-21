package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.GenericTimeline} entity.
 */
public class GenericTimelineDTO implements Serializable {

    private Long id;

    private Boolean needTaskH;

    private Boolean loopEnter;

    private String chooseTask;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getNeedTaskH() {
        return needTaskH;
    }

    public void setNeedTaskH(Boolean needTaskH) {
        this.needTaskH = needTaskH;
    }

    public Boolean getLoopEnter() {
        return loopEnter;
    }

    public void setLoopEnter(Boolean loopEnter) {
        this.loopEnter = loopEnter;
    }

    public String getChooseTask() {
        return chooseTask;
    }

    public void setChooseTask(String chooseTask) {
        this.chooseTask = chooseTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenericTimelineDTO)) {
            return false;
        }

        GenericTimelineDTO genericTimelineDTO = (GenericTimelineDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, genericTimelineDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenericTimelineDTO{" +
            "id=" + getId() +
            ", needTaskH='" + getNeedTaskH() + "'" +
            ", loopEnter='" + getLoopEnter() + "'" +
            ", chooseTask='" + getChooseTask() + "'" +
            "}";
    }
}
