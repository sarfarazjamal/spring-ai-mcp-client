package com.jamal.springai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpClientApplication.class, args);
    }


    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }

    @Bean
    CommandLineRunner run(ChatClient chatClient,
                          ToolCallbackProvider mcpToolProvider) {

        return args -> {

            String response = chatClient
                    .prompt("""
            Execute the following steps:
            1. Call getCustomer with customerId=1.
            2. Call getOrder with orderId=1.
            3. Correlate the customer and order information.
            4. Generate a business summary.
            """).toolCallbacks(mcpToolProvider)
                    .call()
                    .content();

            System.out.println(response);
        };
    }
}