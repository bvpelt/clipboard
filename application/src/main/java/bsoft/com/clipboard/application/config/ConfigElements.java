package bsoft.com.clipboard.application.config;

import lombok.Getter;


@Getter
public class ConfigElements {
    private final String queueName = "task_queue";
    private final String newsExchanges = "news";
    private final String sportExchanges = "sport";
    private final String financeExchanges = "finance";
}
