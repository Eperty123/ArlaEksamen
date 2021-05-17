package BE;

import GUI.Model.ScreenModel;
import GUI.Model.UserModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bug {

    private int id;
    private String description;
    private String fixMessage;
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

    public Bug(int id, String description, String fixMessage, String dateReported, boolean bugResolved, User adminResponsible, ScreenBit referencedScreen) {
        this.id = id;
        this.description = description;
        this.fixMessage = fixMessage;
        this.dateReported = dateReported;
        this.bugResolved = bugResolved;
        this.adminResponsible = adminResponsible;
        this.referencedScreen = referencedScreen;
    }

    /**
     *  gets the description of the bug
     * @return the description string
     */
    public String getDescription() {
        return description;
    }

    /**
     * gets the date the bug was reported
     * @return the date reported in a string
     */
    public String getDateReported() {
        return dateReported;
    }

    /**
     * gets the admin responsible for fixing the bug
     * @return the user object for the admin responsible for fixing the bug.
     */
    public User getAdminResponsible() {
        return adminResponsible;
    }

    /**
     * Sets a user object to be responsible for fixing the bug.
     * @param adminResponsible the admin responsible for fixing the bug.
     */
    public void setAdminResponsible(User adminResponsible) {
        this.adminResponsible = adminResponsible;
    }

    /**
     * sets the description of the bug.
     * @param description the description of the bug.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * sets the date the specified bug was reported.
     * @param dateReported the date reported.
     */
    public void setDateReported(LocalDate dateReported) {
        setDateReported(dateReported.toString());
    }

    /**
     * sets the date the specified bug was reported.
     * @param dateReported the date reported.
     */
    public void setDateReported(LocalDateTime dateReported) {
        setDateReported(dateReported.toString());
    }

    /**
     * sets the date the specified bug was reported.
     * @param dateReported the date reported.
     */
    public void setDateReported(String dateReported) {
        this.dateReported = dateReported;
    }

    /**
     * gets the boolean indicating whether the bug is resolved or not.
     * @return the boolean indicating whether the the bug is resolved or not.
     */
    public boolean isBugResolved() {
        return bugResolved;
    }

    /**
     * sets the boolean indicating whether the bug is resolved or not to either true or false.
     * @param bugResolved true or false.
     */
    public void setBugResolved(boolean bugResolved) {
        this.bugResolved = bugResolved;
    }

    /**
     * parses the bit int from db to either true or false.
     * @param bugResolved the bit from the database. true if 1 or false if 0.
     */
    public void setBugResolved(int bugResolved) {
        this.bugResolved = bugResolved > 0 ? true : false;
    }

    /**
     * Gets the ScreenBit the bug was reported on.
     * @return the ScreenBit the bug was reported on.
     */
    public ScreenBit getReferencedScreen() {
        return referencedScreen;
    }

    /**
     * sets the ScreenBit the bug was reported on.
     * @param referencedScreen
     */
    public void setReferencedScreen(ScreenBit referencedScreen) {
        this.referencedScreen = referencedScreen;
    }

    /**
     * gets the ID of the bug.
     * @return the ID of this bug.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the current id ID the bug.
     * @param id the int ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setFixMessage(String fixMessage){
        this.fixMessage = fixMessage;
    }

    public String getFixMessage() {
        return fixMessage;
    }
}
