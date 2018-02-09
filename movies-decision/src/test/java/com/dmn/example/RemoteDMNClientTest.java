package com.dmn.example;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNResult;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.DMNServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class RemoteDMNClientTest {
	private static final Logger log = Logger.getLogger(RemoteDMNClientTest.class);
	
	private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
    private static final String USER = "krisv";
    private static final String PASSWORD = "krisv";

    private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

    private static KieServicesConfiguration conf;
    private static KieServicesClient kieServicesClient;
    
    @BeforeClass
    public static void setup() {
    	conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
        conf.setMarshallingFormat(FORMAT);
        kieServicesClient = KieServicesFactory.newKieServicesClient(conf);
    }
	
	@Test
	public void testRemoteDMN() {
		DMNServicesClient dmnClient = kieServicesClient.getServicesClient( DMNServicesClient.class );
		
		for (Integer age : Arrays.asList(1,12,13,64,65,66)) {
			DMNContext dmnContext = dmnClient.newContext();
			dmnContext.set("Age", age);
			ServiceResponse<DMNResult> serverResp = 
					dmnClient.evaluateAll("MovieDMNContainer", 
							"http://www.redhat.com/_c7328033-c355-43cd-b616-0aceef80e52a",
							"dmn-movieticket-ageclassification",
							dmnContext);
			
			DMNResult dmnResult = serverResp.getResult();
			for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
				log.info("Age " + age + " Decision '" + dr.getDecisionName() + "' : " + dr.getResult());
			}
		}
	}
	
}
