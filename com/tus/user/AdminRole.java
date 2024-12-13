

package com.tus.user;

import com.tus.exceptions.UserNotFound;

public sealed interface AdminRole permits AdminUser{

    public void deleteAUser(final String userName) throws UserNotFound;
}
