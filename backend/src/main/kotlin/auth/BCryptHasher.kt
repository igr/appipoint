package auth

import org.mindrot.jbcrypt.BCrypt

object BCryptHasher {

    /**
     * Check if the password matches the User's password
     */
    fun checkPassword(attempt: String, password: String) = BCrypt.checkpw(attempt, password)

    /**
     * Returns the hashed version of the supplied password.
     */
    fun hashPassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

}