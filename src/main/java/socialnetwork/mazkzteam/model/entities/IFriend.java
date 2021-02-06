package socialnetwork.mazkzteam.model.entities;

import java.sql.Timestamp;

public interface IFriend {
    int getId();
    String getUsername();
    String getAvatar();
    String getAddress();
    Timestamp getDate_of_birth();
    String getFirst_name();
    String getLast_name();
    String getPhone();
    String getGender();
    Boolean getStatus();
}
