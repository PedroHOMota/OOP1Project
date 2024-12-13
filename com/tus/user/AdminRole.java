

package com.tus.user;

import com.tus.exceptions.UserNotFound;

public interface AdminRole{

    public void deleteAUser(final String userName) throws UserNotFound;
}
