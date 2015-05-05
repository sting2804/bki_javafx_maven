class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        group("/api") {

            "/loans/all"(controller: 'loanInformation'){
                action = [GET: "selectAll"]
            }

            "/loans"(controller: 'loanInformation'){
                action = [GET: "selectClient"]
            }

            "/employeeRoles"(controller: 'employeeRole')

            "/protocolStatuses"(controller: 'protocolStatus')

            "/patientGroupsByProcedures"(controller: 'patientGroupsByProcedures')

            "/smsLogs"(controller: 'smsLogs')


            "/testServer/"(controller: "home") {
                action = [GET: "verifyCorrectInvestigatorServerAddress"]
            }
            "/laboratoryTestTypes/$id?"(controller: "inLab") {
                action = [GET: "laboratoryTestTypes"]
            }
        }
	}
}
