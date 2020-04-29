package com.helper;

import com.entity.User;

import java.util.List;

public class AccessHelper {

    public static boolean isAdminOrPlantManager(User user){

        List<String> roles = user.getRoles();
        if(roles.contains("SUPERADMIN")){
            return true;
        }
        if(roles.contains("PLANTMANAGER")){
            return true;
        }
        return false;
    }

}
