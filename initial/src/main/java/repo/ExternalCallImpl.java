package repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import model.Ackermann;

@Component
public class ExternalCallImpl implements IExternalCall {
	
	private String url;
	@Autowired
    private DiscoveryClient discoveryClient;

	@Override
	@Cacheable("ackerman")
	public Ackermann callAckermann(int m, int n) {
		Ackermann resp = new Ackermann();
		resp.setM(m);
		resp.setN(n);
		int result=0;
        RestTemplate restTemplate = new RestTemplate();
        try {
        	url="http://localhost:8080";
        	List<ServiceInstance> list =discoveryClient.getInstances("ackermann-client");
        	if(list!=null && list.size()>0) {
                url=list.get(0).getUri().toString();
           }
        System.out.println("URL="+url);
        Ackermann res = restTemplate.getForObject(url+"/ackermann?M="+ m+ "&N="+n, Ackermann.class);
        result = res.getResult();
        System.out.println("GOT A RESULT="+res.getResult());
        } catch (Throwable t) {
        	System.out.println("BAAADDDDDD"+t.getMessage());
        }
        resp.setResult(result);
		return resp;
	}

}
