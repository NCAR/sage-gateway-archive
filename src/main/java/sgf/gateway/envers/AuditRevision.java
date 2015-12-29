package sgf.gateway.envers;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.safehaus.uuid.UUID;

import java.io.Serializable;
import java.util.Date;

@RevisionEntity(EnversRevisionListener.class)
public class AuditRevision implements Serializable {

    private static final long serialVersionUID = 1L;

    @RevisionNumber
    private long id;

    @RevisionTimestamp
    private Date revisionDate;

    private UUID userId;

    private String username;

    private String openId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openid) {
        this.openId = openid;
    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof AuditRevision)) return false;

        AuditRevision that = (AuditRevision) o;

        if (id != that.id) return false;
        return revisionDate.equals(that.revisionDate);

    }

    @Override
    public int hashCode() {

        int result = 31 * (int) id + (int) (revisionDate.getTime() ^ (revisionDate.getTime() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "AuditRevision(id = " + id + ", revisionDate = " + revisionDate + ")";
    }
}
