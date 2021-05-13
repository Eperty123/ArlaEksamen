package BE;

import java.time.LocalDate;

public class Bug {
    private String description;
    private String dateReported;
    private User adminResponsible;

    public Bug(String description, String dateReported){
        this.description = description;
        this.dateReported = dateReported;
        adminResponsible = null;
    }
    public Bug(String description, String dateReported, User adminResponsible){
        this.description = description;
        this.dateReported = dateReported;
        this.adminResponsible = adminResponsible;
    }

    public String getDescription() {
        return description;
    }

    public String getDateReported() {
        return dateReported;
    }

    public User getAdminResponsible() {
        return adminResponsible;
    }

    public void setAdminResponsible(User adminResponsible) {
        this.adminResponsible = adminResponsible;
    }
}
