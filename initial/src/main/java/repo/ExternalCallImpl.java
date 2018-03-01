package repo;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import model.Ackermann;

@Component
public class ExternalCallImpl implements IExternalCall {
	
	@Override
	public int callAckermann(int m, int n) {
		int result=0;
        RestTemplate restTemplate = new RestTemplate();
        try {
        Ackermann res = restTemplate.getForObject("http://localhost:8080/ackermann?M="+ m+ "&N="+n, Ackermann.class);
        result = res.getResult();
        System.out.println("GOT A RESULT="+res.getResult());
        } catch (Throwable t) {
        	System.out.println("BAAADDDDDD");
        }
		return result;
	}

}
