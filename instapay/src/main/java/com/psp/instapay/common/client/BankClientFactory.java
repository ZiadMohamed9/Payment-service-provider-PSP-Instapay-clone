package com.psp.instapay.common.client;

import com.psp.instapay.common.client.banks.CIBClient;
import com.psp.instapay.common.client.banks.NBEClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class BankClientFactory {
    private final Map<String, BankClient> clients;

    public BankClientFactory(CIBClient cibClient, NBEClient nbeClient) {
        clients = new HashMap<>();
        clients.put("CIB", cibClient);
        clients.put("NBE", nbeClient);
    }

    public BankClient getBankClient(String bankName) {
        return clients.get(bankName);
    }
}
