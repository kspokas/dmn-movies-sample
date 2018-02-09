package com.dmn.example;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

public class EmbeddedDMNTest {
	private static final Logger log = Logger.getLogger(EmbeddedDMNTest.class);
	
	@Test
	public void testLocalDMN() {
		KieServices kieServices = KieServices.Factory.get();

		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime( DMNRuntime.class );
		
		DMNModel dmnModel = 
				dmnRuntime.getModel("http://www.redhat.com/_c7328033-c355-43cd-b616-0aceef80e52a", 
						"dmn-movieticket-ageclassification");
		
		DMNContext dmnContext = dmnRuntime.newContext();
		
		for (Integer age : Arrays.asList(1,12,13,64,65,66)) {
			dmnContext.set("Age", age);
			DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
			for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
				log.info("Age " + age + " Decision '" + dr.getDecisionName() + "' : " + dr.getResult());
			}
		}
	}
}
