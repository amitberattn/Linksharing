package linksharing

class ApplicationFilters {

    def filters = {
        logRequest(controller:'*',action:'*'){
            before= {
                println("controller : " + controllerName)
                println("action : " + actionName)
            }
        }

        requestHeadersFilter(controller: '*', action: '*') {
            after = {
                response.setHeader("Pragma", "no-cache")
                response.setDateHeader("Expires", 1L)
                response.setHeader("Cache-Control", "no-cache")
                response.addHeader("Cache-Control", "no-store")
            }
        }
/*

        beforeLogin(controller: 'login', action: 'index', invert: true) {
            before = {
                if (!session.user && !(actionName.equals("forgotPassword") || actionName.equals("forgotPasswordEmailSet") || actionName.equals("forgotPasswordReset") || actionName.equals("resetPasswordPage") || actionName.equals("login") || actionName.equals("show") ||actionName.equals("profile") )) {
                    println("con : "+controllerName)
                    println("act : "+actionName)
                    redirect(url: "/")
                    return
                }
            }

        }
        */
/*| forgotPassword | forgotPasswordEmailSet | forgotPasswordReset | resetPasswordPage*//*

 */
/*       beforeLogin(controller: 'userDetail | invitation | resource | topic') {
            before = {
                if (!session.user) {
                    println(controllerName)
                    println(actionName)
                    redirect(url: "/")
                    //return false
                }
            }

        }*//*


        requestHeadersFilter(controller: '*', action: '*') {
            after = {
                response.setHeader("Pragma", "no-cache")
                response.setDateHeader("Expires", 1L)
                response.setHeader("Cache-Control", "no-cache")
                response.addHeader("Cache-Control", "no-store")
            }
        }



        afterLogin(controller: 'login', action: 'index') {
            before = {
                if (session.user) {
                    redirect(controller: 'userDetail', action: 'dashboard')
                }
            }
        }
*/

    }
}
