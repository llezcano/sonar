package tesis.sonar.client;

public class Main {
	
	public static void main(String[] args){
		String project = "org.jvnet.hudson.plugins:sonar" ;
		//Prueba con localhost
		//SonarFacade sonar = new SonarFacade( "http://localhost:9000", "admin", "admin" ) ;
		
		// Prueba con la instandia remota de sonar
		SonarFacade sonar = new SonarFacade( "http://nemo.sonarqube.org/") ;
		
		//	System.out.println(sonar);
		
		// Jenkinks Sonar Plugin (Key = org.jvnet.hudson.plugins:sonar )
		//System.out.println(sonar.getResources("org.jvnet.hudson.plugins:sonar"));
		//System.out.println(sonar.getMetric("org.jvnet.hudson.plugins:sonar", "ncloc"));
		//	System.out.println(sonar.getMeasure("org.jvnet.hudson.plugins:sonar", "org.jvnet.hudson.plugins:sonar"));
	

		System.out.println( sonar.getMetricValues(project) ) ;
	
	
	}

}