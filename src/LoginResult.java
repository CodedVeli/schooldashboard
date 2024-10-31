public class LoginResult {
    private int userId;
    private boolean isAdmin;

    public LoginResult(int userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}