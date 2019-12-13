package br.com.gerenciador.pedidos.config;

import static springfox.documentation.schema.AlternateTypeRules.newRule;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;
import static springfox.documentation.swagger.web.DocExpansion.NONE;
import static springfox.documentation.swagger.web.ModelRendering.EXAMPLE;
import static springfox.documentation.swagger.web.OperationsSorter.ALPHA;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;

import br.com.gerenciador.pedidos.resource.PedidoResource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${app.version}")
	private String appVersion;

	@Bean
	public Docket bookingJuniperAPI(TypeResolver typeResolver) {
		return new Docket(SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage(PedidoResource.class.getPackage().getName())).build()
				.pathMapping("/").directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.alternateTypeRules(newRule(
						typeResolver.resolve(DeferredResult.class,
								typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
						typeResolver.resolve(WildcardType.class)))
				.useDefaultResponseMessages(false);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().description("Documentação da API de consulta de pedidos gerados")
				.title("API Pedidos").version(appVersion).build();
	}

	@Bean
	public UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().deepLinking(true).displayOperationId(false).defaultModelsExpandDepth(1)
				.defaultModelExpandDepth(1).defaultModelRendering(EXAMPLE).displayRequestDuration(false)
				.docExpansion(NONE).filter(false).maxDisplayedTags(null).operationsSorter(ALPHA).showExtensions(false)
				.tagsSorter(TagsSorter.ALPHA).validatorUrl(null).build();
	}

}
