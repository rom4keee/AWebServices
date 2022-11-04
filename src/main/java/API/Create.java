package API;

import java.util.Date;

public class Create {
    private String name;
    private String job;
    private String id;
    private Date createdAt;

    public Create() {
    }
    public String getName() {
        return name;
    }
    public String getJob() {
        return job;
    }
    public String getId() {
        return id;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
}
