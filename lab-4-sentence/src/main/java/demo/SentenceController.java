package demo;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SentenceController {

	@Autowired LoadBalancerClient loadBalancer;
	
	@RequestMapping("/sentence")
	  public @ResponseBody String getSentence() {
	    return 
	      getWord("SUBJECT") + " "
	      + getWord("VERB") + " "
	      + getWord("ARTICLE") + " "
	      + getWord("ADJECTIVE") + " "
	      + getWord("NOUN") + "."
	      ;
	  }

	  public String getWord(String service) {
		ServiceInstance instance = loadBalancer.choose(service);
	   	return (new RestTemplate()).getForObject(instance.getUri(),String.class);
	  }
	  
}
