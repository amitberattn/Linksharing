

import com.linksharing.User
import com.linksharing.UserDetail

class FacebookUser {

    Long uid
    String accessToken
    Date accessTokenExpires

    static belongsTo = [user: UserDetail]

    static constraints = {
        uid unique: true
    }
}
