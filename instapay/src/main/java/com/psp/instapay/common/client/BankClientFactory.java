package com.psp.instapay.common.client;

import com.psp.instapay.common.client.banks.CIBClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BankClientFactory {
    private final Map<String, BankClient> clients;

    public BankClientFactory(CIBClient cibClient) {
        clients = new HashMap<>();
        clients.put("CIB", cibClient);
    }

    public BankClient getBankClient(String bankName) {
        return clients.get(bankName);
    }
}
