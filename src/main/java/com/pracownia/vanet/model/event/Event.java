package com.pracownia.vanet.model.event;

import com.pracownia.vanet.model.devices.Device;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Event {

    /*------------------------ FIELDS REGION ------------------------*/
    private int id;
    private EventType eventType;
    private Date eventDate;
    private String message;
    private String routingPath;
    private Device source;
    private Device target;
    private boolean identityCheck = false;

    public Device getTarget() {
        return target;
    }

    /*------------------------ METHODS REGION ------------------------*/
    public Event(int id, Device source, Device target, Date eventDate, String message, String routingPath) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.eventDate = eventDate;
        this.message = message;
        this.routingPath = routingPath;
    }

    public boolean ifIdentityCheck() {
        return this.identityCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Event event = (Event) o;

        return new EqualsBuilder()
                .append(id, event.id)
                .append(target, event.target)
                .append(eventDate, event.eventDate)
                .append(message, event.message)
                .append(routingPath, event.routingPath)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(target)
                .append(eventDate)
                .append(message)
                .append(routingPath)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("targetId", target.getId())
                .append("eventDate", eventDate)
                .append("message", message)
                .append("routingPath", routingPath)
                .toString();
    }
}
    