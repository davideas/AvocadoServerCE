package eu.davidea.avocadoserver.infrastructure.audit;

import java.util.Date;

/**
 * Models an entity that has audit fields which are to be set by the persistence library.
 *
 * @author Davide Steduto
 * @since 15/08/2016
 */
public interface AuditableEntity {

    Date getCreDate();

    void setCreDate(Date creDate);

    Date getModDate();

    void setModDate(Date modDate);

}