package com.duotail.smtp.server.smtp.command;


import com.duotail.smtp.server.smtp.SmtpConfigurationProperties;
import com.duotail.smtp.server.smtp.client.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ActiveProfiles({"integrationtest", "default"})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public abstract class AbstractCommandIntegrationTest {

	@Autowired
	private SmtpConfigurationProperties smtpConfigurationProperties;

	protected Client c;

	@BeforeEach
	protected void setUp() throws Exception {
		this.c = new Client("localhost", smtpConfigurationProperties.getPort());
	}

	@AfterEach
	protected void tearDown() throws Exception {
		this.c.close();
	}

	public void send(final String msg) throws Exception {
		this.c.send(msg);
	}

	public void expect(final String msg) throws Exception {
		this.c.expect(msg);
	}

	public void expectContains(final String msg) throws Exception {
		this.c.expectContains(msg);
	}
}
