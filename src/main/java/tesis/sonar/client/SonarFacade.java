package tesis.sonar.client;

import java.util.ArrayList;
import java.util.List;

import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;


import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.MetricQuery;


import org.sonar.wsclient.services.ManualMeasure;
import org.sonar.wsclient.services.ManualMeasureQuery ;

/**
 * Casos de ejemplo de uso de la API via GET
 * 
 *  Obtener todos los proyectos:
 * http://localhost:9000/api/resources
 * 
 *  Obtener de todos los proyectos las lineas de codigo y complejidad:
 * http://localhost:9000/api/resources?metrics=ncloc,complexity
 *
 *  Idem anterior pero para el proyecto "test":
 * http://localhost:9000/api/resources?resource=test&metrics=ncloc,complexity
 * 
 *
 *
 */
public class SonarFacade {
	
	private Sonar sonar ;
	
	/**
	 * 
	 * Crea una instancia de "org.sonar.wsclient.Sonar" anonima
	 * (no requiere autentificación) esta instancia referencia 
	 * al servidor donde se aloja SonarQube. 
	 * 
	 * @param 	url 		es la URL absoluta de la ubicacion base de SonarQube.
	 * @param 	login		es usuario con el cual se autenticara.
	 * @param 	password    es la contraseña asociada a ese usuario.
	 */
	public SonarFacade(String url, String login, String password) {
        sonar = new Sonar(new HttpClient4Connector(new Host(url, login, password))); 
	}

	/**
	 * Crea una instancia de "org.sonar.wsclient.Sonar" anonima
	 * (no requiere autentificación) esta instancia referencia 
	 * al servidor donde se aloja SonarQube. 
	 * 
	 * @param 	url 		es la URL absoluta de la ubicacion base de SonarQube.
	 */
	public SonarFacade(String url) {
        sonar = new Sonar(new HttpClient4Connector(new Host(url)));
	}
	
	/**
	 * Devuelve todos los recursos que hay en un proyecto dado. 
	 * Un recurso puede ser de cualquier indole, por ejemplo: proyectos,
	 * directorios, archivos, etc.
	 * 
	 * Esto equivale a:
	 * GET url_base/api/resources?resource=projectKey&depth=-1
	 * 
	 * @param 	projectKey	es la clave que indentifica el proyecto, representado en un String.
	 * @return				retorna una lista de Resources.
	 * 
	 */
	public List<Resource> getResources( String projectKey ){
		return  sonar.findAll(ResourceQuery.create( projectKey )) ;
		
	}
	
	/**
	 * 
	 * @param projectKey
	 * @return todas los tipos de metricas dentro del proyecto projectKey
	 */
	public List<Metric> getMetrics() {
		return sonar.findAll(MetricQuery.all()) ;
	}
	
	public List<Metric> getMetric( String projectKey, String metricKey ) {
		return sonar.findAll(MetricQuery.byKey(metricKey)) ;
	}

	public List<ManualMeasure> getMeasure( String projectKey, String metricKey ) {
		return sonar.findAll(ManualMeasureQuery.create(metricKey)) ;
	}
	
	
	/**
	 * Extraer todos los valores de todas las metricas (definidas en la instancia
	 * de Sonar) de un proyecto dado.
	 * 
	 * @param projectKey	Clave unica que identifica el proyecto dentro de Sonar
	 * @return 				Una lista de recursos que contienen los valores para cada metrica
	 */
	public List<Resource> getMetricValues(String projectKey) {
		List<Metric> metrics = getMetrics() ;
		List<Resource> result = new ArrayList<Resource>() ;
		for ( Metric m : metrics ) {
			Resource resource = sonar.find(ResourceQuery.createForMetrics(projectKey , m.getKey())) ;			
			if ( resource!=null ) {
				// System.out.println(resource.getMeasure(m.getKey())) ;			
				System.out.println(m.getKey() + "=" + resource.getMeasure(m.getKey()).getValue()) ;	
				result.add(resource);
			} else { 
				System.out.println("Don't exist metric '"+m.getKey()+"' into the proyect '" + projectKey + "'") ;
			}
		}
		return result ;
		
	}
 	
	
	
}
