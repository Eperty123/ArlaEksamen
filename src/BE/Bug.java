package BE;

import GUI.Model.ScreenModel;
import GUI.Model.UserModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bug {

    private int id;
    private String description;
    private String dateReported;
    private boolean bugResolved;
    private User adminResponsible;
    private ScreenBit referencedScreen;

    public Bug() {
    }

    public Bug(String description, String dateReported) {
        setDescription(description);
        setDateReported(dateReported);
        adminResponsible = null;
    }

    public Bug(int id, String description, String dateReported, User adminResponsible) {
        setId(id);
        setDescription(description);
        setDateReported(dateReported);
        setAdminResponsible(adminResponsible);
    }

    public Bug(int id, String description, String dateReported, int bugResolved, int userId, int screenId) {
        setId(id);
        setDescription(description);
        setDateReported(dateReported);
        setAdminResponsible(UserModel.getInstance().getUser(userId));
        setBugResolved(bugResolved);
        setReferencedScreen(ScreenModel.getInstance().getScreen(screenId));
    }

    public Bug(String description, String dateReported, int bugResolved, int userId, int screenId) {
        this.description = description;
        this.dateReported = dateReported;
        setAdminResponsible(UserModel.getInstance().getUser(userId));
        setBugResolved(bugResolved);
        setReferencedScreen(ScreenModel.getInstance().getScreen(screenId));
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

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateReported(LocalDate dateReported) {
        setDateReported(dateReported.toString());
    }
    
    public void setDateReported(LocalDateTime dateReported) {
        setDateReported(dateReported.toString());
    }

    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    public boolean isBugResolved() {
        return bugResolved;
    }

    public void setBugResolved(boolean bugResolved) {
        this.bugResolved = bugResolved;
    }

    public void setBugResolved(int bugResolved) {
        this.bugResolved = bugResolved > 0 ? true : false;
    }

    public ScreenBit getReferencedScreen() {
        return referencedScreen;
    }

    public void setReferencedScreen(ScreenBit referencedScreen) {
        this.referencedScreen = referencedScreen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
