package MediaPoolMalBridge.persistence.entity;

import javax.persistence.*;

@Entity
@Table( indexes = {@Index(columnList = "salId"), @Index(columnList = "bmId")})
public class MALIdToBMIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String salId;

    private String bmId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSalId() {
        return salId;
    }

    public void setSalId(String salId) {
        this.salId = salId;
    }

    public String getBmId() {
        return bmId;
    }

    public void setBmId(String bmId) {
        this.bmId = bmId;
    }
}
