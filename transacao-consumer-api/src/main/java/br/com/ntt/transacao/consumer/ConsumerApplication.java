package br.com.ntt.transacao.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"br.com.ntt.transacao", "br.com.ntt.common"})
@EnableJpaRepositories(basePackages = "br.com.ntt.common.transacao.infra.persistence")
@EntityScan(basePackages = "br.com.ntt.common.transacao.infra.persistence")
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
