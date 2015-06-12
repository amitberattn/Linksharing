package linksharing

class ApplicationFilters {

    def filters = {

        beforeLogin(controller: 'login', action: 'index', invert: true) {
            before = {
                if (!session.user) {
                    redirect(url: "/")
                }
            }

        }
        afterLogin(controller: 'login', action: 'index') {
            before = {
                if (session.user) {
                    redirect(controller: 'userDetail', action: 'dashboard')
                }
            }
        }

    }
}
