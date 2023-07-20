package com.allianz.sqltool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.RedirectView;

@SpringBootApplication
@EnableJpaRepositories("com.allianz.sqltool.repo")
@EnableWebMvc
@RestController
public class SQLTOOL
{
	public static Environment environment;
	public static ApplicationContext appContext;

	public static void main(String[] args)
	{
		SpringApplication.run(SQLTOOL.class, args);

		String puertoServer = environment.getProperty("server.port");

		System.out.println("SQL TOOL - API - CORRIENDO EN http://localhost:" + puertoServer);
	}




	@GetMapping(value = "/")
	public static RedirectView swagger()
	{
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("swagger-ui.html");

		return redirectView;
	}

	@Autowired
	public void setearEnvironment(Environment environment)
	{
		SQLTOOL.environment = environment;
	}
	private static String dameVariableEntorno(String nombreVariable)
	{
		return environment.getProperty(nombreVariable);
	}

}
