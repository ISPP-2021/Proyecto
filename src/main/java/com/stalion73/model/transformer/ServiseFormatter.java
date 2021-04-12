package com.stalion73.model.transformer;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import com.stalion73.model.Servise;
import com.stalion73.service.ServiseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

/**
 * Instructs Spring MVC on how to parse and print elements of type 'PetType'. Starting
 * from Spring 3.0, Formatters have come as an improvement in comparison to legacy
 * PropertyEditors. See the following links for more details: - The Spring ref doc:
 * http://static.springsource.org/spring/docs/current/spring-framework-reference/html/validation.html#format-Formatter-SPI
 * - A nice blog entry from Gordon Dickens:
 * http://gordondickens.com/wordpress/2010/09/30/using-spring-3-0-custom-type-converter/
 * <p/>
 * Also see how the bean 'conversionService' has been declared inside
 * /WEB-INF/mvc-core-config.xml
 *
 * @author Mark Fisher
 * @author Juergen Hoeller
 * @author Michael Isvy
 */
@Component
public class ServiseFormatter implements Formatter<Servise> {

	private final ServiseService serviseService;

	@Autowired
	public ServiseFormatter(ServiseService serviseService) {
		this.serviseService = serviseService;
	}

	@Override
	public String print(Servise servise, Locale locale) {
		return servise.getName();
	}

	@Override
	public Servise parse(String text, Locale locale) throws ParseException {
		return null;
	}

}