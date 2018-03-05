package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.Ackermann;
import repo.IExternalCall;

@RestController
@ComponentScan("repo")
public class AckermannController {
	
	@Autowired
	private IExternalCall externalCall;
	

	@RequestMapping("/ackermann")
	public Ackermann ackermann(@RequestParam(value="M") int m, @RequestParam(value="N") int n) {
		Ackermann response =new Ackermann();
		//externalCall=new ExternalCallImpl();
		if (m==0) {
			response.setResult(n+1);
		} else if (m>0 && n==0) {
			// Call Rest Service passing (m-1,1)
			System.out.println("Calling2");
			response=externalCall.callAckermann(m-1, 1);
		} else if (m>0 && n>0) {
			// Call Rest Service 1 time to get N=(m,n-1)
			System.out.println("Calling3a");
			Ackermann N = externalCall.callAckermann(m, n-1);
			// Call Rest Service 2 time with m-1,N)
			System.out.println("Calling3b");
			response=externalCall.callAckermann(m-1, N.getResult());
		}
		
		return response;
	}
}
