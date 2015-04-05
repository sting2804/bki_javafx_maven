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

            "/loans"(controller: 'loanInformation'){
                action = [GET: "selectAll"]
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
