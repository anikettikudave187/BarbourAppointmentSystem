package com.dbApi.dbApi.responseBody;

import com.dbApi.dbApi.modelClasses.AppUser;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminRB {
    List<AppUser> admins;

    public List<AppUser> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AppUser> admins) {
        this.admins = admins;
    }
}
