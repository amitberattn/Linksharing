import com.linksharing.*

class BootStrap {
    def springSecurityService

    def init = { servletContext ->
        //String password= springSecurityService.encodePassword("123")
 /*       User user= new User(username:"amit",password:"123",enabled:true).save(flush: true)
        Role role = new Role("ROLE_USER").save(flush: true)
        UserRole userRole = new UserRole(user,role).save(flush: true)*/

        User user = new UserDetail(username:"amit",password:"123",enabled:true,email:"amitbera.10@gmail.com",firstName:"Amit",lastName:"Bera")
        user.save(flush: true)
        Role role = new Role("ROLE_USER").save(flush: true)
        UserRole userRole = new UserRole(user,role).save(flush: true)

        User user1 = new UserDetail(username:"admin",password:"123",enabled:true,email:"admin@gmail.com",firstName:"admin",lastName:"admin")

        user1.save(flush: true)
        Role role1 = new Role("ROLE_ADMIN").save(flush: true)
        new UserRole(user1,role1).save(flush: true)
    }
    def destroy = {
    }
}
