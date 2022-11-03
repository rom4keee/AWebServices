package API;

import java.util.Date;

public class Update {
    private String name;
    private String job;
    private Date updatedAt;
    
    public Update() {
    }
    public Update(String name, String job) {
        this.name = name;
        this.job = job;
    }


    public Update(String name, String job, Date updatedAt) {
        this.name = name;
        this.job = job;
        this.updatedAt = updatedAt;
    }
    public String getName() {
        return name;
    }
    public String getJob() {
        return job;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
}
