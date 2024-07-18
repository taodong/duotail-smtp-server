package com.duotail.smtp.server.smtp.command;

import org.junit.jupiter.api.Test;

class CommandTest extends AbstractCommandIntegrationTest {

	@Test
	void testCommandHandling() throws Exception {
		this.expect("220");

		this.send("blah blah blah");
		this.expect("503");
	}
}
